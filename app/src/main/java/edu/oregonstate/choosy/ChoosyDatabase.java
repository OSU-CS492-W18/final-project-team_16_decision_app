package edu.oregonstate.choosy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samuel on 3/19/2018.
 */

public class ChoosyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "choosy.db";
    private static final int DATABASE_VERSION = 1;

    public ChoosyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Comparisons table
        final String SQL_CREATE_COMPARISONS_TABLE =
                "CREATE TABLE " + ChoosyContract.Comparisons.TABLE_NAME +
                        " (" + ChoosyContract.Comparisons._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ChoosyContract.Comparisons.COLUMN_FIRST + " TEXT NOT NULL, " +
                        ChoosyContract.Comparisons.COLUMN_SECOND + " TEXT NOT NULL, " +
                        ChoosyContract.Comparisons.COLUMN_TIMESTAMP +
                        " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");";
        //Create Factors table
        final String SQL_CREATE_FACTORS_TABLE =
                "CREATE TABLE " + ChoosyContract.Factors.TABLE_NAME +
                        " (" + ChoosyContract.Factors._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ChoosyContract.Factors.COLUMN_NAME + " TEXT NOT NULL, " +
                        ChoosyContract.Factors.COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                        ChoosyContract.Factors.COLUMN_TIMESTAMP +
                        " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");";

        //Create tables
        db.execSQL(SQL_CREATE_COMPARISONS_TABLE);
        db.execSQL(SQL_CREATE_FACTORS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int cur) {
        //do nothing
    }
}
