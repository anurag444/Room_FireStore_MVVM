package com.example.internshipapp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.internshipapp.Dao.StudentDao;
import com.example.internshipapp.Database.StudentDatabase;
import com.example.internshipapp.Student;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private StudentDatabase studentDatabase;
    private LiveData<List<Student>> getAllStudents;
    private ArrayList<Student> studentArrayList= new ArrayList<>();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    public Repository(Application application){
        studentDatabase=StudentDatabase.getInstance(application);
        getAllStudents=studentDatabase.studentDao().getStudents();
    }
    public void insert(List<Student> studentList){
        new insertAsync(studentDatabase).execute(studentList);
    }
    public void deleteAll()
    {
        new DeleteAsynTask(studentDatabase).execute();
    }
    public LiveData<List<Student>> getStudents(){
        return  getAllStudents;
    }

    public void getDataFromDB() {

        db.collection("Student")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                        studentArrayList.add(new Student(String.valueOf(snapshot.get("id")),String.valueOf(snapshot.get("studentName"))));
                    }
                });

        insert(studentArrayList);

    }
    static class insertAsync extends AsyncTask<List<Student>,Void,Void>{

        private StudentDao studentDao;

        insertAsync(StudentDatabase studentDatabase){
            studentDao= studentDatabase.studentDao();
        }


        @Override
        protected Void doInBackground(List<Student>... lists) {
            studentDao.insert(lists[0]);
            return null;
        }
    }
    static class DeleteAsynTask extends AsyncTask<Void,Void,Void>
    {
        private StudentDao studentDao;

        DeleteAsynTask(StudentDatabase studentDatabase)
        {
            studentDao=studentDatabase.studentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAll();
            return null;
        }
    }


}
