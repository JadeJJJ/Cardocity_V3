package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
private Button ResetPasswordBtn;
private TextView loginBtn;
private EditText emailText;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ResetPasswordBtn = findViewById(R.id.resetPassword);
        loginBtn = findViewById(R.id.log_forgotPassword);
        emailText = findViewById(R.id.logEmail);
        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            startActivity(new Intent(Forgot_Password.this, MainActivity.class));
        });

        ResetPasswordBtn.setOnClickListener(view -> {
            String email_forgot =  emailText.getText().toString().trim();
            if(email_forgot.isEmpty()){
                emailText.setError("Enter Email!!");
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email_forgot).matches()){
               emailText.setError("Enter Valid Email!!");
               return;
            }
                auth.sendPasswordResetEmail(email_forgot).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(Forgot_Password.this,"Check email for password Reset",Toast.LENGTH_LONG).show();
                      }else{
                          Toast.makeText(Forgot_Password.this,"Error!!,Try again!!",Toast.LENGTH_LONG).show();
                      }
                    }
                });

        });
    }
}