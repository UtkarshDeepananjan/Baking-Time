package com.uds.bakingtime;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.uds.bakingtime.adapters.ScreenSlidePagerAdapter;
import com.uds.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.uds.bakingtime.model.Steps;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailActivity extends AppCompatActivity {
    public static final String DETAIL_RECIPE_KEY = "RECIPE_DETAIL_KEY";
    private ActionBar actionBar;
    private int currentStepPos;
    private List<Steps> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecipeDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            steps = bundle.getParcelableArrayList("steps");
            currentStepPos = bundle.getInt("step_pos");
        } else {
            steps = savedInstanceState.getParcelableArrayList("steps");
        }

        binding.pager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), 1, steps));
        binding.pager.setCurrentItem(currentStepPos);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideStatusBar(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            hideStatusBar(false);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
    }

    private void hideStatusBar(boolean status) {
        View decorView = getWindow().getDecorView();
        if (status) {
            actionBar.hide();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } else {
            actionBar.show();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}