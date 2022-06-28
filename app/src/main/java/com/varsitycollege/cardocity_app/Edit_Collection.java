package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Collection extends AppCompatActivity {
    private Button updateCollectionBtn;
    private Button deletedCollectionBtn;
    private EditText txtCollectionID;
    private EditText txtCollectionName;
    private EditText txtCollectionGoal;
    private Integer collectionID;
    private String collectionName;
    private String oldCollectionName;
    private Integer collectionGoal;
    private String userID = MainActivity.UserID;
    DatabaseCPrt2 dataHand = new DatabaseCPrt2();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference collRef = database.getReference("Collection");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collection);

        updateCollectionBtn = findViewById(R.id.updateCollectionButton);
        deletedCollectionBtn = findViewById(R.id.deleteCollectionButton);
        txtCollectionID = findViewById(R.id.txtCollectionID);
        txtCollectionName = findViewById(R.id.txtCollectionName);
        txtCollectionGoal = findViewById(R.id.txtCollectionGoal);

        collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledData : snapshot.getChildren())
                {
                    Collection collection = pulledData.getValue(Collection.class);
                    if (collection.getCollectionName().equals(Home_Page.sendSelectedCollection) && collection.getUserID().equals(userID))
                    {
                        txtCollectionID.setText(collection.getCollectionID().toString());
                        txtCollectionName.setText(collection.getCollectionName());
                        oldCollectionName = collection.getCollectionName();
                        txtCollectionGoal.setText(collection.getGoalItems().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        updateCollectionBtn.setOnClickListener(view -> {
            collectionID = Integer.parseInt(txtCollectionID.getText().toString());
            collectionName = txtCollectionName.getText().toString();
            collectionGoal = Integer.parseInt(txtCollectionGoal.getText().toString());
            Collection newCollection = new Collection(collectionID, collectionName, collectionGoal, userID);
            dataHand.updateCollection(collectionID, oldCollectionName, newCollection, userID);
            startActivity(new Intent(Edit_Collection.this,Home_Page.class));
        });

        deletedCollectionBtn.setOnClickListener(view -> {
            collectionID = Integer.parseInt(txtCollectionID.getText().toString());
            dataHand.deleteCollection(oldCollectionName, userID);
            startActivity(new Intent(Edit_Collection.this,Home_Page.class));
        });
    }
}