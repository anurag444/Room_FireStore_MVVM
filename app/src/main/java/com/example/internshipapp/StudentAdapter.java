package com.example.internshipapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {

    private List<Student> studentArrayList;
    private Context context;

    public StudentAdapter(List<Student> studentArrayList, Context context) {
        this.studentArrayList = studentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new StudentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student student= studentArrayList.get(position);
        holder.studentName.setText(student.getStudentName());
        holder.studentId.setText(student.getId());
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public void getAllStudents(List<Student> studentArrayList){
        this.studentArrayList= studentArrayList;
    }

    class StudentHolder extends RecyclerView.ViewHolder{
        private TextView studentName,studentId;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            studentId=itemView.findViewById(R.id.studentId);
            studentName=itemView.findViewById(R.id.student_name);
        }
    }
}
