package com.uds.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.uds.bakingtime.Utils.ItemClickListener;
import com.uds.bakingtime.adapters.RecipeStepsAdapter;
import com.uds.bakingtime.model.Ingredient;
import com.uds.bakingtime.model.Recipe;
import com.uds.bakingtime.model.Steps;
import com.uds.bakingtime.widget.RecipeWidgetService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements ItemClickListener {

    public static final String RECIPE_KEY = "RECIPE_KEY";
    public static final String SAVED_INSTANCE_RECIPE_KEY = "SAVED_RECIPE_STEPS";
    private boolean mTwoPane;
    private List<Steps> steps;
    private List<Ingredient> ingredients;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        } else {
            recipe = savedInstanceState.getParcelable(SAVED_INSTANCE_RECIPE_KEY);
        }
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
        setTitle(recipe.getName());
        setupIngredients(ingredients);
        setupSteps(steps);
        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.CURRENT_POSITION, steps.get(0));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        } else
            mTwoPane = false;

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        recipe = intent.getParcelableExtra(RECIPE_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.destail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, RecipeListActivity.class));
            return true;
        }
        if (id == R.id.action_add_to_widget) {
            RecipeWidgetService.updateWidget(this, recipe);
            Toast.makeText(this, recipe.getName() + " added to widget", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupSteps(List<Steps> steps) {
        RecyclerView recyclerView = findViewById(R.id.rv_steps);
        recyclerView.setAdapter(new RecipeStepsAdapter(steps, this));
    }

    private void setupIngredients(List<Ingredient> ingredients) {
        StringBuilder recipe = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            String s = ingredient.getIngredient() + " (" + new DecimalFormat("#.##").format(ingredient.getQuantity()) + "\u00A0" + ingredient.getMeasure() + ")\n";
            recipe.append(s);
        }
        TextView ingredientsTextView = findViewById(R.id.tv_ingredients);
        ingredientsTextView.setText(recipe.toString());
    }


    @Override
    public void onItemClick(int position) {
        if (mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(RecipeDetailFragment.CURRENT_POSITION, steps.get(position));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
            bundle.putInt("step_pos", position);
            bundle.putString("recipe_name", recipe.getName());
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_RECIPE_KEY, recipe);
    }
}