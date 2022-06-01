package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Edit_Collection extends AppCompatActivity {
Button updateCollectionBtn;
Button deletedCollectionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collection);

        updateCollectionBtn = findViewById(R.id.updateCollectionButton);
        deletedCollectionBtn = findViewById(R.id.deleteCollectionButton);



        updateCollectionBtn.setOnClickListener(view -> {
            //TODO Update Collection method
            //TODO push back to Collection screen when done

        });

        deletedCollectionBtn.setOnClickListener(view -> {
            //TODO Delete Collection Method
            //TODO Push back to Collection Screen when done


        });
    }
}