package to.charlie.faa.datasourcce;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import to.charlie.faa.model.Cat;
import to.charlie.faa.model.CatDao;

import static to.charlie.faa.model.Gender.MALE;

@Database(entities = {Cat.class}, version = 1)
public abstract class FaaRoomDatabase extends RoomDatabase
{
	private static FaaRoomDatabase INSTANCE;

	public abstract CatDao getCatDao();

	private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
	{
		@Override
		public void onCreate(@NonNull SupportSQLiteDatabase db)
		{
			super.onCreate(db);
			new PopulateDbAsync(INSTANCE).execute();
		}
	};

	public static FaaRoomDatabase getDatabase(final Context context)
	{
		if (INSTANCE == null)
		{
			synchronized (FaaRoomDatabase.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FaaRoomDatabase.class, "faa_database").allowMainThreadQueries().addCallback(sRoomDatabaseCallback).build();
				 /* Do the following when migrating
				 to a new version of the database
				 INSTANCE =
				 Room.databaseBuilder(
				 context.getApplicationContext(),
				 FaaRoomDatabase.class,
				 "faa_database").addMigrations(MIGRATION_1_2, G
				 MIGRATION_2_3) H
				 .build();
				 */
				}
			}
		}
		return INSTANCE;
	}

	private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
	{
		private final CatDao catDao;

		PopulateDbAsync(FaaRoomDatabase db)
		{
			catDao = db.getCatDao();
		}

		@Override
		protected Void doInBackground(final Void... params)
		{
			// This data will eventually come from a server

			populateDatabase();
			return null;
		}

		private void populateDatabase()
		{

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -(365 / 2)); // Half a year old
			Date upTo1Year = cal.getTime();

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -(365 + (36 / 2))); // 1.5 years old
			Date from1to2Years = cal.getTime();

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -(365 * 3)); // 3 years old
			Date from2to5Years = cal.getTime();

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -(365 * 10)); // 10 years old
			Date over5Years = cal.getTime();

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -60); // Two months ago
			Date admissionDate = cal.getTime();

			Date veryRecentAdmission = new Date(); // Today!

			// Delete any existing test data. Not needed if onCreate rather than onOpen called
			//catDao.deleteAll();

			List<Cat> catList = new ArrayList<>();
			catList.add(new Cat("Tibs", MALE, "Moggie", upTo1Year, veryRecentAdmission, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", upTo1Year, veryRecentAdmission, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", from1to2Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", from1to2Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", from2to5Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", from2to5Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", over5Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
			catList.add(new Cat("Tibs", MALE, "Moggie", over5Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

			catDao.insertMultipleCats(catList);
		}
	}
}
