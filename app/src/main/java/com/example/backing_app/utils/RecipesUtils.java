package com.example.backing_app.utils;

import com.example.backing_app.recipe.Recipe;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class RecipesUtils {

    private static String TAG = RecipesUtils.class.getSimpleName();

    /**
     * An index is added to the step name, to easily identify the step order when all the steps
     * are presented to the user
     * @param index of the step
     * @param stepName name of the step
     * @return a qualified step name. index + stepName
     */
    private static String getQualifiedStepShortDescription(int index, String stepName){

        return (index + 1) + ". " + stepName;
    }

    /**
     * Obtain the recipe raw info from the internet and parses it into a List of Recipe objects
     * @return a List of Recipes
     */
    public static List<Recipe> getRecipes(){

        String recipesJSON = NetworkUtils.getRecipesJSON();

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Recipe.class);
        JsonAdapter<List<Recipe>> jsonAdapter = moshi.adapter(type);
        List<Recipe> recipes;
        try {
            assert recipesJSON != null;
            recipes = jsonAdapter.fromJson(recipesJSON);

            assert recipes != null;
            for(int i = 0; i < recipes.size(); i++){

                // Add recipe id to the steps and qualified name (step_index + step_short_description)
                for(int j = 0; j < recipes.get(i).getSteps().size(); j++){
                    recipes.get(i).getSteps().get(j).setRecipeId(recipes.get(i).getId());

                    String qualifiedName =
                            getQualifiedStepShortDescription(
                                    recipes.get(i).getSteps().get(j).getId(),
                                    recipes.get(i).getSteps().get(j).getShortDescription());

                    recipes.get(i).getSteps().get(j).setShortDescription(qualifiedName);

                }

                // Add recipe id to the ingredients
                for(int j = 0; j < recipes.get(i).getIngredients().size(); j++){
                    recipes.get(i).getIngredients().get(j).setRecipeId(recipes.get(i).getId());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return recipes;
    }
}
