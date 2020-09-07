package com.industrialmaster.hospitalapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private TextView dateDisplay;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    private TextView userName;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateDisplay = findViewById(R.id.date);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        dateDisplay.setText(date);

        //Username Display
        userName = findViewById(R.id.userNameDispaly);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersData usersData = dataSnapshot.getValue(UsersData.class);
                assert usersData != null;
                userName.setText(usersData.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDoctor (View view){
        Intent intent = new Intent(MainActivity.this,DoctorsActivity.class);
        startActivity(intent);
    }

    public void onInformation (View view){
        Intent intent = new Intent(MainActivity.this,InformationActivity.class);
        startActivity(intent);
    }

    public void onAppointment (View view){
        Intent intent = new Intent(MainActivity.this,AppointmentsActivity.class);
        startActivity(intent);
    }

    public void onMyAppointments (View view){
        Intent intent = new Intent(MainActivity.this,MyAppointmentsActivity.class);
        startActivity(intent);
    }

    public void onSettings (View view){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onPrescription (View view){
        Intent intent = new Intent(MainActivity.this,PrescriptionActivity.class);
        startActivity(intent);
    }
}
