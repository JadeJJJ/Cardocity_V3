package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Achiements extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");
    private Integer numCards = 0;
    private String userid = MainActivity.UserID;
    private ImageView imgAchieve1;
    private ImageView imgAchieve2;
    private ImageView imgAchieve3;
    private ImageView imgAchieve4;
    private TextView txtAchieve1;
    private TextView txtAchieve2;
    private TextView txtAchieve3;
    private TextView txtAchieve4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achiements);
        imgAchieve1 = findViewById(R.id.imgAchieve1);
        imgAchieve2 = findViewById(R.id.imgAchieve2);
        imgAchieve3 = findViewById(R.id.imgAchieve3);
        imgAchieve4 = findViewById(R.id.imgAchieve4);
        txtAchieve1 = findViewById(R.id.textAchieve1);
        txtAchieve2 = findViewById(R.id.textAchieve2);
        txtAchieve3 = findViewById(R.id.textAchieve3);
        txtAchieve4 = findViewById(R.id.textAchieve4);
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);
                    if (Objects.equals(item.getUserID(), userid))
                    {
                        numCards ++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Achiements.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DisplayAchievements();
            }
        }, 1200);

    }

    private void DisplayAchievements()
    {
        if(numCards >= 10 && numCards <= 50 )
        {
            imgAchieve1.setVisibility(View.VISIBLE);
            txtAchieve1.setVisibility(View.VISIBLE);
        }
        else if(numCards >= 50 && numCards <= 100 )
        {
            imgAchieve1.setVisibility(View.VISIBLE);
            txtAchieve1.setVisibility(View.VISIBLE);
            imgAchieve2.setVisibility(View.VISIBLE);
            txtAchieve2.setVisibility(View.VISIBLE);
        }
        else if(numCards >= 100 && numCards <= 200 )
        {
            imgAchieve1.setVisibility(View.VISIBLE);
            txtAchieve1.setVisibility(View.VISIBLE);
            imgAchieve2.setVisibility(View.VISIBLE);
            txtAchieve2.setVisibility(View.VISIBLE);
            imgAchieve3.setVisibility(View.VISIBLE);
            txtAchieve3.setVisibility(View.VISIBLE);
        }
        else if(numCards >= 200 && numCards <= 300)
        {
            imgAchieve1.setVisibility(View.VISIBLE);
            txtAchieve1.setVisibility(View.VISIBLE);
            imgAchieve2.setVisibility(View.VISIBLE);
            txtAchieve2.setVisibility(View.VISIBLE);
            imgAchieve3.setVisibility(View.VISIBLE);
            txtAchieve3.setVisibility(View.VISIBLE);
            imgAchieve4.setVisibility(View.VISIBLE);
            txtAchieve4.setVisibility(View.VISIBLE);
        }
        else {Toast.makeText(Achiements.this, "You have not collected enough cards to earn an Achievement", Toast.LENGTH_SHORT).show();}
    }
}