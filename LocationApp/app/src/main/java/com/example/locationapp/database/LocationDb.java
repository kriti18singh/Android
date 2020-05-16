package com.example.locationapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.locationapp.database.DbManager.DB_TABLE;
import static com.example.locationapp.database.DbManager.LATITUDE;
import static com.example.locationapp.database.DbManager.LONGITUDE;
import static com.example.locationapp.database.DbManager._ID;

public class LocationDb extends SQLiteOpenHelper {
    private SQLiteDatabase mDatabase;

    public LocationDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql =     "create table " + DB_TABLE + " ( " +
                _ID + " integer primary key autoincrement , " +
                LONGITUDE + " double , " +
                LATITUDE + " double  " +
                " ) ";

        sqLiteDatabase.execSQL(sql);
        mDatabase = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //upgrade logic based on versions
    }
}
