package com.example.daniellachacz.taskmvvm;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.daniellachacz.taskmvvm.view.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainScreenTest {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void floatingActionButton() {
        onView(withId(R.id.floating_action_button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewIsVisible() {
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void swipeItem() {
        onView(withId(R.id.recycler_view))
               .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
    }

    @Test
    public void checkItem() {
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.edit_task_activity))
        .check(matches(isDisplayed()));
    }

    @Test
    public void checkSize() {
        onView(withId(R.id.recycler_view)).check(matches(recyclerViewAtPositionOnView(5)));
    }

    public static Matcher<View> recyclerViewAtPositionOnView(final int matcherSize) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            protected boolean matchesSafely(RecyclerView item) {

                return matcherSize == item.getAdapter().getItemCount();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with list size: " + matcherSize);
            }
        };
    }

    @Test
    public void checkChild() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        ChildViewAction.clickChildViewWithId(R.id.edit_task_activity)));

    }

    public static class ChildViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();

                }
            };
        }
    }

}