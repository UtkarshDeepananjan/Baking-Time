package com.uds.bakingtime.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.uds.bakingtime.model.Recipe;

public class WidgetPrefs {
    public final static String PREFS_KEY = "recipe-prefs";
    public final static String RECIPE_KEY = "recipe-key";

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE).edit();
        String json = new Gson().toJson(recipe);

        prefs.putString(RECIPE_KEY, json);

        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        String recipeJson = prefs.getString(RECIPE_KEY, "");

        return recipeJson.equals("") ? null : new Gson().fromJson(recipeJson, Recipe.class);
    }
}
