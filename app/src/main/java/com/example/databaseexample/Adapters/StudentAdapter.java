package com.example.databaseexample.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.databaseexample.Contract.StudentContract.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databaseexample.Contract.StudentContract;
import com.example.databaseexample.R;
import com.example.databaseexample.Models.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private Cursor cursor;
    private List<Student> studentList;
    private long id;
    protected OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position, long id);
        void OnDelete(long id);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public StudentAdapter(Context context, Cursor cursor) {
        this.context = context;
        //this.studentList = studentList;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String studName = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_NAME));
        String studEmail = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_EMAIL));
        String studPhone = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_PHONE));
        id = cursor.getLong(cursor.getColumnIndex(StudentEntry._ID));

        holder.tvName.setText(studName);
        holder.tvEmail.setText(studEmail);
        holder.tvPhone.setText(studPhone);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvEmail, tvPhone;
        LinearLayout studLayout;
        Button btnDelete;

        public StudentViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            studLayout = (LinearLayout) itemView.findViewById(R.id.student_container);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            studLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.OnItemClick(position, id);
                        }
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                // Add Dialog

                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.OnDelete((long) itemView.getTag());
                        }
                    }
                }
            });
        }
    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null){
            cursor.close();
        }
        cursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
