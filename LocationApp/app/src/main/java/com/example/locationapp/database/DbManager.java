package com.example.locationapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {

    private static final String DATABASE_NAME = "location_database";
    private static final int DATABASE_VERSION = 1;

    public static final String _ID = "_id";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String DB_TABLE = "locations";

    private Context mContext;
    private LocationDb mDbHelper;
    private SQLiteDatabase mDb;

    public DbManager(Context ctx) {
        this.mContext = ctx;
    }

    public DbManager open() {
        mDbHelper = new LocationDb(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long insertLocation(double lat, double lng) {
        ContentValues values = new ContentValues();
        values.put(LATITUDE, lat);
        values.put(LONGITUDE, lng);

        // insert row
        long id = mDb.insert(DB_TABLE, null, values);

        mDb.close();

        return id;
    }
}
