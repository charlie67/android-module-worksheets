package uk.ac.aber.dcs.cs31620.faaversion7;

import android.content.Context;

import uk.ac.aber.dcs.cs31620.faaversion7.datasource.RoomDatabaseI;

public class Injection {

    public static RoomDatabaseI getDatabase(Context context) {
        return FaaPersistentRoomDatabase.getDatabase(context);
    }
}
