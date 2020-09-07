package com.industrialmaster.hospitalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private Button logout,changepassword;
    private TextView displayName,displayEmail,displayContact;
    private EditText editDisplayName,editPassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceUpdate;

    String userOldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logout = findViewById(R.id.logoutBtn);
        changepassword = findViewById(R.id.updateBtn);
        displayName = findViewById(R.id.profileName);
        displayEmail = findViewById(R.id.userEmailDisplay);
        displayContact = findViewById(R.id.userPhoneDisplay);
        editDisplayName = findViewById(R.id.displayNameEdit);
        editPassword = findViewById(R.id.passwordEdit);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersData usersData = dataSnapshot.getValue(UsersData.class);
                displayName.setText(usersData.getUsername());
                displayEmail.setText(usersData.getEmail());
                displayContact.setText(usersData.getPhone());
                editDisplayName.setText(usersData.getUsername());
                userOldPassword = usersData.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingsActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),userOldPassword);
                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firebaseUser.updatePassword(editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                                        DatabaseReference databaseReferenceUpdate = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("email",displayEmail.getText().toString());
                                        hashMap.put("imageURL","default");
                                        hashMap.put("password",editPassword.getText().toString());
                                        hashMap.put("phone",displayContact.getText().toString());
                                        hashMap.put("userId",user.getUid());
                                        hashMap.put("username",editDisplayName.getText().toString());

                                        databaseReferenceUpdate.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SettingsActivity.this,"Successfull Action",Toast.LENGTH_SHORT).show();
                                                    FirebaseAuth.getInstance().signOut();
                                                    Intent intent = new Intent(SettingsActivity.this,SignInActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else{
                                                    Toast.makeText(SettingsActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
    }


}
