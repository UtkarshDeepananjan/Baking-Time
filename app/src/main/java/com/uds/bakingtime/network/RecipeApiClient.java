package com.uds.bakingtime.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.uds.bakingtime.Utils.Constants;
import com.uds.bakingtime.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeApiClient {
    private static RecipeApi recipeApi;
    private static final RecipeApiClient mClient = new RecipeApiClient();

    public static RecipeApiClient getInstance() {
        return mClient;
    }

    private RecipeApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipeApi = retrofit.create(RecipeApi.class);
    }

    public LiveData<List<Recipe>> getRecipes() {
        MutableLiveData<List<Recipe>> mutableLiveData = new MutableLiveData<>();
        recipeApi.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.body() != null) {
                    List<Recipe> movies = response.body();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("Call",t.getLocalizedMessage());
            }
        });
        return mutableLiveData;
    }
}
