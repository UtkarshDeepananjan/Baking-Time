package com.uds.bakingtime;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.uds.bakingtime.adapters.RecipeAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {

    @Rule
    public ActivityScenarioRule<MainActivity> intentsTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Click a Recipe in the Mainactivity and check if the DetailActivity is opened properly
     */
    @Test
    public void clickRecipeItem_openRecipeDetail() {
        onData(instanceOf(RecipeAdapter.class)) .atPosition(0) .perform(click());

        //Check if the ingredients and the steps are displayed
        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
    }

    /**
     * Click the Recipe and check if it sends a recipe intent extra
     */
    @Test
    public void clickRecipeItem_hasRecipeIntent() {
        onData(instanceOf(RecipeAdapter.class)) .atPosition(0) .perform(click());
        intended(hasComponent(RecipeDetailActivity.class.getName()));
        intended(hasExtraWithKey(RecipeDetailActivity.DETAIL_RECIPE_KEY));
    }
}
