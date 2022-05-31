package com.varsitycollege.cardocity_app;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCPrt2 {

    //The connection to the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    //References
    //User Table
    // private DatabaseReference userRef = database.getReference("tableName");

    private static FirebaseAuth userRef = FirebaseAuth.getInstance();

    //Item Table
    private DatabaseReference itemRef = database.getReference("Item");

    //Deck Table
    private DatabaseReference deckRef = database.getReference("Deck");

    //Collection Table
    private DatabaseReference collRef = database.getReference("Collection");

    //Lists for storing the data
    private List<Item> itemList = new ArrayList<Item>();
    private List<Deck> deckList = new ArrayList<Deck>();
    private List<Collection> collList = new ArrayList<Collection>();

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

    //---------------------------------GetItem-----------------------------//
    public List<Item> GetItem(){
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledLogin : snapshot.getChildren()) {
                    Item item = pulledLogin.getValue(Item.class);
                    itemList.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });

        return itemList;
    }

    //---------------------------------SetItem-----------------------------//
    public void SetItem(Item newItem) {
        itemRef.push().setValue(newItem);
    }

    //---------------------------------GetDeck-----------------------------//
    public List<Deck> GetDeck(){
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledLogin : snapshot.getChildren()) {
                    Deck deck = pulledLogin.getValue(Deck.class);
                    deckList.add(deck);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });

        return deckList;
    }

    //---------------------------------SetDeck-----------------------------//
    public void SetDeck(Deck newDeck) {
        deckRef.push().setValue(newDeck);
    }

    //---------------------------------GetCollection-----------------------------//
    public List<Collection> GetCollection(){
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledLogin : snapshot.getChildren()) {
                    Collection coll = pulledLogin.getValue(Collection.class);
                    collList.add(coll);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });

        return collList;
    }

    //---------------------------------SetCollection-----------------------------//
    public void SetDeck(Collection newColl) {
        collRef.push().setValue(newColl);
    }
}