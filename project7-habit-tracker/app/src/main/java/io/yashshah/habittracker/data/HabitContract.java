package io.yashshah.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by yashshah on 21/01/17.
 */

public final class HabitContract {

    // Make constructor private so no one can create an instance of this class.
    private HabitContract() {
    }

    public class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_PERSON_NAME = "name";
        public static final String COLUMN_PERSON_HABIT = "habit";
        public static final String COLUMN_PERSON_GENDER = "gender";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
