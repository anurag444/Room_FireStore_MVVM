package com.example.internshipapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internshipapp.Repository.Repository;
import com.example.internshipapp.ViewModel.StudentViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView studentRecycler;
    private StudentAdapter studentAdapter;
    private StudentViewModel studentViewModel;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText studentId,studentName;
    private Button save;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private List<Student> studentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        studentRecycler=findViewById(R.id.student_recycler_view);
        studentRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentRecycler.setHasFixedSize(true);


        studentArrayList= new ArrayList<>();
        studentAdapter= new StudentAdapter(studentArrayList,this);
        getDataFromDB();



        //using view model to get data from repository
        studentViewModel= new ViewModelProvider(this).get(StudentViewModel.class);
//        studentViewModel.insert(studentViewModel.getDataFromDB());
        studentViewModel.getStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {

                studentAdapter.getAllStudents(students);
                studentRecycler.setAdapter(studentAdapter);
            }
        });

        //fab button
        FloatingActionButton fab= findViewById(R.id.button_save);
        fab.setOnClickListener(v -> openDialog());
    }

    private void openDialog() {
        builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);
        studentId=view.findViewById(R.id.student_id);
        studentName=view.findViewById(R.id.student_name);
        save = view.findViewById(R.id.btn_save);
        save.setOnClickListener(v -> saveDetails());
        dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setView(view);

        dialog.show();
    }

    private void saveDetails() {
        String student_id= studentId.getText().toString();
        String student_name=studentName.getText().toString();

        if (student_id.trim().isEmpty() || student_name.trim().isEmpty()){
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        }

        Student student= new Student(student_id,student_name);
        db.collection("Student").document().set(student)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "New Student added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();


//        CollectionReference reference= FirebaseFirestore.getInstance().collection("Student");
//        reference.add(new Student(student_id,student_name));

    }
        public void getDataFromDB() {

        db.collection("Student")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        assert queryDocumentSnapshots != null;
                        for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                            studentArrayList.add(new Student(String.valueOf(snapshot.get("id")),String.valueOf(snapshot.get("studentName"))));
                        }

                        studentViewModel.insert(studentArrayList);
                    }
                });

    }

}
