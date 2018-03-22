package edu.oregonstate.choosy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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
                        ChoosyContract.Factors.COLUMN_COMP + " TEXT NOT NULL, " +
                        ChoosyContract.Factors.COLUMN_PRO + " TEXT NOT NULL, " +
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

    public void addDecision(DecisionUtils.decisionObject dec) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Insert
        String sqlSelection = ChoosyContract.Comparisons.COLUMN_FIRST + " = ?";
        String[] sqlSelectionArgs = { dec.firstOption };
        Cursor cursor = db.query(
                ChoosyContract.Comparisons.TABLE_NAME,
                null,
                sqlSelection,
                sqlSelectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() == 0) {
            //if entry doesn't already exist, add entry
            ContentValues values = new ContentValues();
            values.put(ChoosyContract.Comparisons.COLUMN_FIRST, dec.firstOption);
            values.put(ChoosyContract.Comparisons.COLUMN_SECOND, dec.secondOption);
            db.insert(ChoosyContract.Comparisons.TABLE_NAME, null, values);
            Log.d("ChoosyDatabase","Added decision "+dec.firstOption+" vs "+dec.secondOption+" to database.");
        }
        else
            Log.d("ChoosyDatabase","Decision already exists in database!");
    }

    public void addFactor(DecisionUtils.factorObject factor) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Insert
        String sqlSelection = ChoosyContract.Factors.COLUMN_NAME + " = ? AND " + ChoosyContract.Factors.COLUMN_COMP + " = ?";
        String[] sqlSelectionArgs = { factor.name, factor.comp };
        Cursor cursor = db.query(
                ChoosyContract.Factors.TABLE_NAME,
                null,
                sqlSelection,
                sqlSelectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() == 0) {
            //if factor entry doesn't already exist, add entry
            ContentValues values = new ContentValues();
            values.put(ChoosyContract.Factors.COLUMN_NAME, factor.name);
            values.put(ChoosyContract.Factors.COLUMN_COMP, factor.comp);
            values.put(ChoosyContract.Factors.COLUMN_PRO, factor.pro);
            values.put(ChoosyContract.Factors.COLUMN_WEIGHT, factor.weight);
            db.insert(ChoosyContract.Factors.TABLE_NAME, null, values);
            Log.d("ChoosyDatabase","Added factor "+ factor.name +" of decision "+ factor.comp +" to database.");
        }
        else
            Log.d("ChoosyDatabase","Factor already exists for decision "+ factor.comp + " in database!");
    }

    public ArrayList<DecisionUtils.decisionObject> getDecisions() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ChoosyContract.Comparisons.TABLE_NAME,
                null, null, null, null,
                null, ChoosyContract.Comparisons.COLUMN_TIMESTAMP + " DESC");

        ArrayList<DecisionUtils.decisionObject> vals = new ArrayList<>();
        DecisionUtils.decisionObject val;
        int index;
        while (cursor.moveToNext()) {
            String first = "";
            String second = "";

            index = cursor.getColumnIndex(ChoosyContract.Comparisons.COLUMN_FIRST);
            first = cursor.getString(index);

            index = cursor.getColumnIndex(ChoosyContract.Comparisons.COLUMN_SECOND);
            second = cursor.getString(index);

            val = new DecisionUtils.decisionObject(first, second);
            vals.add(val);

            Log.d("ChoosyDatabase","Retrieved "+val.firstOption+" vs "+val.secondOption+" from database.");
        }

        return vals;
    }
}
