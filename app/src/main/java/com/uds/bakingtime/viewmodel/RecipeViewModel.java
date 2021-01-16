package com.uds.bakingtime.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.uds.bakingtime.model.Recipe;
import com.uds.bakingtime.network.RecipeApiClient;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    LiveData<List<Recipe>> recipeList;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeList = RecipeApiClient.getInstance().getRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
