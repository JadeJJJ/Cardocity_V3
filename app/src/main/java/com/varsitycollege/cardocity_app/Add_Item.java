package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Add_Item extends AppCompatActivity {
Button takePhotoBtn;
EditText etSerialNumber;
EditText etCardName;
EditText etCardType;
EditText etNumberOfCards;
Button addItemBtn;

/*public Bitmap newImage;
private String serialNum;
private String cardName;
private String cardType;
private Integer numOfCards;
private String userID; */
//Image variable

    private Button fabButton;
    public ImageView camImage;
    private Button storePhoto;
    private static final int requestImageCapture = 0;
    private static final int requestImageCapPer = 100;

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
           Bitmap newImage = ((BitmapDrawable) camImage.getDrawable()).getBitmap();
           String userID = MainActivity.UserID;
           String collection = Home_Page.selectedCollection;
          Integer numberOfCards = 0;
           Bitmap cardImage = null; // change this later
           boolean bFlag = true;
           DatabaseCPrt2 db = new DatabaseCPrt2();

           // TODO Take a picture functionality

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
                //Item it = new Item(serialNumber, cardName, cardType, numberOfCards, cardImage, userID);
                //addItem();
                Item myItem = new Item(serialNumber, cardName, cardType, numberOfCards, newImage, collection, userID);
                db.SetItem(myItem);
                iv.msg("Item Added!", Add_Item.this);

           }
           else
               iv.msg("Failed to Add Item!!", Add_Item.this);

       });

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Add_Item.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    final String[] permissions = {Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(Add_Item.this, permissions, requestImageCapPer);
                }
                else
                {
                    takePhoto();
                }
            }

        });

        camImage = findViewById(R.id.addItemImageView);
        storePhoto = findViewById(R.id.btnStorePhoto);

    }

    /*private void addItem() {
        serialNum = etSerialNumber.getText().toString();
        cardName = etCardName.getText().toString();
        cardType = etCardType.getText().toString();
        numOfCards = Integer.valueOf(etNumberOfCards.getText().toString());
        //newImage = ((BitmapDrawable) camImage.getDrawable()).getBitmap();
        userID = MainActivity.UserID;
        newImage = ((BitmapDrawable) camImage.getDrawable()).getBitmap();
    } */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestImageCapture && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            camImage.setImageBitmap(bitmap);
        }
    }

    private void takePhoto()
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, requestImageCapture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestImageCapPer && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            takePhoto();
        }
    }
}