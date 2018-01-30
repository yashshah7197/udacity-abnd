package io.yashshah.habittracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.yashshah.habittracker.data.HabitContract.HabitEntry;
import io.yashshah.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);

        mDbHelper.insertHabit();
        displayHabits(mDbHelper.readHabits());
    }

    private void displayHabits(Cursor cursor) {

        try {
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_PERSON_NAME);
            int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_PERSON_HABIT);
            int genderColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_PERSON_GENDER);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentHabit = cursor.getString(habitColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);

                Log.i(LOG_TAG, currentID + "\n" + currentName + "\n" + currentHabit + "\n" + currentGender + "\n\n");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "There was an unexpected error!", e);
        } finally {
            cursor.close();
        }
    }
}
