package com.example.backing_app.recipe;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.backing_app.database.IngredientListConverter;
import com.example.backing_app.database.StepListConverter;

import java.util.List;

/**
 * POJO representing a Recipe.
 *
 * As we have tables for the ingredients and steps, we don't need to store that info within
 * the recipe table
 *
 * Please see RecipeDataBase for more info about the Database structure
 */

@Entity(tableName = "recipes")

public class Recipe {

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

}
