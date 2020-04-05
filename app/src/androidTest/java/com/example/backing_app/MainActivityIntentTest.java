package com.example.backing_app;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.backing_app.fragment.RecipeListFragment.RECIPE_ID_KEY;
import static org.hamcrest.core.IsNot.not;


/**
 * In this Test class we are going to test that all the intents created in MainActivity have
 * the expected information
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mStepDetailRule =
            new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void stubIntent(){
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(not(isInternal())).respondWith(result);
    }

    /**
     * This test simulates the action of clicking on the first recipe available.
     * The intent to launch RecipeDetailActivity contains:
     * RECIPE_ID_INDEX pos+1 -> The index of the clicked recipe is pos+1 as the
     * recipe indexes start at 1 instead of 0
     */
    @Test
    public void clickOnRecipe_CreatesCorrectIntentInfo(){
        onView(withId(R.id.fragment_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasExtra(RECIPE_ID_KEY,1));
    }
}
