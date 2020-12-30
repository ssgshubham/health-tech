package com.vaibhav.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity {

    Button doc1, doc2, doc3, doc4, doc5;
    ArrayList<Doctor> arrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        doc1 = findViewById(R.id.doc1);
        doc2 = findViewById(R.id.doc2);
        doc3 = findViewById(R.id.doc3);
        doc4 = findViewById(R.id.doc4);
        doc5 = findViewById(R.id.doc5);

        recyclerView = findViewById(R.id.doctors);

        arrayList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("doctors")
                .whereEqualTo("specialize", "General Physician")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult() != null) {
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                arrayList.add(documentSnapshot.toObject(Doctor.class));
                            }
                        }
                        setAdapter(arrayList);
                    }
                });

        doc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                doc1.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                doc2.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc3.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc4.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc5.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("doctors")
                        .whereEqualTo("specialize", "General Physician")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        arrayList.add(documentSnapshot.toObject(Doctor.class));
                                    }
                                }
                                setAdapter(arrayList);
                            }
                        });
            }
        });

        doc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                doc2.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                doc1.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc3.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc4.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc5.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("doctors")
                        .whereEqualTo("specialize", "ENT Specialist")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        arrayList.add(documentSnapshot.toObject(Doctor.class));
                                    }
                                }
                                setAdapter(arrayList);
                            }
                        });
            }
        });

        doc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                doc3.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                doc2.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc1.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc4.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc5.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("doctors")
                        .whereEqualTo("specialize", "Cardiologist")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        arrayList.add(documentSnapshot.toObject(Doctor.class));
                                    }
                                }
                                setAdapter(arrayList);
                            }
                        });
            }
        });

        doc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                doc4.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                doc2.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc3.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc1.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc5.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("doctors")
                        .whereEqualTo("specialize", "Paediatrician")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        arrayList.add(documentSnapshot.toObject(Doctor.class));
                                    }
                                }
                                setAdapter(arrayList);
                            }
                        });
            }
        });

        doc5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                doc5.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                doc2.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc3.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc4.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                doc1.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("doctors")
                        .whereEqualTo("specialize", "Dermatologist")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        arrayList.add(documentSnapshot.toObject(Doctor.class));
                                    }
                                }
                                setAdapter(arrayList);
                            }
                        });
            }
        });

    }
    void setAdapter(ArrayList<Doctor> arrayList) {
        AdapterDoctor adapterDoctor = new AdapterDoctor(BookAppointment.this, R.layout.card_doctor_holder, arrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(BookAppointment.this);
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager1);
            recyclerView.setAdapter(adapterDoctor);
        } catch (Exception e) {

        }
    }
}