package com.industrialmaster.hospitalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText userfFllnanme,emailAddress,contactNumber,passWord;
    private ProgressBar progressBar;
    private Button registerBtn;

    //Instance of Firebase authentication
    private FirebaseAuth firebaseAuth;
    //Firebase databse referance
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Inniatialize variables to view Id

        firebaseAuth = FirebaseAuth.getInstance();

        userfFllnanme = findViewById(R.id.userfullnanme);
        emailAddress = findViewById(R.id.emailaddress);
        contactNumber = findViewById(R.id.contactnumber);
        passWord = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress);
        registerBtn = findViewById(R.id.RegisterBtn);

        //action when sign-up button tapped
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize storing variables
                final String user_name = userfFllnanme.getText().toString();
                final String email = emailAddress.getText().toString();
                final String password = passWord.getText().toString();
                final String phone = contactNumber.getText().toString();

                if(TextUtils.isEmpty(user_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)){
                    Toast.makeText(SignUpActivity.this,"All the fields must be filled",Toast.LENGTH_SHORT).show();
                }else{
                    register(user_name,email,password,phone);
                }
            }
        });
    }

    private void register(final String user_name, final String email, final String password, final String phone) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser rUser = firebaseAuth.getCurrentUser();
                    assert  rUser !=null;
                    String userId = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("userId",userId);
                    hashMap.put("username",user_name);
                    hashMap.put("email",email);
                    hashMap.put("password",password);
                    hashMap.put("phone",phone);
                    hashMap.put("imageURL","default");

                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this,Objects.requireNonNull(task.getException().getMessage()),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
