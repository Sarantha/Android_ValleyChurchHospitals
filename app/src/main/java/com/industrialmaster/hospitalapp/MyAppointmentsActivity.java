package com.industrialmaster.hospitalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAppointmentsActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //private Query query;
    private Query query;

    RecyclerView recyclerView;

    ArrayList<Appointments> list;
    MyAppointmentsAdapter appointmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        recyclerView = (RecyclerView) findViewById(R.id.appointmentDetailsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Appointments>();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String currentUser = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");
        query = databaseReference.orderByChild("userId").equalTo(currentUser);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Appointments a = dataSnapshot1.getValue(Appointments.class);
                    list.add(a);
                }
                appointmentsAdapter = new MyAppointmentsAdapter(MyAppointmentsActivity.this,list);
                recyclerView.setAdapter(appointmentsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyAppointmentsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
