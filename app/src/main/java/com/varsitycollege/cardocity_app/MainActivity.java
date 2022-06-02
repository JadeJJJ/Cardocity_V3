package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText LoginEmail;
    private EditText LoginPassword;
    private TextView RegisterTEXT;
    private Button Login_BTN;
    public static String UserID;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginEmail = findViewById(R.id.logEmail);
        LoginPassword = findViewById(R.id.logPassword);
        RegisterTEXT = findViewById(R.id.log_Reg);
        Login_BTN = findViewById(R.id.Log_Login);

        mAuth = FirebaseAuth.getInstance();

        Login_BTN.setOnClickListener(view -> {
            LoginUser();
        });
        RegisterTEXT.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Register.class));
        });

    }

    private void LoginUser(){
        String email = LoginEmail.getText().toString().trim();
        String password = LoginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            LoginEmail.setError("Please Enter Email!!");
        } else if (TextUtils.isEmpty(password)){
            LoginPassword.setError("Please enter Password!!");
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"You have Been Logged In!!!",Toast.LENGTH_SHORT).show();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserID = user.getUid();
                        startActivity(new Intent(MainActivity.this, Home_Page.class));
                    }else{
                        Toast.makeText(MainActivity.this,"You have Not Been Logged In!!!"+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
   /* private void LoginUser(){
        String email = LoginEmail.getText().toString().trim();
        String password = LoginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            LoginEmail.setError("Please Enter Email!!");
        } else if (TextUtils.isEmpty(password)){
            LoginPassword.setError("Please enter Password!!");
        }else{
            boolean flag = DatabaseCPrt2.GetLogin(email, password);
            // slightly improved
            if (flag == true) {
                Toast.makeText(MainActivity.this,"You have Been Logged In!!!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Home_Page.class));
            }
            else if (flag == false){
                Toast.makeText(MainActivity.this, "You have Not Been Logged In!!!", Toast.LENGTH_SHORT).show();
            }
        }
    } */



}