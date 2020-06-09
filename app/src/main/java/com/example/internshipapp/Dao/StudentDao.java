package com.example.internshipapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.internshipapp.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Student> student);

    @Query("SELECT * from student_table")
    LiveData<List<Student>>  getStudents();


    @Query("DELETE FROM student_table")
    void deleteAll();

    @Insert
    void insertAll(Student... students);
}
