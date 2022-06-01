package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        //Method to add item to collection
       addItemBtn.setOnClickListener(view -> {
           InputValidation iv = new InputValidation();
           String serialNumber = etSerialNumber.getText().toString();
           String cardName = etCardName.getText().toString();
           String cardType = etCardType.getText().toString();
           Integer numberOfCards = 0;
           Image cardImage = null; // change this later
           boolean bFlag = true;
           DatabaseCPrt2 db = new DatabaseCPrt2();

           if (!iv.NotNullorEmpty(serialNumber))
           {
               bFlag = false;
               etSerialNumber.setError("Please enter a Serial Number!!");
           }

           if (!iv.NotNullorEmpty(cardName))
           {
               bFlag = false;
               etCardName.setError("Please enter a Card Name!!");
           }

           if (!iv.NotNullorEmpty(cardType))
           {
               bFlag = false;
               etCardType.setError("Please enter a Card Type!!");
           }


           if (!iv.NotNullorEmpty(etNumberOfCards.getText().toString()))
           {
               bFlag = false;
               etNumberOfCards.setError("Please enter a Number of cards in!!");
           }
           else
           {
               try
               {
                   numberOfCards = Integer.parseInt(etNumberOfCards.getText().toString());
               } catch(Exception ex)
               {
                   bFlag = false;
                   etNumberOfCards.setError("Number of cards invalid!!");
               }
           }

           if (bFlag)
           {
                Item it = new Item(serialNumber, cardName, cardType, numberOfCards, cardImage);
                db.SetItem(it);
           }
           else
               iv.msg("Failed to Add Item!!", Add_Item.this);

       });

        takePhotoBtn.setOnClickListener(view -> {
            startActivity(new Intent(Add_Item.this,Camera_Activity.class));


    });
    }
}