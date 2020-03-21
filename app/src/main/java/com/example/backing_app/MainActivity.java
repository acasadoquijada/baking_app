package com.example.backing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.utils.NetworkUtils;
import com.example.backing_app.utils.RecipesUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppExecutorUtils.getsInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                recipes = RecipesUtils.getRecipes();

                String s = "A";
            }
        });



    }
}
