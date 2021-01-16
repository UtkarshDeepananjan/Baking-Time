package com.uds.bakingtime.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.uds.bakingtime.R;
import com.uds.bakingtime.RecipeDetailFragment;
import com.uds.bakingtime.model.Steps;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private final List<Steps> steps;
    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm, int behavior, List<Steps> steps) {
        super(fm, behavior);
        this.steps=steps;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailFragment.CURRENT_POSITION, steps.get(position));
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

}
