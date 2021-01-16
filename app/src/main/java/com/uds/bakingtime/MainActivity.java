package com.uds.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.uds.bakingtime.Utils.ItemClickListener;
import com.uds.bakingtime.adapters.RecipeAdapter;
import com.uds.bakingtime.databinding.ActivityMainBinding;
import com.uds.bakingtime.model.Recipe;
import com.uds.bakingtime.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    private RecipeAdapter adapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        adapter = new RecipeAdapter(new ArrayList<>(), this);
        binding.rvRecipe.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        binding.rvRecipe.setAdapter(adapter);
        RecipeViewModel viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.getRecipeList().observe(this, recipes -> {
                    adapter.setRecipes(recipes);
                    this.recipes = recipes;
                }
        );


    }


    @Override
    public void onItemClick(int position) {
        Intent recipeDetailIntent = new Intent(this, RecipeListActivity.class);
        recipeDetailIntent.putExtra(RecipeListActivity.RECIPE_KEY, recipes.get(position));
        recipeDetailIntent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) recipes.get(position).getIngredients());
        recipeDetailIntent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) recipes.get(position).getSteps());
        startActivity(recipeDetailIntent);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        return Math.max(nColumns, 2);
    }
}