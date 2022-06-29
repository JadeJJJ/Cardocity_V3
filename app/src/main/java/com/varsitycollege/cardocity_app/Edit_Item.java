package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class Edit_Item extends AppCompatActivity {
    DatabaseCPrt2 dataHand = new DatabaseCPrt2();
    private String selectedItem = Cards_In_Collection.selectedItem;
    private String userid = MainActivity.UserID;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storeRef = storage.getReference();
    private EditText edtSerialNumber;
    private EditText edtCardName;
    private EditText edtNumCards;
    private EditText edtCardType;
    private ImageView imgView;
    private Button btnUpdateItem;
    private Button btnDeleteItem;
    private Button btnNewPhoto;
    private EditText edtDate;
    private String serialNumber;
    private String oldCardName;
    private static final int requestImageCapture = 0;
    private static final int requestImageCapPer = 100;
    private ImageView newImageCapture;
    private Item oldItem = new Item();
    private Boolean bPic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_1_edit_item_screen);

        edtSerialNumber = findViewById(R.id.editSerialView);
        edtCardName = findViewById(R.id.editCardNameView);
        edtNumCards = findViewById(R.id.editNoCardsView);
        edtCardType = findViewById(R.id.editCardTypeView);
        imgView = findViewById(R.id.newImage);
        btnUpdateItem = findViewById(R.id.btnUpdateItem);
        btnDeleteItem = findViewById(R.id.btnDeleteItem);
        edtDate = findViewById(R.id.editDate);
        btnNewPhoto = findViewById(R.id.btnNewPhoto);
        newImageCapture = findViewById(R.id.newImage);

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);

                    if (Objects.equals(item.getUserID(), userid) && Objects.equals(item.getCardName(), selectedItem))
                    {
                        oldItem = item;
                        edtSerialNumber.setText(item.getSerialNumber());
                        serialNumber = item.getSerialNumber();
                        edtCardName.setText(item.getCardName());
                        edtCardType.setText(item.getCardType());
                        edtNumCards.setText(item.getNumberOfCards().toString());
                        edtDate.setText(item.getAquireDate());

                        StorageReference pathRef = storeRef.child("images/" + item.getCardImageLink());
                        final long myBytes = 1024 * 1024;
                        pathRef.getBytes(myBytes).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap myMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imgView.setImageBitmap(myMap);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Edit_Item.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });




        btnNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Edit_Item.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    final String[] permissions = {Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(Edit_Item.this, permissions, requestImageCapPer);
                }
                else
                {
                    takePhoto();
                }
            }
        });

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bPic == true)
                {
                    oldCardName = oldItem.getCardName();
                    serialNumber = oldItem.getSerialNumber();
                    Bitmap newImage = ((BitmapDrawable) newImageCapture.getDrawable()).getBitmap();
                    String newSerial = edtSerialNumber.getText().toString();
                    String newCardName = edtCardName.getText().toString();
                    String newCardType = edtCardType.getText().toString();
                    Integer newCardNums = Integer.parseInt(edtNumCards.getText().toString());
                    String newImageLink = newCardName + ".jpg";
                    String newDate = edtDate.getText().toString();
                    Item newItem = new Item(newSerial, newCardName, newCardType, newCardNums, newImageLink, oldItem.getCollectionName(), userid, newDate, oldItem.getDeckID());
                    dataHand.updateItem(serialNumber, oldCardName, newItem, userid);
                }
                else
                {
                    oldCardName = oldItem.getCardName();
                    serialNumber = oldItem.getSerialNumber();
                    String newSerial = edtSerialNumber.getText().toString();
                    String newCardName = edtCardName.getText().toString();
                    String newCardType = edtCardType.getText().toString();
                    Integer newCardNums = Integer.parseInt(edtNumCards.getText().toString());
                    String newImageLink = oldItem.getCardImageLink();
                    String newDate = edtDate.getText().toString();
                    Item newItem = new Item(newSerial, newCardName, newCardType, newCardNums, newImageLink, oldItem.getCollectionName(), userid, newDate, oldItem.getDeckID());
                    dataHand.updateItem(serialNumber, oldCardName, newItem, userid);
                }

                startActivity(new Intent(Edit_Item.this, Cards_In_Collection.class));

            }

        });

        btnDeleteItem.setOnClickListener(View -> {
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dataHand.deleteItem(serialNumber);
                                Toast.makeText(Edit_Item.this, "Collection Deleted!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Edit_Item.this, Cards_In_Collection.class));

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                dataHand.deleteItem(serialNumber);
                startActivity(new Intent(Edit_Item.this, Cards_In_Collection.class));
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestImageCapture && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            newImageCapture.setImageBitmap(bitmap);
            bPic = true;
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

