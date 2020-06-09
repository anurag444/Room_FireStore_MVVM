package com.example.internshipapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.internshipapp.Repository.Repository;
import com.example.internshipapp.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Student>> getAllStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        getAllStudents=repository.getStudents();
    }
    public void insert(List<Student> student){
        repository.insert(student);
    }
    public LiveData<List<Student>> getStudents(){
        return getAllStudents;
    }

    public void getDataFromDB(){
          repository.getDataFromDB();
    }

}
