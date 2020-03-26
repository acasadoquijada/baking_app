package com.example.backing_app.utils;

import android.util.Log;

import com.example.backing_app.recipe.Ingredient;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.recipe.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipesUtils {

    private static String TAG = RecipesUtils.class.getSimpleName();

    private static String id_token = "id";
    private static String name_token = "name";
    private static String ingredients_token = "ingredients";
    private static String steps_token = "steps";
    private static String servings_token = "servings";
    private static String image_token = "image";
    private static String quantity_token = "quantity";
    private static String measure_token = "measure";
    private static String ingredient_name_token = "ingredient";
    private static String description_token = "description";
    private static String short_description_token = "shortDescription";
    private static String video_url_token = "videoURL";
    private static String thumbnail_url_token = "thumbnailURL";

    /**
     * Parses all the recipes from JSON to a list of recipes objects
     * @param recipesJSON String with the recipes info
     * @return list of recipes objects
     */
    private static List<Recipe> parseRecipes(String recipesJSON){


        List<Recipe> recipes = new ArrayList<>();
        // Create a JSONObject with the recipes information
        try {

            JSONArray recipesJSONArray = new JSONArray(recipesJSON);

            for (int i = 0; i < recipesJSONArray.length(); i++){
                recipes.add(parseRecipe(recipesJSONArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error parsing recipes");
            return null;

        }

        return recipes;
    }

    private static List<Ingredient> parseIngredients(JSONArray ingredientsJSON,int recipe_id){

        List<Ingredient> ingredients = new ArrayList<>();

        try {

            for(int i = 0; i < ingredientsJSON.length(); i++){

                Ingredient ingredient = new Ingredient();

                JSONObject ingredientJSON = ingredientsJSON.getJSONObject(i);
                ingredient.setIngredientName(ingredientJSON.getString(ingredient_name_token));
                ingredient.setMeasure(ingredientJSON.getString(measure_token));
                ingredient.setQuantity(ingredientJSON.getInt(quantity_token));

                ingredient.setRecipeId(recipe_id);

                ingredients.add(ingredient);
                Log.d(TAG,ingredient.getIngredientName());
                Log.d(TAG,String.valueOf(ingredient.getQuantity()));
                Log.d(TAG,ingredient.getMeasure());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    private static String getQualifiedStepShortDescription(int index, String stepName){

        String qualifiedName = (index + 1) + ". " + stepName;

        return qualifiedName;

    }

    private static List<Step> parseSteps(JSONArray stepsJSON, int recipe_id){

        List<Step> steps = new ArrayList<>();
        try{

            for(int i = 0; i < stepsJSON.length(); i++){

                Step step = new Step();

                JSONObject stepJSON = stepsJSON.getJSONObject(i);

                step.setIndex(stepJSON.getInt(id_token));

                step.setDescription(stepJSON.getString(description_token));

                step.setShortDescription(
                        getQualifiedStepShortDescription(
                                step.getIndex(),
                                stepJSON.getString(short_description_token)));

                step.setThumbailURL(stepJSON.getString(thumbnail_url_token));

                step.setVideoURL(stepJSON.getString(video_url_token));

                step.setRecipeId(recipe_id);

                steps.add(step);
                Log.d(TAG,step.getDescription());
                Log.d(TAG,step.getShortDescription());
                Log.d(TAG,step.getThumbailURL());
                Log.d(TAG,step.getVideoURL());
            }

        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        return steps;
    }

    private static Recipe parseRecipe(JSONObject recipeJSON){

        Recipe recipe = new Recipe();

        try {

            recipe.setId(recipeJSON.getInt(id_token));

            recipe.setName(recipeJSON.getString(name_token));

            recipe.setServings(recipeJSON.getString(servings_token));

            recipe.setImage(recipeJSON.getString(image_token));

            recipe.setIngredients(parseIngredients(recipeJSON.getJSONArray(ingredients_token),recipe.getId()));

            recipe.setSteps(parseSteps(recipeJSON.getJSONArray(steps_token),recipe.getId()));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return recipe;
    }

    public static List<Recipe> getRecipes(){

        String recipesJSON = NetworkUtils.getRecipesJSON();

        return parseRecipes(recipesJSON);

    }
}
