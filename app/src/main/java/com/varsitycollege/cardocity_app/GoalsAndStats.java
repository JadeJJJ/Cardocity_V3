package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoalsAndStats extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button UpdateChartBTN;
    //navBar
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navView;

    private PieChart pieChart;

    // Database stuff
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");
    private DatabaseReference deckRef = database.getReference("Deck");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_and_goals);
// Button Clicks------------------------------------------------------------------------------------
        UpdateChartBTN = findViewById(R.id.btnUpdateChart);
        pieChart= findViewById(R.id.piechart);
// OnCreate Pie Charts------------------------------------------------------------------------------
        //TODO: Update Pie Chart FUNCTION
        //The sections work just like a list so it is easy to add more
        //follow this link for tut:
        //https://www.geeksforgeeks.org/how-to-add-a-pie-chart-into-an-android-application/
        String userid = MainActivity.UserID;
        List<Integer> listItemDecks = new ArrayList<>(); // All the deckIDs of the cards in the collection
        List<Integer> listDeckIDs = new ArrayList<>(); // All the deckIDs in a collection
        List<String> listDeckNames = new ArrayList<>(); // All the deckNames
        List<Integer> listNoCardsInDeck = new ArrayList<>();
        List<Integer> listPercents = new ArrayList<>();

        //TODO: Get the DeckIDs as a list

        deckRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Deck deck = pulledOrder.getValue(Deck.class);
                    // Finds the Decks for that user from a collection
                    if (Objects.equals(deck.getUserID(), userid) && Objects.equals(deck.getChooseCollection(),Home_Page.sendSelectedCollection)) //&& Objects.equals(item.getDeckID(), Cards_In_Collection.sendDeckID)
                    {
                       listDeckIDs.add(deck.getDeckID());
                       listDeckNames.add(deck.getDeckName());
                       listNoCardsInDeck.add(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GoalsAndStats.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

        // Gets the item MAYBE why this doesn't work is bc it's in the change listener???? check if work now
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);
                    // Finds the items for that user
                    if (Objects.equals(item.getUserID(), userid) && Objects.equals(item.getCollectionName(),Home_Page.sendSelectedCollection)) // && item.getDeckID(), Cards_In_Collection.sendDeckID
                    {
                       listItemDecks.add(item.getDeckID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GoalsAndStats.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });


        //Count the number of cards in each deck
        for(int i=0; i < listItemDecks.size(); i++)
        {
            for(int j=0; j < listDeckIDs.size(); j++)
            {
                if (listItemDecks.get(i).equals(listDeckIDs.get(j)))
                {
                    Integer iVal = listNoCardsInDeck.get(j);
                    iVal++;
                    listNoCardsInDeck.set(j,iVal);
                    break;
                }
            }
        }

        //Sort the decks by most cards
        Integer iTemp;
        String sTemp;
        for(int i=0; i < listNoCardsInDeck.size(); i++)
        {
            for(int j=1; j < (listNoCardsInDeck.size()-i); j++)
            {
                if(listNoCardsInDeck.get(j-1) > listNoCardsInDeck.get(j))
                {
                    //Sorting the number of cards per deck
                    iTemp = listNoCardsInDeck.get(j-1);
                    listNoCardsInDeck.set(j-1, listNoCardsInDeck.get(j));
                    listNoCardsInDeck.set(j, iTemp);

                    //Sorting the names
                    sTemp = listDeckNames.get(j-1);
                    listDeckNames.set(j-1, listDeckNames.get(j));
                    listDeckNames.set(j, sTemp);
                    //The DeckIDs here can be abandoned because they are not used in the graph
                }

            }
        }
        //Get the percentage of each Deck
        Integer iTotal = 0;
        double dPerc;
        for(Integer num: listNoCardsInDeck)
        {
            iTotal += num;
        }
        for(int i=0; i < listNoCardsInDeck.size(); i++)
        {
            dPerc = listNoCardsInDeck.get(i) / iTotal;
            listPercents.add((int) (Math.round(dPerc) * 100));
        }

        //if more than 10 decks, add the rest of the cards to other
        Integer iOther = 0;
        if (listPercents.size() >= 10)
        {
            for(int i=9; i < listNoCardsInDeck.size(); i++)
                iOther += listPercents.get(i);

            listPercents.set(9, iOther);
            listDeckNames.set(9, "Other");
        }

        //Add Percentages to pie chart
        switch (listPercents.size())
        {
            case 1:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                break;
            case 2:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                break;
            case 3:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                break;
            case 4:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                break;
            case 5:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(4), listPercents.get(4),Color.parseColor("#FF0398")));
                break;
            case 6:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(4), listPercents.get(4),Color.parseColor("#FF0398")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(5), listPercents.get(5),Color.parseColor("#5502BD")));
                break;
            case 7:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(4), listPercents.get(4),Color.parseColor("#FF0398")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(5), listPercents.get(5),Color.parseColor("#5502BD")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(6), listPercents.get(6),Color.parseColor("#FDA600")));
                break;
            case 8:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(4), listPercents.get(4),Color.parseColor("#FF0398")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(5), listPercents.get(5),Color.parseColor("#5502BD")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(6), listPercents.get(6),Color.parseColor("#FDA600")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(7), listPercents.get(7),Color.parseColor("#FF000000")));
                break;
            case 9:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(4), listPercents.get(4),Color.parseColor("#FF0398")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(5), listPercents.get(5),Color.parseColor("#5502BD")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(6), listPercents.get(6),Color.parseColor("#FDA600")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(7), listPercents.get(7),Color.parseColor("#FF000000")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(8), listPercents.get(8),Color.parseColor("#85E3FF")));
                break;
            case 10:
                pieChart.addPieSlice(new PieModel(listDeckNames.get(0), listPercents.get(0),Color.parseColor("#FF5E5E")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(1), listPercents.get(1),Color.parseColor("#00B3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(2), listPercents.get(2),Color.parseColor("#FFEC03")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(3), listPercents.get(3),Color.parseColor("#7FE970")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(4), listPercents.get(4),Color.parseColor("#FF0398")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(5), listPercents.get(5),Color.parseColor("#5502BD")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(6), listPercents.get(6),Color.parseColor("#FDA600")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(7), listPercents.get(7),Color.parseColor("#FF000000")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(8), listPercents.get(8),Color.parseColor("#85E3FF")));
                pieChart.addPieSlice(new PieModel(listDeckNames.get(9), listPercents.get(9),Color.parseColor("#808080")));
                break;
        }

// NAV DRAWER---------------------------------------------------------------------------------------
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);//EVERY PAGE NEEDS A DRAWERLAYOUT ID
        mDrawerLayout.addDrawerListener(mToggle);//DylanA

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();

        navView = findViewById(R.id.nav_side_menu) ;
        navView.setNavigationItemSelectedListener(this);
// -------------------------------------------------------------------------------------------------
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;//DylanA
        }

        return super.onOptionsItemSelected(item);
    }

    //@SuppressLint("NonConstantResourceId") IDK if this produces errors??
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_myCollections:
                startActivity(new Intent(GoalsAndStats.this, Home_Page.class));
                break;
            case R.id.nav_decks:
                startActivity(new Intent(GoalsAndStats.this, Deck_Screen.class));
                break;
            case R.id.nav_stats:
                startActivity(new Intent(GoalsAndStats.this, GoalsAndStats.class));
                break;
            case R.id.nav_signOut:
                startActivity(new Intent(GoalsAndStats.this, MainActivity.class));//Sends User to Login Screen
                break;
        }
        return true;
    }

}