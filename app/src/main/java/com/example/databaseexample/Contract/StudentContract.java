package com.example.databaseexample.Contract;

import android.provider.BaseColumns;

public class StudentContract {

    private StudentContract() {}

    public static final class StudentEntry implements BaseColumns{
        public static final String TABLE_NAME = "studentList";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_PHONE = "Phone";
    }
}
