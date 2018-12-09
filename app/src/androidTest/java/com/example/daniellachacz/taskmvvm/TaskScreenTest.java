package com.example.daniellachacz.taskmvvm;

import com.example.daniellachacz.taskmvvm.view.MainActivity;
import com.example.daniellachacz.taskmvvm.view.TaskActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TaskScreenTest {

    @Rule
    public ActivityTestRule<TaskActivity> taskActivityActivityTestRule =
            new ActivityTestRule<>(TaskActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addOnlyDescriptionTask() {
        onView(withId(R.id.description_text)).perform(typeText("Task 1")).perform(closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("Task saved"));
    }

    @Test
    public void addFullTask() {
        onView(withId(R.id.description_text)).perform(typeText("Task 2")).perform(closeSoftKeyboard());
        onView(withId(R.id.date_text)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.time_text)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("Task saved")).perform(pressBack());
    }

    @Test
    public void addEmptyTask() {
        onView(withId(R.id.description_text)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("Title is empty!"));
    }

}
