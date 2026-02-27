package com.example.androiduitesting;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    // helper: add a city to the list
    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
    }

    // 1) Check whether the activity correctly switched
    @Test
    public void clickingCity_switchesToShowActivity() {
        addCity("Edmonton");

        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        intended(hasComponent(ShowActivity.class.getName()));
    }

    // 2) Test whether the city name is consistent
    @Test
    public void clickedCityName_isDisplayedInShowActivity() {
        addCity("Toronto");

        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.city_name_text)).check(matches(withText("Toronto")));
    }

    // 3) Test the "back" button
    @Test
    public void backButton_returnsToMainActivity() {
        addCity("Berlin");

        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.back_button)).perform(click());

        // Back on MainActivity: buttons should be visible
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
        onView(withId(R.id.button_clear)).check(matches(isDisplayed()));
    }
}