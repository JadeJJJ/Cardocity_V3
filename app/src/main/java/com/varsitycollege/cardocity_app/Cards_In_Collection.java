package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Cards_In_Collection extends AppCompatActivity {
Button addItemBtn;
Button editCollectionBtn;
Button btnSelect;
Button btnAddToDeck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_in_collection);
        addItemBtn=findViewById(R.id.addItem);
        editCollectionBtn = findViewById(R.id.editCollectionButton);
        btnSelect = findViewById(R.id.btnSelectItem);
        btnAddToDeck = findViewById(R.id.btnAddtoDeck);



        editCollectionBtn.setOnClickListener(view -> {

            startActivity(new Intent(Cards_In_Collection.this,Edit_Collection.class));

        });

        addItemBtn.setOnClickListener(view -> {
            startActivity(new Intent(Cards_In_Collection.this,Add_Item.class));
    });

    }
}