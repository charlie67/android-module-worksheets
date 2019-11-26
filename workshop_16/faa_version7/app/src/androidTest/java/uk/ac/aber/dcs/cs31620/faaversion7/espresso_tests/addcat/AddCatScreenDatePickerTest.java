package uk.ac.aber.dcs.cs31620.faaversion7.espresso_tests.addcat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.aber.dcs.cs31620.faaversion7.R;
import uk.ac.aber.dcs.cs31620.faaversion7.ui.cats.addcat.AddCatActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest   // Use this annotation is execution time > 1000ms, e.g. where db access
public class AddCatScreenDatePickerTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<AddCatActivity> addCatsActivityTestRule =
            new ActivityTestRule<>(AddCatActivity.class);

    // This test forced me to add a title to the DatePickerDialog, so that
    // it can be found. This is good anyway for accessibility reasons.
    @Test
    public void clickDatePickerButton_opensDatePicker() throws Exception {
        // Click on the date picker button
        onView(withId(R.id.datePicker)).perform(click());

        // Check that the date picker is displayed. We look for its title text
        onView(withText(R.string.date_picker_title)).check(matches(isDisplayed()));
    }

}
