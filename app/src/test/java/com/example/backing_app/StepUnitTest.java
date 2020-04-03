package com.example.backing_app;

import com.example.backing_app.recipe.Step;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StepUnitTest {

    @Test
    public void testStep(){
        Step step = new Step();

        String video_url = "safqawfqawf";
        String short_description = "dsgfsbfdb";
        String description = "egwgeh";
        int id = 2;
        int key = 4;
        int recipe_id = 12;
        String thumbnailURL= "ggvdgershsr";


        step.setVideoURL(video_url);
        step.setShortDescription(short_description);
        step.setDescription(description);
        step.setId(id);
        step.setKey(key);
        step.setRecipeId(recipe_id);
        step.setThumbnailURL(thumbnailURL);

        assertEquals(video_url, step.getVideoURL());
        assertEquals(short_description, step.getShortDescription());
        assertEquals(description, step.getDescription());
        assertEquals(id, step.getId());
        assertEquals(key, step.getKey());
        assertEquals(recipe_id, step.getRecipeId());
        assertEquals(thumbnailURL, step.getThumbnailURL());
    }
}
