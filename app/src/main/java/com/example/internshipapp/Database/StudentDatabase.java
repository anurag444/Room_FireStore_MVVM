package com.example.internshipapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.internshipapp.Dao.StudentDao;
import com.example.internshipapp.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="student_name";
    public abstract StudentDao studentDao();

    private static volatile StudentDatabase instance= null;

    //creating only one instance
    public static StudentDatabase getInstance(Context context){
        if (instance==null){
            synchronized (StudentDatabase.class){
                if (instance==null){
                    instance= Room.databaseBuilder(context,StudentDatabase.class,
                    DATABASE_NAME).createFromAsset("databases/student_name.db").fallbackToDestructiveMigration()
                             .build();
                }
            }
        }
        return instance;
    }
    private static RoomDatabase.Callback callback= new RoomDatabase.Callback()  {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateAsyncTask(instance);
        }
    };
    //empty the database on first creation
    static class populateAsyncTask extends AsyncTask<Void,Void,Void>{

        private StudentDao studentDao;

        populateAsyncTask(StudentDatabase studentDatabase){
            studentDao= studentDatabase.studentDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            List<Student> studentList= new ArrayList<>();
            studentList.add(new Student("1","Sample1"));
            studentDao.insert(studentList);
            return null;
        }
    }
}
