package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Spinner collSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);

// Adding Items to Spinner -------------------------------------------------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference collRef = database.getReference("Collection");
        String userid = MainActivity.UserID;
        List<String> collListName = new ArrayList<String>();
        collSpinner = findViewById(R.id.spnCollection);


        ValueEventListener valueEventListener = collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledOrder : snapshot.getChildren()) {
                    Collection coll = pulledOrder.getValue(Collection.class);
                    if (Objects.equals(coll.getUserID(), userid)) {
                        collListName.add(coll.getCollectionName());
                    }
                }

                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Add_Deck.this, android.R.layout.simple_spinner_item, collListName);
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
            boolean bFlag = true;
            InputValidation iv = new InputValidation();
            EditText etDeckName = findViewById(R.id.edtDeckName);
            EditText etNumCards = findViewById(R.id.edtNoCards);
            String sName = etDeckName.getText().toString();
            String sColl = collSpinner.getSelectedItem().toString();
            Integer iNoCards = 0;
            Integer iDeckID = 0;
            DatabaseCPrt2 db = new DatabaseCPrt2();

            if (!iv.NotNullorEmpty(sName))
            {
                bFlag = false;
                etDeckName.setError("Please enter a Card Name!!");
            }

            if (!iv.NotNullorEmpty(sColl))
            {
                bFlag = false;
                etDeckName.setError("Please enter a Collection!!");
            }

            if (!iv.NotNullorEmpty(etNumCards.getText().toString()))
            {
                bFlag = false;
                etNumCards.setError("Please enter a Number of cards in!!");
            }
            else
            {
                try
                {
                    iNoCards = Integer.parseInt(etNumCards.getText().toString());
                } catch(Exception ex)
                {
                    bFlag = false;
                    etNumCards.setError("Number of cards invalid!!");
                }
            }
            if (bFlag)
            {
                Deck myDeck = new Deck(iDeckID, sName, sColl, iNoCards, userid);
                db.SetDeck(myDeck);
                iv.msg("Deck Added!", Add_Deck.this);
                startActivity(new Intent(Add_Deck.this,Deck_Screen.class));

            }
            else
                iv.msg("Failed to Add Item!!", Add_Deck.this);

        });
    }
}