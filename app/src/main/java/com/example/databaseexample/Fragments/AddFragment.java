package com.example.databaseexample.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.databaseexample.Activities.MainActivity;
import com.example.databaseexample.Contract.StudentContract;
import com.example.databaseexample.Database.StudentDBHelper;
import com.example.databaseexample.R;
import com.example.databaseexample.Models.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.example.databaseexample.Contract.StudentContract.*;

public class AddFragment extends Fragment implements View.OnClickListener {

    private View view;
    private FloatingActionButton fab;
    private TextInputLayout etName, etEmail, etPhone;
    private Button btnAdd;
    private String studName, studEmail, studPhone;
    private SQLiteDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        init(view);
        hideFab();
        return view;
    }


    @SuppressLint("RestrictedApi")
    private void hideFab() {
        if (fab.getVisibility() == View.VISIBLE) {
            fab.setVisibility(View.GONE);
        }
    }

    private void init(View view) {

        StudentDBHelper dbHelper = new StudentDBHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        etName = view.findViewById(R.id.etNameLayout);
        etEmail = view.findViewById(R.id.etEmailLayout);
        etPhone = view.findViewById(R.id.etPhoneLayout);
        btnAdd = view.findViewById(R.id.btnAdd);
        fab = getActivity().findViewById(R.id.fab);

        btnAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        // Add to the database
        studName = etName.getEditText().getText().toString();
        studEmail = etEmail.getEditText().getText().toString();
        studPhone = etPhone.getEditText().getText().toString();
        /*Student student = new Student(studName, studEmail, studPhone);
        ((MainActivity)getActivity()).studentList.add(student);*/

        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, studName);
        cv.put(StudentEntry.COLUMN_EMAIL, studEmail);
        cv.put(StudentEntry.COLUMN_PHONE, studPhone);

        mDatabase.insert(StudentEntry.TABLE_NAME, null, cv);
        ((MainActivity) getActivity()).mAdapter.swapCursor(((MainActivity) getActivity()).getAllItems());
        //((MainActivity)getActivity()).mAdapter.notifyDataSetChanged();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPause() {
        super.onPause();
        fab.setVisibility(View.VISIBLE);
    }


}
