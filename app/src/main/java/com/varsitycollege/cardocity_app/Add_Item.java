package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Add_Item extends AppCompatActivity {
Button takePhotoBtn;
EditText etSerialNumber;
EditText etCardName;
EditText etCardType;
EditText etNumberOfCards;
Button addItemBtn;
//Image variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etSerialNumber = findViewById(R.id.serialNumber);
        etCardName = findViewById(R.id.cardName);
        etCardType = findViewById(R.id.cardType);
        etNumberOfCards = findViewById(R.id.numberOfCards);
        takePhotoBtn = findViewById(R.id.take_photo);
         addItemBtn = findViewById(R.id.addItemButton);
     //------------------------------------------------------------------------------------------------

       addItemBtn.setOnClickListener(view -> {
           //Method to add item to collection



       });

        takePhotoBtn.setOnClickListener(view -> {
            startActivity(new Intent(Add_Item.this,Camera_Activity.class));


    });
    }
}