package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Forgot_Password extends AppCompatActivity {
private Button ResetPasswordBtn;
private TextView loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ResetPasswordBtn = findViewById(R.id.resetPassword);
        loginBtn = findViewById(R.id.log_forgotPassword);

        loginBtn.setOnClickListener(view -> {
            startActivity(new Intent(Forgot_Password.this, MainActivity.class));
        });
    }
}