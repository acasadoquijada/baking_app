package com.example.backing_app.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.backing_app.MainActivity;
import com.example.backing_app.R;
import com.example.backing_app.recipe.Recipe;
import com.example.backing_app.ui.RecipeFragment;
import com.example.backing_app.utils.AppExecutorUtils;
import com.example.backing_app.utils.RecipesUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private static final String TAG = RecipeViewModel.class.getSimpleName();
    private List<Recipe> mRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Recipe> getRecipes(){
        if(mRecipes == null){
            Log.d(TAG,"I request the recipes online");
            mRecipes = RecipesUtils.getRecipes();
        }
        return mRecipes;
    }
}
