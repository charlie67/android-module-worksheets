package uk.ac.aber.dcs.cs31620.faaversion6.datasource;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion6.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion6.model.CatDao;

/**
 * FAA repository. Recommended that we have an abstraction layer between
 * UI ViewModels and the data source: relational database or network.
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
public class FaaRepository {
    private CatDao catDao;

    public FaaRepository(Application application){
        FaaRoomDatabase db = FaaRoomDatabase.getDatabase(application);
        catDao = db.getCatDao();
    }

    public void insert(Cat cat) {
        // We start a thread before inserting using the DAO
        new InsertAsyncTask(catDao).execute(cat);
    }

    public LiveData<List<Cat>> getAllCats() {
        return catDao.getAllCats();
    }

    public LiveData<List<Cat>> getCats(String breed, String gender, Date startDate, Date endDate) {
        return catDao.getCats(breed, gender, startDate, endDate);
    }

    public LiveData<List<Cat>> getRecentCats(Date startDate, Date endDate) {
        return catDao.getCatsAdmittedBetweenDates(startDate, endDate);
    }

    public List<Cat> getRecentCatsSync(Date startDate, Date endDate) {
        return catDao.getCatsAdmittedBetweenDatesSync(startDate, endDate);
    }

    public LiveData<List<Cat>> getCatsByBreed(String breed) {
        return catDao.getCatsByBreed(breed);
    }

    public LiveData<List<Cat>> getCatsByGender(String gender) {
        return catDao.getCatsByGender(gender);
    }

    public LiveData<List<Cat>> getCatsByBreedAndGender(String breed, String gender) {
        return catDao.getCatsByBreedAndGender(breed, gender);
    }

    public LiveData<List<Cat>> getCatsBornBetweenDates(Date startDate, Date endDate) {
        return catDao.getCatsBornBetweenDates(startDate, endDate);
    }

    public LiveData<List<Cat>> getCatsByBreedAndBornBetweenDates(String breed, Date startDate, Date endDate) {
        return catDao.getCatsByBreedAndBornBetweenDates(breed, startDate, endDate);
    }

    public LiveData<List<Cat>> getCatsByGenderAndBornBetweenDates(String gender, Date startDate, Date endDate) {
        return catDao.getCatsByGenderAndBornBetweenDates(gender, startDate, endDate);
    }

    public LiveData<List<Cat>> getCatsByBreedAndGenderAndBornBetweenDates(String breed, String gender, Date startDate, Date endDate) {
        return catDao.getCatsByBreedAndGenderAndBornBetweenDates(breed, gender, startDate, endDate);
    }

    private static class InsertAsyncTask extends AsyncTask<Cat, Void, Void> {

        private CatDao mAsyncTaskDao;

        InsertAsyncTask(CatDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cat... params) {
            mAsyncTaskDao.insertMultipleCats(params);
            return null;
        }
    }
}
