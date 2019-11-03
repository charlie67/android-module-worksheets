package uk.ac.aber.dcs.cs31620.faaversion5.datasource.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

class AssetSQLiteOpenHelper implements SupportSQLiteOpenHelper {
    private final AssetHelper delegate;

    AssetSQLiteOpenHelper(Context context, String name, int version,
                          Callback callback) {
        delegate = createDelegate(context, name, version, callback);
    }

    private AssetHelper createDelegate(Context context, String name,
                                       int version, final Callback callback) {
        return new AssetHelper(context, name, version) {
            @Override
            public final void onCreate(SQLiteDatabase db) {
                wrappedDb = new FrameworkSQLiteDatabase(db);
                callback.onCreate(wrappedDb);
            }

            @Override
            public final void onUpgrade(SQLiteDatabase db, int oldVersion,
                                        int newVersion) {
                callback.onUpgrade(getWrappedDb(db), oldVersion,
                        newVersion);
            }

            @Override
            public void onConfigure(SQLiteDatabase db) {
                callback.onConfigure(getWrappedDb(db));
            }

            @Override
            public final void onDowngrade(SQLiteDatabase db, int oldVersion,
                                          int newVersion) {
                callback.onDowngrade(getWrappedDb(db), oldVersion, newVersion);
            }

            @Override
            public void onOpen(SQLiteDatabase db) {
                callback.onOpen(getWrappedDb(db));
            }
        };
    }

    @Override
    public String getDatabaseName() {
        return delegate.getDatabaseName();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        delegate.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        return delegate.getWritableSupportDatabase();
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        return delegate.getReadableSupportDatabase();
    }

    @Override
    public void close() {
        delegate.close();
    }

    abstract static class AssetHelper extends SQLiteAssetHelper {
        FrameworkSQLiteDatabase wrappedDb;

        AssetHelper(Context context, String name, int version) {
            super(context, name, null, null, version, null);
        }

        SupportSQLiteDatabase getWritableSupportDatabase() {
            SQLiteDatabase db = super.getWritableDatabase();
            return getWrappedDb(db);
        }

        SupportSQLiteDatabase getReadableSupportDatabase() {
            SQLiteDatabase db = super.getReadableDatabase();
            return getWrappedDb(db);
        }

        FrameworkSQLiteDatabase getWrappedDb(SQLiteDatabase sqLiteDatabase) {
            if (wrappedDb == null) {
                wrappedDb = new FrameworkSQLiteDatabase(sqLiteDatabase);
            }
            return wrappedDb;
        }

        @Override
        public synchronized void close() {
            super.close();
            wrappedDb = null;
        }
    }
}
