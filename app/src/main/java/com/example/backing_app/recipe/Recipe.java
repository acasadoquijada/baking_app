package com.example.backing_app.recipe;

import java.util.List;

/**
 * POJO representing a Recipe
 */
public class Recipe {

    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private String servings;
    private String image;


    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

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
