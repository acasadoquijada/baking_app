package com.example.backing_app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.getIntents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.backing_app.fragment.StepListFragment.RECIPE_INDEX_KEY;
import static com.example.backing_app.fragment.StepListFragment.STEP_INDEX_KEY;
import static org.hamcrest.core.AllOf.allOf;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.core.IsNot.not;

/**
 * In this Test class we are going to test that all the intents created in RecipeDetailActivity have
 * the expected information
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityIntentTest {

    private static final int STEP_NUMBER = 3;
    private static final int RECIPE_INDEX = 1;


    // Override idea taken from here: https://gist.github.com/grumpyshoe/fdcddeed2c70c0b2b0d69428ce83ecca
    // This is done as the StepDetailActivity needs to retrieve info from the intent that launches it

    @Rule
    public IntentsTestRule<StepDetailActivity> mStepDetailRule = new IntentsTestRule<StepDetailActivity>(StepDetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, StepDetailActivity.class);
            result.putExtra(STEP_INDEX_KEY, STEP_NUMBER);
            result.putExtra(RECIPE_INDEX_KEY, RECIPE_INDEX);
            return result;
        }
    };

    @Before
    public void stubIntent(){

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(not(isInternal())).respondWith(result);
    }

    /**
     * Check that the intent created to open a StepDetailActivity showing
     * the previous step contains the desired info:
     *
     * STEP_INDEX_KEY STEP_NUMBER-1 -> The step index is the previous one
     * RECIPE_INDEX_KEY RECIPE_INDEX -> The recipe index is 1
     */
    @Test
    public void previousButton_CreatesCorrectIntentInfo(){

        onView(withId(R.id.previous_button)).perform(click());

        intended(allOf(
                hasExtra(STEP_INDEX_KEY, STEP_NUMBER-1),hasExtra(RECIPE_INDEX_KEY, RECIPE_INDEX)));
    }

    /**
     * Check that the intent created to open a StepDetailActivity showing
     * the next step contains the desired info:
     *
     * STEP_INDEX_KEY STEP_NUMBER+1 -> The step index is the next one
     * RECIPE_INDEX_KEY RECIPE_INDEX -> The recipe index is 1
     */
    @Test
    public void nextButton_CreatesCorrectIntentInfo(){

        // Find the button and perform click

        onView(withId(R.id.next_button)).perform(click());

        intended(allOf(
                hasExtra(STEP_INDEX_KEY, STEP_NUMBER+1),hasExtra(RECIPE_INDEX_KEY, RECIPE_INDEX)));
    }
}
