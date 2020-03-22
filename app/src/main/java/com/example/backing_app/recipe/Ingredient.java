package com.example.backing_app.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredientName;

    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredientName = in.readString();
    }

    public Ingredient(){

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

    public int getQuantity() {
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredientName);
    }
}
