package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_and_goals);
// Button Clicks------------------------------------------------------------------------------------
        UpdateChartBTN = findViewById(R.id.btnUpdateChart);
// OnCreate Pie Charts------------------------------------------------------------------------------
        //TODO: Update Pie Chart FUNCTION
        //TODO: Pie chart needs to be percentages of cards in deck
        //The sections work just like a list so it is easy to add more
        //follow this link for tut:
        //https://www.geeksforgeeks.org/how-to-add-a-pie-chart-into-an-android-application/
        String userid = MainActivity.UserID;
        List<String> listItemDecks = new ArrayList<>(); // All the deckIDs of the cards in the collection
        List<String> listDeckIDs = new ArrayList<>(); // All the deckIDs in a collection
        List<String> listDeckNames = new ArrayList<>(); // All the deckNames
        List<Integer> listNoCardsInDeck = new ArrayList<>();
        List<Integer> listPercents = new ArrayList<>();

        //TODO: Get the DeckIDs as a list
        /*
        deckRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Deck deck = pulledOrder.getValue(Deck.class);
                    // Finds the Decks for that user from a collection
                    if (Objects.equals(deck.getUserID(), userid) && Objects.equals(deck.getCollectionName(),Home_Page.sendSelectedCollection)) //&& Objects.equals(item.getDeckID(), Cards_In_Collection.sendDeckID)
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
        }); */

        // Gets the item
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);
                    // Finds the items for that user
                    if (Objects.equals(item.getUserID(), userid) && Objects.equals(item.getCollectionName(),Home_Page.sendSelectedCollection)) // && item.getDeckID(), Cards_In_Collection.sendDeckID
                    {
                       // itemListName.add(item.getCardName());
                       /*
                       listItemDecks.add(item.getDeckID());
                       */
                    }
                }

                //Count the number of cards in each deck
                for(int i=0; i < listItemDecks.size(); i++)
                {
                    for(int j=0; j < listDeckIDs.size(); j++)
                    {
                        if (listItemDecks.get(i).equals(listDeckIDs.get(j)))
                        {
                            listNoCardsInDeck.get(j)++;
                            break;
                        }
                    }
                }

                //Sort the decks by most cards
                Integer iTemp = 0;
                String sTemp = "";
                for(int i=0; i < listNoCardsInDeck.size(); i++)
                {
                    for(int j=1; j < (listNoCardsInDeck.size()-i); j++)
                    {
                        if(listNoCardsInDeck.get(j-1) > listNoCardsInDeck.get(j))
                        {
                            //Sorting the number of cards per deck
                            iTemp = listNoCardsInDeck.get(j-1);
                            listNoCardsInDeck.get(j-1) = listNoCardsInDeck.get(j);
                            listNoCardsInDeck.get(j) = iTemp;

                            //Sorting the names
                            sTemp = listDeckNames.get(j-1);
                            listDeckNames.get(j-1) = listDeckNames.get(j);
                            listDeckNames.get(j) = sTemp;
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
                    listPercents.add((int) Math.round(dPerc));
                }

                //TODO: if more than 10 decks, add the rest of the cards to other
                //TODO: Add Percentages to pie chart

                /*
                ArrayAdapter<String> itemNameAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListName);
                lstvCardsName.setAdapter(itemNameAdapter);
                ArrayAdapter<String> itemTypeAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListType);
                lstvCardsType.setAdapter(itemTypeAdapter);
                ArrayAdapter<String> itemNumAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListNumCards);
                lstvCardsNumCards.setAdapter(itemNumAdapter);

                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_spinner_item, itemListName);
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                itemSpinner.setAdapter(spnAdapter);*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GoalsAndStats.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_myCollections:
                startActivity(new Intent(GoalsAndStats.this, Home_Page.class));
                break;
            case R.id.nav_decks:
                startActivity(new Intent(GoalsAndStats.this, Home_Page.class));//TODO: needs to reroute to decks activity not home_page
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