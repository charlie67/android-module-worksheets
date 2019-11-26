package uk.ac.aber.dcs.cs31620.faaversion7;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import uk.ac.aber.dcs.cs31620.faaversion7.datasource.RoomDatabaseI;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.CatDao;

/**
 * The Room Database creation and initialisation class
 * @author Chris Loftus
 * @version 1, 26/08/2018.
 */
// During production add exportSchema = false to the annotation to stop generation of schema
// to the project (see build.gradle for location)
@Database(entities = {Cat.class}, version = 1) //  exportSchema = false
public abstract class FaaInMemoryRoomDatabase extends RoomDatabase implements RoomDatabaseI {
    private static FaaInMemoryRoomDatabase INSTANCE;

    @Override
    public abstract CatDao getCatDao();

    @Override
    public void closeDb(){
        INSTANCE.close();
        INSTANCE = null;
    }

    public static FaaInMemoryRoomDatabase getDatabase(final Context context){
        // We use the Singleton design pattern so that we only every have one instance of the Room database
        if (INSTANCE == null){
            synchronized (FaaInMemoryRoomDatabase.class){
                if (INSTANCE == null){
                    // For simplicity and to show it's possible, I've added support to allow queries on the
                    // main thread. It is bad practice to allow queries on the main thread since
                    // it can reduce UI performance. Normally, create an AsyncTask or use LiveData
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                            FaaInMemoryRoomDatabase.class).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }





}
