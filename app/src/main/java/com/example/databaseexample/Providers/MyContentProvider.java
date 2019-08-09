package com.example.databaseexample.Providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.databaseexample.Activities.MainActivity;
import com.example.databaseexample.Contract.StudentContract;
import com.example.databaseexample.Database.StudentDBHelper;

public class MyContentProvider extends ContentProvider {

    private StudentDBHelper dbHelper;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbHelper.getWritableDatabase().delete(StudentContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        dbHelper = new StudentDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        return dbHelper.getReadableDatabase().query(StudentContract.StudentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                sortOrder,
                null,
                null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
