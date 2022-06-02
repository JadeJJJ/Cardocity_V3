package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class View_Item extends AppCompatActivity {
    private String selectedItem = Cards_In_Collection.selectedItem;
    private String userid = MainActivity.UserID;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storeRef = storage.getReference();
    EditText edtSerialNumber;
    EditText edtCardName;
    EditText edtNumCards;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        edtSerialNumber = findViewById(R.id.edtSerialView);
        edtCardName = findViewById(R.id.edtCardNameView);
        edtNumCards = findViewById(R.id.edtNoCardsView);
        imgView = findViewById(R.id.ivImageView);
        List<String> serialNum = new ArrayList<>();
        List<String> cardName = new ArrayList<>();
        List<String> numOfCards = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);


        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);

                    if (Objects.equals(item.getUserID(), userid)&& Objects.equals(item.getCardName(), selectedItem))
                    {
                        serialNum.add(item.getSerialNumber());
                        cardName.add(item.getCardName());
                        numOfCards.add(item.getNumberOfCards().toString());



                        StorageReference pathRef = storeRef.child("images/" + item.getCardImageLink());
                        final long myBytes = 1024 * 1024;
                        pathRef.getBytes(myBytes).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap myMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imgView.setImageBitmap(myMap);
                            }
                        });
                        for (int i =0 ; i < serialNum.size(); i++)
                        {
                            edtSerialNumber.setText(serialNum.get(i));
                            edtCardName.setText(cardName.get(i));
                            edtNumCards.setText(numOfCards.get(i));
                        }


                    }
                    else{
                        Toast.makeText(View_Item.this, "Failed to load", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(View_Item.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}