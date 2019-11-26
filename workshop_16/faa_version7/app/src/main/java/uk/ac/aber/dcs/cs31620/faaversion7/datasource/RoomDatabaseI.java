package uk.ac.aber.dcs.cs31620.faaversion7.datasource;

import uk.ac.aber.dcs.cs31620.faaversion7.model.CatDao;

public interface RoomDatabaseI {
    abstract CatDao getCatDao();
    abstract void closeDb();
}
