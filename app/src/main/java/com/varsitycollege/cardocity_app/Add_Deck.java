package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Add_Deck extends AppCompatActivity {
    private Button btnAddDeck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);

// Adding Items to Spinner -------------------------------------------------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference collRef = database.getReference("Collection");
        String userid = MainActivity.UserID;
        List<String> collListName = new ArrayList<String>();
        Spinner collSpinner = findViewById(R.id.spnCollection);

        ValueEventListener valueEventListener = collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledOrder : snapshot.getChildren()) {
                    Collection coll = pulledOrder.getValue(Collection.class);
                    if (Objects.equals(coll.getUserID(), userid)) {
                        collListName.add(coll.getCollectionName());
                    }
                }

                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Deck_Screen.this, android.R.layout.simple_spinner_item, collListName);
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                collSpinner.setAdapter(spnAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
// Adding Deck Button ------------------------------------------------------------------------------
        btnAddDeck.findViewById(R.id.btnCreate);
        btnAddDeck.setOnClickListener(view -> {
            boolean bflag = true;
            InputValidation iv = new InputValidation();


            startActivity(new Intent(Add_Deck.this,Deck_Screen.class));

        });
    }
}