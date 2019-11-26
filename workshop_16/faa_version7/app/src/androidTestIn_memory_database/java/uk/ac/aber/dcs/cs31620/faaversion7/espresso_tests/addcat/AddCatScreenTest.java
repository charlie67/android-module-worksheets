package uk.ac.aber.dcs.cs31620.faaversion7.espresso_tests.addcat;


import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import uk.ac.aber.dcs.cs31620.faaversion7.Injection;
import uk.ac.aber.dcs.cs31620.faaversion7.R;
import uk.ac.aber.dcs.cs31620.faaversion7.datasource.RoomDatabaseI;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.CatDao;
import uk.ac.aber.dcs.cs31620.faaversion7.ui.FAAMainActivity;
import uk.ac.aber.dcs.cs31620.faaversion7.ui.cats.listcats.CatsRecyclerWithListAdapter;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static uk.ac.aber.dcs.cs31620.faaversion7.model.Gender.MALE;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddCatScreenTest {

    private static final String TEST_CAT = "TEST CAT";
    private static final int NUM_CATS = 10;
    private CatDao catDao;
    private RoomDatabaseI db;

    @Rule
    public ActivityTestRule<FAAMainActivity> activityActivityTestRule =
            new ActivityTestRule<>(FAAMainActivity.class);

    @Before
    public void setup(){
        db = Injection.getDatabase(InstrumentationRegistry.getTargetContext());
        catDao = db.getCatDao();
        for (int i=0; i<NUM_CATS; i++) {
            catDao.insertSingleCat(new Cat("T" + i, MALE, "Moggie", new Date(), new Date(), "cat1.png", "Lorem ipsum dolor"));
        }
    }

    @After
    public void teardown(){
        db.closeDb();
    }

    @Test
    public void addCat_ShowsNewCatInCatsList(){
        // Find the Cats tabs and click on it
        onView(withText(R.string.cat_tab)).perform(click());

        // Now find and click on the FAB
        onView(withId(R.id.fab_add)).perform((click()));

        // Fill in the required fields
        // Name field
        onView(withId(R.id.catName)).perform(typeText(TEST_CAT), closeSoftKeyboard());

        // Click on the DatePickerDialog button
        onView(withId(R.id.datePicker)).perform(click());

        // This one forced me to add my own text to the buttons. Yes, I could
        // have just searched for OK, but that could change in the future. So
        // this was a useful change.
        onView(withText(R.string.ok_button_text)).perform(click());

        // We can now click the FAB to return to the Cats Fragment page
        onView(withId(R.id.add)).perform(click());

        // We should now scroll to the location of the new cat. The safest way is to
        // search for a ViewHolder that holds the TEST CAT text. We have to define
        // a BoundedMatcher which has a matchesSafely method that gets called for each item
        // in the RecyclerView. We compare the cat name in each holder with what we're
        // looking for. If true is returned then the test passes

        onView(withId(R.id.cat_list))
                .perform(RecyclerViewActions.scrollToHolder(getBoundedMatcher(TEST_CAT)));

    }

    public static Matcher<RecyclerView.ViewHolder> getBoundedMatcher(final String text){
        return new BoundedMatcher<RecyclerView.ViewHolder, CatsRecyclerWithListAdapter.ViewHolder>(CatsRecyclerWithListAdapter.ViewHolder.class) {
            @Override
            protected boolean matchesSafely(CatsRecyclerWithListAdapter.ViewHolder item) {
                TextView nameView = item.itemView.findViewById(R.id.catNameTextView);
                if (nameView == null){
                    return false;
                }
                return nameView.getText().toString().contains(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + text);
            }
        };
    }

}
