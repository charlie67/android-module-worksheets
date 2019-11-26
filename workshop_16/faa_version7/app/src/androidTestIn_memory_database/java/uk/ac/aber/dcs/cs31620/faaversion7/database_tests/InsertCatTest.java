package uk.ac.aber.dcs.cs31620.faaversion7.database_tests;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion7.Injection;
import uk.ac.aber.dcs.cs31620.faaversion7.database_tests.util.LiveDataTestUtil;
import uk.ac.aber.dcs.cs31620.faaversion7.database_tests.util.TestUtil;
import uk.ac.aber.dcs.cs31620.faaversion7.datasource.RoomDatabaseI;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.CatDao;

import static junit.framework.Assert.assertEquals;

/**
 * Tests whether cat insertions via Dao into the database
 * really adds the cats. We check whether the we can get the cats back.
 */
@RunWith(AndroidJUnit4.class)
public class InsertCatTest {
    // This is a JUnit Test Rules that swaps the background executor used by the Architecture
    // Components with one that executes synchronously instead.
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CatDao catDao;
    private RoomDatabaseI db;

    private TestUtil testUtil = new TestUtil();

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();

        db = Injection.getDatabase(context);
        catDao = db.getCatDao();
    }

    @After
    public void closeDb() throws IOException {
        db.closeDb();
    }

    @Test
    public void onInsertingACat_checkThat_catWasInserted() throws Exception {
        List<Cat> cats = testUtil.createMaleRecent(1, 365);

        catDao.insertSingleCat(cats.get(0));

        LiveData<List<Cat>> foundCats = catDao.getAllCats();
        Assert.assertEquals(1, LiveDataTestUtil.getValue(foundCats).size());
    }

    @Test
    public void onInsertingTwoCatsWithDifferentBreeds_checkThat_weCanFindByBreed() throws Exception {
        List<Cat> cats = testUtil.createMaleRecent(2, 365);
        Cat cat2 = cats.get(1);
        cat2.setBreed("TEST-BREED");

        catDao.insertMultipleCats(cats);

        LiveData<List<Cat>> foundCats = catDao.getCatsByBreed("TEST-BREED");
        assertEquals(1, LiveDataTestUtil.getValue(foundCats).size());
    }

    @Test
    public void onInsertCatsOfDifferentAges_checkThat_weCanFindForDifferentAgeDateRanges() throws Exception {
        List<Cat> halfYearCats = testUtil.createMaleRecent(2, (int)365/2);
        List<Cat> oneYearCats = testUtil.createMaleRecent(2, 365);
        List<Cat> fiveYearCats = testUtil.createMaleRecent(2, 365*5);

        catDao.insertMultipleCats(halfYearCats);
        catDao.insertMultipleCats(oneYearCats);
        catDao.insertMultipleCats(fiveYearCats);

        // Up to one year cats (startDate, endDate)
        LiveData<List<Cat>> foundCats = catDao.getCatsBornBetweenDates(testUtil.createDate(365-1), new Date());
        assertEquals("Up to one year cats: failed", 2, LiveDataTestUtil.getValue(foundCats).size());

        // From one to two year cats
        foundCats = catDao.getCatsBornBetweenDates(testUtil.createDate(365*2-1), testUtil.createDate(365));
        assertEquals("From one to two year cats: failed", 2, LiveDataTestUtil.getValue(foundCats).size());

        // Over five year cats
        foundCats = catDao.getCatsBornBetweenDates(testUtil.createDate(365*10), testUtil.createDate(365*5));
        assertEquals("Over five year cats: failed", 2, LiveDataTestUtil.getValue(foundCats).size());

        // Make start and end dates exactly same as cat dob
        foundCats = catDao.getCatsBornBetweenDates(testUtil.createDate(365*5), testUtil.createDate(365*5));
        assertEquals("Exactly five year cats: failed", 2, LiveDataTestUtil.getValue(foundCats).size());
    }

    // Add more database tests here....
}
