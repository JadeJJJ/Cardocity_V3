package com.varsitycollege.cardocity_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText eEmail;
    EditText ePassword;
    TextView Login_BTN;
    Button Register_BTN;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eEmail = findViewById(R.id.logEmail);
        ePassword = findViewById(R.id.logPassword);
        Login_BTN = findViewById(R.id.LoginText);
        Register_BTN = findViewById(R.id.RegisterBTN);

        mAuth = FirebaseAuth.getInstance();
        Register_BTN.setOnClickListener(view -> {
            registerUser();
        });

        Login_BTN.setOnClickListener(view -> {
            startActivity(new Intent(Register.this, MainActivity.class));
        });
    }
    private void registerUser() {
        String email = eEmail.getText().toString().trim();
        String password = ePassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            eEmail.setError("Please Enter Email!!");
        } else if (TextUtils.isEmpty(password)){
            ePassword.setError("Please enter Password!!");
        }else{
            boolean flag = DatabaseCPrt2.SetLogin(email, password);

            if (flag == true)
            {
                Toast.makeText(Register.this,"You have Been Registered!!!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, MainActivity.class));
            }
            else if (flag == false)
            {
                Toast.makeText(Register.this,"You have Not Been Registered!!!",Toast.LENGTH_SHORT).show();
            }
        }

    }

}