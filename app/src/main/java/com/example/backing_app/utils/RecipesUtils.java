package com.example.backing_app.utils;

import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.recipe.Step;
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

        return (index + ". " + stepName);
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


            // For the step video there are two variables:
            // - videoURL
            // - thumbnailURL
            // They may or may not contain info, so for simplicity, we are going to store
            // the mediaURL(from videoURL o thumbnailURL) into videoURL. This will make future
            // queries more efficient

            assert recipes != null;
            for(int i = 0; i < recipes.size(); i++){

                for(int j = 0; j < recipes.get(i).getSteps().size(); j++){

                    Step s = recipes.get(i).getSteps().get(j);
                    String mediaURL;

                    if (!s.getVideoURL().equals("")) {
                        mediaURL = recipes.get(i).getSteps().get(j).getVideoURL();
                    } else if (!s.getThumbnailURL().equals("")) {
                        mediaURL = s.getThumbnailURL();
                    } else {
                        mediaURL = "";
                    }

                    s.setVideoURL(mediaURL);
                }
            }


            // Al the logic is done here to avoid increase the memory usage as the method arguments
            // are passed as value (copied) instead of reference

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

            // Data for the recipe index 2 steps is incorrect. Some indexes are wrong:
            // Step 7 has index 8, step 8 has index 9...
            // As this only happens for this recipe, we are going to use a quick fix for this
            // specific case. In a real world database, the fix should be generic for all the
            // recipes

            int wrong_indexes_recipe_index = 2;
            int wrong_index_start = 7;

            for(int i = wrong_index_start; i < recipes.get(wrong_indexes_recipe_index).getSteps().size(); i++){
                recipes.get(wrong_indexes_recipe_index).getSteps().get(i).setId(i);
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return recipes;
    }
}
