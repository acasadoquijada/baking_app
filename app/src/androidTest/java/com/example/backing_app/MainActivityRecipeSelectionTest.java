package com.example.backing_app;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;

import com.example.backing_app.fragment.IngredientListAdapter;
import com.example.backing_app.fragment.IngredientListFragment;
import com.example.backing_app.fragment.RecipeListAdapter;
import com.example.backing_app.fragment.RecipeListFragment;
import com.example.backing_app.recipe.Ingredient;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecipeSelectionTest {

    private static final String INGREDIENT_NAME = "Graham Cracker crumbs";
    private static final String STEP_NAME = "1. Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    /**
     * The purpose of this test is to ensure that a RecipeDetailActivity is opened when clicked
     * in one of the Views of the MainActivity layout.
     * <p>
     * It follows this patter:
     * <p>
     * MainActivityLayout:
     * - FrameLayout containing a RecyclerView (R.id.fragment_list)
     * - Once an element is clicked, a RecipeDetailActivity is opened
     * <p>
     * RecipeDetailActivity:
     * <p>
     * - Two FrameLayouts: ingredients_frame_layout and steps_frame_layout.
     * <p>
     * Both of them contains a RecyclerView (R.id.fragment_list) where the info is shown
     * <p>
     * First we click in the first element of the MainLayout RecyclerView and then we check
     * that the info in both RecipeDetail's FrameLayout is correct
     * <p>
     * The idea of using of only using onView() comes from this stackoverflow post and webpage:
     * https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
     * https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
     */


    @Test
    public void clickGridViewRecipe_OpenRecipeDetailActivity() {

        // Click and open RecipeDetailActivity
        onView(withId(R.id.fragment_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Select ingredients_frame_layout RecyclerView and check ingredient_name
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.ingredients_frame_layout))))
                .perform(RecyclerViewActions.
                        scrollToPosition(0)).check(matches(hasDescendant(withText(INGREDIENT_NAME))));

        // Select step_frame_layout RecyclerView and check step_name
        onView(allOf(withId(R.id.fragment_list), isDescendantOfA(withId(R.id.steps_frame_layout))))
                .perform(RecyclerViewActions.
                        scrollToPosition(0)).check(matches(hasDescendant(withText(STEP_NAME))));

    }
}
