package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class View_Item extends AppCompatActivity {
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
    private Button btnReturn;
    private EditText edtDate;
    private Button btnEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        edtSerialNumber = findViewById(R.id.editSerialView);
        edtCardName = findViewById(R.id.editCardNameView);
        edtNumCards = findViewById(R.id.editNoCardsView);
        edtCardType = findViewById(R.id.editCardTypeView);
        imgView = findViewById(R.id.newImage);
        btnReturn = findViewById(R.id.btnUpdateItem);
        edtDate = findViewById(R.id.edtDate);
        btnEditItem = findViewById(R.id.btnEditItem);

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);

                    if (Objects.equals(item.getUserID(), userid) && Objects.equals(item.getCardName(), selectedItem))
                    {

                        edtSerialNumber.setText(item.getSerialNumber());
                        edtCardName.setText(item.getCardName());
                        edtCardType.setText(item.getCardType());
                        edtNumCards.setText(item.getNumberOfCards().toString());
                        edtDate.setText(item.getAquireDate().toString());

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
                Toast.makeText(View_Item.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

        btnReturn.setOnClickListener(view ->{
            startActivity(new Intent(View_Item.this,Home_Page.class));
        });

        btnEditItem.setOnClickListener(view -> {
            startActivity(new Intent(View_Item.this, Edit_Item.class));
        });
    }
}