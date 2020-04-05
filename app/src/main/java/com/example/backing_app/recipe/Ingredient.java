package com.example.backing_app.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Java object that represents an Ingredient. The associated table has a foreign key set to its
 * recipe. Please see RecipeDataBase for more info about the Database structure
 */

@Entity(tableName = "ingredients",
        indices =  {@Index("recipeId")},
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = CASCADE))

public class Ingredient implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int key;
    private float quantity;
    private String measure;
    @Json(name = "ingredient") private String ingredientName;

    @ColumnInfo(name = "recipeId")
    private int recipeId;

    public Ingredient(){

    }

    protected Ingredient(Parcel in) {
        key = in.readInt();
        quantity = in.readFloat();
        measure = in.readString();
        ingredientName = in.readString();
        recipeId = in.readInt();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getKey() {
        return key;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredientName);
        dest.writeInt(recipeId);
    }
}