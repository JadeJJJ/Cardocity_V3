package com.varsitycollege.cardocity_app;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCPrt2 {

    //The connection to the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    //References
    //User Table
    // private DatabaseReference userRef = database.getReference("tableName");

    private static FirebaseAuth userRef = FirebaseAuth.getInstance();
    //Collection Table
    private DatabaseReference collRef = database.getReference("tableName");

    //Lists for storing the data

    private static boolean flag = false;

    //---------------------------------GetLogin-----------------------------//
    public static boolean GetLogin(String email, String password) {
        userRef = FirebaseAuth.getInstance();
        userRef.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag; // Returns list to the other class
        //If loginList = null then must show the toast message in the Activity
    }

    //---------------------------------SetLogin-----------------------------//
    public static boolean SetLogin(String email, String password) {
        userRef.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag;
    }
}