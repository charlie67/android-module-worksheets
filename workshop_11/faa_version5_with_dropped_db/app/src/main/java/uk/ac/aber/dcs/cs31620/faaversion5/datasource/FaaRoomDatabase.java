package uk.ac.aber.dcs.cs31620.faaversion5.datasource;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion5.datasource.util.AssetSQLiteOpenHelperFactory;
import uk.ac.aber.dcs.cs31620.faaversion5.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion5.model.CatDao;

import static uk.ac.aber.dcs.cs31620.faaversion5.model.Gender.MALE;
/**
 * The Room Database creation and initialisation class
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
// During production add exportSchema = false to the annotation to stop generation of schema
// to the project (see build.gradle for location)
// Change the version number if you make any changes to the database schema in
// any database file you wish to import from the assets folder
// I HAD TO CHANGE TO 2 TO MAKE THIS WORK SINCE ROOM CONSIDERED I'D MADE
// A SCHEMA CHANGE: I HAD REMOVED THE TABLE CALLED ROOM
@Database(entities = {Cat.class}, version = 2) //  exportSchema = false
public abstract class FaaRoomDatabase extends RoomDatabase{
    private static FaaRoomDatabase INSTANCE;
    private static Context myContext;

    private static final String DB_NAME = "faa_database";

    public abstract CatDao getCatDao();

    public static FaaRoomDatabase getDatabase(final Context context){
        // We use the Singleton design pattern so that we only every have one instance of the Room database
        myContext = context;

        if (INSTANCE == null){
            synchronized (FaaRoomDatabase.class){
                if (INSTANCE == null){
                    // For simplicity and to show it's possible, I've added support to allow queries on the
                    // main thread. It is bad practice to allow queries on the main thread since
                    // it can reduce UI performance. Normally, create an AsyncTask or use LiveData
                    //INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            //FaaRoomDatabase.class, DB_NAME).allowMainThreadQueries().addCallback(sRoomDatabaseCallback).build();
                    INSTANCE = createDatabase(context);
                    /* Do the following when migrating to a new version of the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FaaRoomDatabase.class, "faa_database").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
                    */
                }
            }
        }
        return INSTANCE;
    }

    private static FaaRoomDatabase createDatabase(Context context) {
        RoomDatabase.Builder<FaaRoomDatabase> builder =
                Room.databaseBuilder(context.getApplicationContext(), FaaRoomDatabase.class,
                        DB_NAME);

        // The AssetSQLiteOpenHelperFactory does the heavy lifting of the database file
        // from assets/databases to our /data/data/package-name/databases folder
        // It is important that the Room table
        return (builder.openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build());
    }

    // The following example is needed to copy in an updated sqlite database
    // See https://medium.com/google-developers/understanding-migrations-with-room-f01e04b07929
    private static final Migration MIGRATION_1_2 = new Migration(1,2){
        @Override
        public void migrate(SupportSQLiteDatabase database){
            Log.d("migrate", "Doing a migrate from version 1 to 2");
            // This is where we make relevant database data changes,
            // or copy data from old table to a new table.
            // Deals with the migration from version 1 to version 2
            // I MANAGED TO AVOID HAVING TO ADD THIS, HENCE COMMENTED OUT
            // IT SEEMED THOUGH THAT FOR THIS EXAMPLE ROOM REQUIRED A CHANGE
            // OF VERSION AND A CALL TO THIS MIGRATION EVEN THOUGH IT DOESN'T
            // DO ANYTHING.
            /*String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS 'cats' (" +
                    "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " 'name' TEXT," +
                    " 'gender' TEXT," +
                    " 'breed' TEXT," +
                    " 'description' TEXT," +
                    " 'dob' INTEGER, " +
                    " 'admission_date' INTEGER," +
                    " 'main_image_path' TEXT)" ;

            database.execSQL(SQL_CREATE_TABLE);*/
        }
    };

    // Gives us a way to initialise the database the first time
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);

                    // Let's build the database asynchronously in a separate thread
                    // We are not allowed to use Dao calls until onCreate returns
                    // so we cannot populate the database on the current main thread
                    //new PopulateDbAsync(INSTANCE).execute();
                }


            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CatDao catDao;

        PopulateDbAsync(FaaRoomDatabase db){
            catDao = db.getCatDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // This data will eventually come from a server

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -(365/2)); // Half a year old
            Date upTo1Year = cal.getTime();

            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -(365+(36/2))); // 1.5 years old
            Date from1to2Years = cal.getTime();

            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -(365*3)); // 3 years old
            Date from2to5Years = cal.getTime();

            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -(365*10)); // 10 years old
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
            catList.add(new Cat("Tibs", MALE, "Moggie", over5Years, admissionDate, "cat1.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

            catDao.insertMultipleCats(catList);
            return null;
        }
    }


}
