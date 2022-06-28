package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Deck_Screen extends AppCompatActivity {
    //Components
    private ListView lstvDeck;
    private Button btnCreateDeck;
    private Button btnSelectDeck;
    private Spinner spnSelectDeck;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference deckRef = database.getReference("Deck");
    public static String selectedDeck;
    private DrawerLayout mDrawerLayout; //DylanA
    private ActionBarDrawerToggle mToggle; //DylanA
    private NavigationView navView; //DylanA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_screen);
        //FindViews
        lstvDeck = findViewById(R.id.lstvDecks);
        btnCreateDeck = findViewById(R.id.btnNewDeck);
        btnSelectDeck = findViewById(R.id.btnSelectDeck);
        spnSelectDeck = findViewById(R.id.spnSelectDeck);
        //Get userID
        String userid = MainActivity.UserID;
        //List
        List<String> listDeck = new ArrayList<>();
        // NAV DRAWER---------------------------------------------------------------------------------------
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//DylanA
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);//DylanA EVERY PAGE NEEDS A DRAWERLAYOUT ID
        mDrawerLayout.addDrawerListener(mToggle);//DylanA

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close); //DylanA
        mToggle.syncState();//DylanA

        navView = findViewById(R.id.nav_side_menu) ;
        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        deckRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Deck deck = pulledOrder.getValue(Deck.class);
                    if (Objects.equals(deck.getUserID(), userid))
                    {
                        listDeck.add(deck.getDeckName());

                    }
                }
                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Deck_Screen.this, android.R.layout.simple_spinner_item, listDeck);
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnSelectDeck.setAdapter(spnAdapter);

                lstvDeck.setAdapter(spnAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Deck_Screen.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

        btnCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Deck_Screen.this, Add_Deck.class));
            }
        });

        btnSelectDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDeck = spnSelectDeck.getSelectedItem().toString();
               // startActivity(new Intent(Deck_Screen.this, View_deck.class)); //NEED TO GO TO SELECTED DECK OR ADD ITEM TO DECK
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//DylanA

        if(mToggle.onOptionsItemSelected(item)){//DylanA
            return true;//DylanA
        }

        return super.onOptionsItemSelected(item);//DylanA
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_myCollections:
                startActivity(new Intent(Deck_Screen.this, Home_Page.class));
                break;
            case R.id.nav_decks:
                 startActivity(new Intent(Deck_Screen.this, Deck_Screen.class));
                break;
            case R.id.nav_stats:
                startActivity(new Intent(Deck_Screen.this, GoalsAndStats.class));
                break;
            case R.id.nav_signOut:
                startActivity(new Intent(Deck_Screen.this, MainActivity.class));//Sends User to Login Screen
                break;
        }
        return true;
    }
}
