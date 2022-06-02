package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cards_In_Collection extends AppCompatActivity {
    private Button addItemBtn;
    private Button editCollectionBtn;
    private Button btnSelect;
    private Button btnAddToDeck;
    private ListView lstvOrderHistory;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_in_collection);
        addItemBtn=findViewById(R.id.addItem);
        editCollectionBtn = findViewById(R.id.editCollectionButton);
        btnSelect = findViewById(R.id.btnSelectItem);
        btnAddToDeck = findViewById(R.id.btnAddtoDeck);


        // TODO Add items from the database to the list in a collection
// Adding to List View------------------------------------------------------------------------------
        List<String> itemList = new ArrayList<>();
        ListView lstvItems = findViewById(R.id.lstvCards);
        String userid = MainActivity.UserID;
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);
                   // if (Objects.equals(item.getUserID(), userid))
                        itemList.add(item.toString());
                }

                ArrayAdapter<String> collAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemList);
                lstvItems.setAdapter(collAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cards_In_Collection.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

        editCollectionBtn.setOnClickListener(view -> {
            startActivity(new Intent(Cards_In_Collection.this,Edit_Collection.class));

        });

        addItemBtn.setOnClickListener(view -> {
            startActivity(new Intent(Cards_In_Collection.this,Add_Item.class));
         });

        btnSelect.setOnClickListener(view ->{
            // TODO Check that the item exists (if not the display an error)
            // TODO Once the item is found go to edit items screen
        });

        // TODO Remove button and put in edit item
        btnAddToDeck.setOnClickListener(view -> {
            // TODO Check deck exists
            // TODO Add selected card to entered deck
        });

    }
}