package com.industrialmaster.hospitalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AppointmentsActivity extends AppCompatActivity {

    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    Spinner spinner;
    ArrayAdapter<String> adapter;

    EditText appointmnetName,appointmentDate;
    Button bookAppointmnet;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        spinner = (Spinner) findViewById(R.id.selectDoctor);
        String[] doctorsList = new String[]{"Dr. Kumari Hewage", "Dr. Upali Jayarathna", "Dr. Kumari De Silva"};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,doctorsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        appointmnetName = (EditText) findViewById(R.id.appName);
        appointmentDate = (EditText) findViewById(R.id.appDate);
        bookAppointmnet = (Button) findViewById(R.id.btnBookApmt);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        appointmentDate.setText(date);

        firebaseAuth = FirebaseAuth.getInstance();



//        bookAppointmnet.setOnClickListener(new View.OnClickListener() {
//            //final String bookingName = appointmnetName.getText().toString();
//            final String doctorSelected = spinner.getSelectedItem().toString();
//            final String dateOfApmt = appointmentDate.getText().toString();
//
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AppointmentsActivity.this,appointmnetName.getText(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(AppointmentsActivity.this,spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
//            }
//        });

        bookAppointmnet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //final String bookingName = appointmnetName.getText().toString();
                //final String doctorSelected = spinner.getSelectedItem().toString();
                final String dateOfApmt = appointmentDate.getText().toString();

                FirebaseUser rUser = firebaseAuth.getCurrentUser();
                String userID = rUser.getUid();
                if(appointmnetName.equals("")){
                    Toast.makeText(AppointmentsActivity.this,"Enter appointment refaries name",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("userId",userID);
                    hashMap.put("name",appointmnetName.getText().toString());
                    hashMap.put("doctor",spinner.getSelectedItem().toString());
                    hashMap.put("bookDate",dateOfApmt);

                    databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AppointmentsActivity.this,"Appointment Booked successfully",Toast.LENGTH_SHORT).show();
                            }else{
                               Toast.makeText(AppointmentsActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
//                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(AppointmentsActivity.this,"Appointment Booked successfully",Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(AppointmentsActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
            }
        });

    }




}
