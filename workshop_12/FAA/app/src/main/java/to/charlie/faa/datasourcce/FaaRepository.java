package to.charlie.faa.datasourcce;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import to.charlie.faa.model.Cat;
import to.charlie.faa.model.CatDao;

public class FaaRepository
{
	private CatDao catDao;

	public FaaRepository(Application application)
	{
		FaaRoomDatabase db = FaaRoomDatabase.getDatabase(application);
		catDao = db.getCatDao();
	}

	public LiveData<List<Cat>> getAllCats()
	{
		return catDao.getAllCats();
	}

	public LiveData<List<Cat>> getCats(String breed, String gender, Date startDate, Date endDate)
	{
		return catDao.getCats(breed, gender, startDate, endDate);
	}

	public LiveData<List<Cat>> getRecentCats(Date startDate, Date endDate)
	{
		return catDao.getCatsAdmittedBetweenDates(startDate, endDate);
	}

	public List<Cat> getRecentCatsSync(Date startDate, Date endDate)
	{
		return catDao.getCatsAdmittedBetweenDatesSync(startDate, endDate);
	}

	public LiveData<List<Cat>> getCatsByBreed(String breed)
	{
		return catDao.getCatsByBreed(breed);
	}

	public LiveData<List<Cat>> getCatsByGender(String gender)
	{
		return catDao.getCatsByGender(gender);
	}

	public LiveData<List<Cat>> getCatsByBreedAndGender(String breed, String gender)
	{
		return catDao.getCatsByBreedAndGender(breed, gender);
	}

	public LiveData<List<Cat>> getCatsBornBetweenDates(Date startDate, Date endDate)
	{
		return catDao.getCatsBornBetweenDates(startDate, endDate);
	}

	public LiveData<List<Cat>> getCatsByBreedAndBornBetweenDates(String breed, Date startDate, Date endDate)
	{
		return catDao.getCatsByBreedAndBornBetweenDates(breed, startDate, endDate);
	}

	public LiveData<List<Cat>> getCatsByGenderAndBornBetweenDates(String gender, Date startDate, Date endDate)
	{
		return catDao.getCatsByGenderAndBornBetweenDates(gender, startDate, endDate);
	}

	public LiveData<List<Cat>> getCatsByBreedAndGenderAndBornBetweenDates(String breed, String gender, Date startDate, Date endDate)
	{
		return catDao.getCatsByBreedAndGenderAndBornBetweenDates(breed, gender, startDate, endDate);
	}

	public void insert(Cat cat)
	{
		new InsertAsyncTask(catDao).execute(cat);
	}

	private static class InsertAsyncTask extends AsyncTask<Cat, Void, Void>
	{
		private CatDao mAsyncTaskDao;

		InsertAsyncTask(CatDao dao)
		{
			mAsyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Cat... params)
		{
			mAsyncTaskDao.insertMultipleCats(params);
			return null;
		}
	}
}
