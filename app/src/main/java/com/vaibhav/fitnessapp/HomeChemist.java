package com.vaibhav.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeChemist extends AppCompatActivity {

    RecyclerView recyclerView;
    Button logout;
    ArrayList<Prescription> presNew, presPaid, presDeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chemist);

        recyclerView  = findViewById(R.id.orderHolder);

        Button newOrder, paid, delivered;

        newOrder = findViewById(R.id.newOrder);
        paid = findViewById(R.id.paid);
        delivered = findViewById(R.id.delivered);
        logout = findViewById(R.id.logout);

        presNew = new ArrayList<>();
        presPaid = new ArrayList<>();
        presDeli = new ArrayList<>();

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presNew.clear();
                newOrder.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                paid.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("prescription")
                        .whereEqualTo("status", 1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                        presNew.add(documentSnapshot.toObject(Prescription.class));
                                    }
                                }
                                setAdapterNew(presNew);
                            }
                        });
            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presPaid.clear();
                paid.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                newOrder.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("prescription")
                        .whereEqualTo("status", 3)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                        presPaid.add(documentSnapshot.toObject(Prescription.class));
                                    }
                                }
                                setAdapterPaid(presPaid);
                            }
                        });
            }
        });

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presDeli.clear();
                delivered.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
                paid.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));
                newOrder.setBackground(getResources().getDrawable(R.drawable.button_background_unselected));

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("prescription")
                        .whereEqualTo("status", 4)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null) {
                                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                        presDeli.add(documentSnapshot.toObject(Prescription.class));
                                    }
                                }
                                setAdapterDelivered(presDeli);
                            }
                        });
            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("prescription")
                .whereEqualTo("status", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult() != null) {
                            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                presNew.add(documentSnapshot.toObject(Prescription.class));
                            }
                        }
                        setAdapterNew(presNew);
                    }
                });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("loggedInStatus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editStatus = sharedPreferences.edit();
                editStatus.putBoolean("loggedInSuperAdmin", false);
                editStatus.apply();
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeChemist.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("loggedInStatus", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editStatus = sharedPreferences.edit();
                        editStatus.putBoolean("loggedIn", false);
                        editStatus.apply();
                        SharedPreferences sharedPreferences1 = getSharedPreferences("Role", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editStatus1 = sharedPreferences1.edit();
                        editStatus1.putString("Role", "None");
                        editStatus1.apply();
                        Intent intent1 = new Intent(HomeChemist.this, ChooseRole.class);
                        startActivity(intent1);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });



    }
    void setAdapterNew(ArrayList<Prescription> arrayList) {
        AdapterPrescriptionNew adapterPrescriptionNew = new AdapterPrescriptionNew(HomeChemist.this, R.layout.card_prescription_order_holder, arrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(HomeChemist.this);
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager1);
            recyclerView.setAdapter(adapterPrescriptionNew);
        } catch (Exception e) {

        }
    }

    void setAdapterPaid(ArrayList<Prescription> arrayList) {
        AdapterPrescriptionPaid adapterPrescriptionPaid = new AdapterPrescriptionPaid(HomeChemist.this, R.layout.card_prescription_paidorder_holder, arrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(HomeChemist.this);
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager1);
            recyclerView.setAdapter(adapterPrescriptionPaid);
        } catch (Exception e) {

        }
    }

    void setAdapterDelivered(ArrayList<Prescription> arrayList) {
        AdapterPrescriptionDelivered adapterPrescriptionDelivered = new AdapterPrescriptionDelivered(HomeChemist.this, R.layout.card_prescription_deliveredorder_holder, arrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(HomeChemist.this);
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager1);
            recyclerView.setAdapter(adapterPrescriptionDelivered);
        } catch (Exception e) {

        }
    }
}