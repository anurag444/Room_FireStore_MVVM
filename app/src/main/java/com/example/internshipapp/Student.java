package com.example.internshipapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class Student {

    @PrimaryKey
    @NonNull
    private String Id;

    @ColumnInfo(name = "studentName")
    private String studentName;

    public Student(String Id,String studentName){
        this.studentName=studentName;
        this.Id=Id;
    }
    public Student(){}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public static Student[] populateData() {
        return new Student[] {
                new Student("1", "Sample Name 1"),
                new Student("2", "Sample Name 2"),
                new Student("3", "Sample Name 3"),
                new Student("4", "Sample Name 4"),
                new Student("5", "Sample Name 5")

        };
    }
}

