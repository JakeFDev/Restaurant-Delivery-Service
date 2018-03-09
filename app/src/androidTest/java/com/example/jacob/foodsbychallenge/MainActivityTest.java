package com.example.jacob.foodsbychallenge;

import android.graphics.Color;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.espresso.Espresso;
import android.util.Log;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;

/**
 * Simple test suite using Espresso to check validity of Elements on the screen as
 * well as some of their contents.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void LocationTest() {
        onView(withId(R.id.locationIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.locationLine1)).check(matches(withText(R.string.locationMain)));
        onView(withId(R.id.locationLine2)).check(matches(withText(R.string.locationSecondary)));
    }

    @Test
    public void dayOfWeekButtonsTest() {
        onView(withId(R.id.buttonContainer)).check(matches(isDisplayed()));
        onView(withId(R.id.mondayBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.tuesdayBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.wednesdayBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.thursdayBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.fridayBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testMondayDeliveries() {
        onView(withId(R.id.mondayBtn)).perform(click());
        onView(withText("Mort's Delicatessen")).check(matches(isDisplayed()));
        onView(withText("09:30 AM")).check(matches(isDisplayed()));
        onView(withText("11:35 AM")).check(matches(isDisplayed()));
        onView(withText("Selling Out!")).check(matches(isDisplayed()));
    }

    @Test
    public void testChangingDay() throws Exception {
        onView(withId(R.id.fridayBtn)).perform(click());
        onView(withText("Granite City")).check(matches(isDisplayed()));
        onView(withText("11:30 AM")).check(matches(isDisplayed()));

        onView(withId(R.id.wednesdayBtn)).perform(click());
        Thread.sleep(2500);
        onView(withText("Living Waters Market & Cafe")).check(doesNotExist());
        onView(withText("10:15 AM")).check(doesNotExist());
        onView(withText("Past Cutoff")).check(doesNotExist());
    }


}
