package com.uds.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.uds.bakingtime.R;
import com.uds.bakingtime.model.Ingredient;
import com.uds.bakingtime.model.Recipe;

import java.util.List;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientsRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Recipe recipe = WidgetPrefs.loadRecipe(mContext);
        if(recipe != null) {
            mIngredients = recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mIngredients.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item);

        rv.setTextViewText(R.id.tv_widget_ingredient_name, ingredient.toString());
        rv.setOnClickFillInIntent(R.id.tv_widget_ingredient_name, new Intent());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
