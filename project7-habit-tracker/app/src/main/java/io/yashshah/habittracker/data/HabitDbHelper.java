package io.yashshah.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import io.yashshah.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by yashshah on 21/01/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_PERSON_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_PERSON_HABIT + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_PERSON_GENDER + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor readHabits() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] projections = {
                HabitEntry._ID,
                HabitEntry.COLUMN_PERSON_NAME,
                HabitEntry.COLUMN_PERSON_HABIT,
                HabitEntry.COLUMN_PERSON_GENDER
        };

        Cursor cursor = sqLiteDatabase.query(
                HabitEntry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    public void insertHabit() {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitEntry.COLUMN_PERSON_NAME, "John");
        contentValues.put(HabitEntry.COLUMN_PERSON_HABIT, "Forgetting things");
        contentValues.put(HabitEntry.COLUMN_PERSON_GENDER, HabitEntry.GENDER_MALE);

        long newRowId = sqLiteDatabase.insert(HabitEntry.TABLE_NAME, null, contentValues);

        if (newRowId == -1) {
            Log.i(LOG_TAG, "Insertion failed!");
        } else {
            Log.i(LOG_TAG, "Insertion successful!");
        }
    }
}
