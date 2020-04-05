package com.example.backing_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.backing_app.fragment.StepListFragment.RECIPE_INDEX_KEY;
import static com.example.backing_app.fragment.StepListFragment.STEP_INDEX_KEY;
import static org.hamcrest.core.AllOf.allOf;

import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
/**
 * In this test class we are going to check that the info shown is correct
 * and that the actions that the user can perform behaves as expected
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    private static final String INGREDIENT_NAME = "Graham Cracker crumbs";
    private static final String STEP_NAME = "0. Recipe Introduction";
    private static final String CURRENT_STEP_TEXT = "Recipe Introduction";
    private static final String STEP_DESCRIPTION =
            "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup)" +
            " of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted " +
            "butter and 1 teaspoon of vanilla into the dry ingredients and stir together until " +
            "evenly mixed.";


    // This is done as the RecipeDetailActivity needs to retrieve info from the intent that launches it
    @Rule
    public ActivityTestRule<RecipeDetailActivity> recipeDetailRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeDetailActivity.class);
                    result.putExtra("recipe_id", 1);
                    return result;
                }
            };

    /**
     * Check that the Ingredient info in ingredients_frame_layout is correct
     */
    @Test
    public void ingredientFragmentLayoutShowsCorrectInfo(){
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.ingredients_frame_layout))))
                .perform(RecyclerViewActions.
                        scrollToPosition(0)).check(matches(hasDescendant(withText(INGREDIENT_NAME))));
    }

    @Test
    public void ingredientFragmentLayoutShowsCorrectInfo_AfterRotation(){
        recipeDetailRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ingredientFragmentLayoutShowsCorrectInfo();
    }

    /**
     * Check that the Ingredient info in ingredients_frame_layout is correct
     */
    @Test
    public void stepFragmentLayoutShowsCorrectInfo(){
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.
                        scrollToPosition(0)).check(matches(hasDescendant(withText(STEP_NAME))));
    }

    @Test
    public void stepFragmentLayoutShowsCorrectInfo_AfterRotation(){
        recipeDetailRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        stepFragmentLayoutShowsCorrectInfo();
    }

    /**
     * Check that clicking on one of the steps open a StepDetailActivity
     * We check that the step_description_frame_layout is displayed (this happens also in large
     * devices without launching StepDetailActivity
     */
    @Test
    public void clickOnStep_OpenStepDetailActivity(){

        // Click on the first step
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_description_frame_layout)).check(matches(isDisplayed()));

    }

    @Test
    public void clickOnStep_OpenStepDetailActivity_AfterRotation() {
        recipeDetailRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Click on the first step
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.video_frame_layout)).check(matches(isDisplayed()));

    }
        /**
         * Check the StepDetailActivity launched contains the correct info
         */
    @Test
    public void clickOnStep_OpenStepDetailActivityWithCorrectInfo() {
        // Click on the first step
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.step_description), isDescendantOfA(withId(R.id.step_description_frame_layout))))
                .check(matches(withText(CURRENT_STEP_TEXT)));
    }

    /**
     * Check the StepDescriptionFragment is updated when a step is clicked (Only for larger devices)
     */
    @Test
    public void clickOnStep_UpdatesFragmentInfo(){
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(allOf(withId(R.id.step_description), isDescendantOfA(withId(R.id.step_description_frame_layout))))
            .check(matches((withText(STEP_DESCRIPTION))));
    }

    /**
     * Should be run only in large devices because in small devices the landscape layout for
     * StepDetailActivity only contains a video_frame_layout
     */

    @Test
    public void clickOnStep_UpdatesFragmentInfo_AfterRotation(){

        recipeDetailRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        clickOnStep_UpdatesFragmentInfo();
    }
}