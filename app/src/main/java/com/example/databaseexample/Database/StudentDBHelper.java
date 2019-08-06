package com.example.databaseexample.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.databaseexample.Contract.StudentContract.*;

import com.example.databaseexample.Contract.StudentContract;

public class StudentDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Students.db";
    public static final int DATABASE_VERSION = 1;

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE "+
                StudentEntry.TABLE_NAME + " (" +
                StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                StudentEntry.COLUMN_NAME + " TEXT NOT NULL, "+
                StudentEntry.COLUMN_EMAIL + " TEXT NOT NULL, "+
                StudentEntry.COLUMN_PHONE + " TEXT NOT NULL"+
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ StudentEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
