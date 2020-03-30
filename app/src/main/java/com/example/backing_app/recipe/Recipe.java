package com.example.backing_app.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.backing_app.database.IngredientListConverter;
import com.example.backing_app.database.StepListConverter;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

/**
 * POJO representing a Recipe.
 *
 * As we have tables for the ingredients and steps, we don't need to store that info within
 * the recipe table
 *
 * Please see RecipeDataBase for more info about the Database structure
 */

@Entity(tableName = "recipes")

public class Recipe implements Parcelable {

    @PrimaryKey
    private int id;

    private String name;
    @Ignore
    private List<Ingredient> ingredients;
    @Ignore
    private List<Step> steps;
    private String servings;
    private String image;

    public Recipe(){

    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @TypeConverters({IngredientListConverter.class})
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @TypeConverters({StepListConverter.class})
    public List<Step> getSteps() {
        return steps;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeString(servings);
        dest.writeString(image);
    }
}
