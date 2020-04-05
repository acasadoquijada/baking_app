package com.example.backing_app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.backing_app.fragment.StepListFragment.RECIPE_INDEX_KEY;
import static com.example.backing_app.fragment.StepListFragment.STEP_INDEX_KEY;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * In this Test class we are going to test that all the intents created in RecipeDetailActivity have
 * the expected information
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityIntentTest {

    // This is done as the RecipeDetailActivity needs to retrieve info from the intent that launches it
    @Rule
    public IntentsTestRule<RecipeDetailActivity> recipeDetailRule =
            new IntentsTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeDetailActivity.class);
                    result.putExtra("recipe_id", 1);
                    return result;
                }
            };

    @Before
    public void stubIntent(){

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(not(isInternal())).respondWith(result);
    }

    /**
     * Check that the intent created to open a StepDetailActivity contains the desired info:
     * STEP_INDEX_KEY 0 -> The step index is 0
     * RECIPE_INDEX_KEY 1 -> The recipe index is 1
     */
    @Test
    public void clickOnStep_CreatesCorrectIntentInfo(){

        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(
                hasExtra(STEP_INDEX_KEY, 0),hasExtra(RECIPE_INDEX_KEY, 1)));
    }

}
