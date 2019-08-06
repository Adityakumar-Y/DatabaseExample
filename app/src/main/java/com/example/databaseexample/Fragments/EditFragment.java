package com.example.databaseexample.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.databaseexample.Activities.MainActivity;
import com.example.databaseexample.Contract.StudentContract.*;

import com.example.databaseexample.Database.StudentDBHelper;
import com.example.databaseexample.R;
import com.example.databaseexample.Models.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditFragment extends Fragment implements View.OnClickListener {

    private View view;
    private FloatingActionButton fab;
    private TextInputLayout etName, etEmail, etPhone;
    private Button btnUpdate;
    private Bundle bundle;
    private long id;
    private int position;
    private SQLiteDatabase mDatabase;
    private Cursor cursor;
    private String name, email, phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container, false);
        init(view);
        hideFab();
        fillData();
        return view;
    }

    private void fillData() {
        bundle = getArguments();
        position = bundle.getInt("position");
        cursor = ((MainActivity)getActivity()).getAllItems();
        cursor.moveToPosition(position);

        name = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_NAME));
        email = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_EMAIL));
        phone = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_PHONE));
        id = cursor.getLong(cursor.getColumnIndex(StudentEntry._ID));

        etName.getEditText().setText(name);
        etEmail.getEditText().setText(email);
        etPhone.getEditText().setText(phone);
    }

    private void init(View v) {

        StudentDBHelper dbHelper = new StudentDBHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        fab = getActivity().findViewById(R.id.fab);
        etName = v.findViewById(R.id.EditNameLayout);
        etEmail = v.findViewById(R.id.EditEmailLayout);
        etPhone = v.findViewById(R.id.EditPhoneLayout);
        btnUpdate = v.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(this);
    }

    @SuppressLint("RestrictedApi")
    private void hideFab() {
        if (fab.getVisibility() == View.VISIBLE) {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d("EditFrag", id+"");
        /*Student student = new Student(
                etName.getEditText().getText().toString(),
                etEmail.getEditText().getText().toString(),
                etPhone.getEditText().getText().toString()
        );*/
        name = etName.getEditText().getText().toString();
        email = etEmail.getEditText().getText().toString();
        phone = etPhone.getEditText().getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, name);
        cv.put(StudentEntry.COLUMN_EMAIL, email);
        cv.put(StudentEntry.COLUMN_PHONE, phone);

        mDatabase.update(
                StudentEntry.TABLE_NAME,
                cv,
                StudentEntry._ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        //((MainActivity) getActivity()).studentList.set(position, student);
        //((MainActivity) getActivity()).mAdapter.notifyDataSetChanged();
        ((MainActivity) getActivity()).mAdapter.swapCursor(((MainActivity)getActivity()).getAllItems());
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPause() {
        super.onPause();
        fab.setVisibility(View.VISIBLE);
    }
}
