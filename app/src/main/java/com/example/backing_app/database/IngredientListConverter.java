package com.example.backing_app.database;

import androidx.room.TypeConverter;

import com.example.backing_app.recipe.Ingredient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientListConverter {
    @TypeConverter
    public static List<Ingredient> fromString(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Ingredient> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
