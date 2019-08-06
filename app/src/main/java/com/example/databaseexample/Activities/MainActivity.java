package com.example.databaseexample.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.databaseexample.Contract.StudentContract;
import com.example.databaseexample.Database.StudentDBHelper;
import com.example.databaseexample.Fragments.AddFragment;
import com.example.databaseexample.Fragments.EditFragment;
import com.example.databaseexample.R;
import com.example.databaseexample.Models.Student;
import com.example.databaseexample.Adapters.StudentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StudentAdapter.OnItemClickListener {

    private SQLiteDatabase mDatabase;
    private RecyclerView recyclerView;
    public FloatingActionButton fab;
    public List<Student> studentList;
    public StudentAdapter mAdapter;
    private StudentDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupAdpater();
    }

    private void init() {
        dbHelper = new StudentDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        recyclerView = (RecyclerView) findViewById(R.id.student_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        studentList = new ArrayList<Student>();

        fab.setOnClickListener(this);
    }

    private void setupAdpater() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StudentAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    public Cursor getAllItems(){
        return mDatabase.query(
                StudentContract.StudentEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new AddFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnItemClick(int position, long id) {

        Log.d("MainActivity", "OnClick");
        EditFragment editFragment = new EditFragment();
        /*Student student = new Student(
                studentList.get(position).getmName(),
                studentList.get(position).getmEmail(),
                studentList.get(position).getmPhone()
        );*/

        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putLong("id", id);
        editFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnDelete(long id) {
        //studentList.remove(studentList.get(position));
        Log.d("Main", id+"");
        mDatabase = dbHelper.getWritableDatabase();
        mDatabase.delete(StudentContract.StudentEntry.TABLE_NAME,
                StudentContract.StudentEntry._ID + " = ?" ,
                new String[]{String.valueOf(id)}
        );
        mAdapter.swapCursor(getAllItems());
    }
}
