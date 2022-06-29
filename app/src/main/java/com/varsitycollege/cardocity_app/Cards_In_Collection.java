package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cards_In_Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button addItemBtn;
    private Button btnSelect;
    private Button btnAddToDeck;
    private ListView lstvOrderHistory;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");
    private DatabaseReference collRef = database.getReference("Collection");
    private DatabaseReference deckRef = database.getReference("Deck");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public static String selectedItem;
    private DrawerLayout mDrawerLayout; //navbar
    private ActionBarDrawerToggle mToggle; //navbar
    private NavigationView navView;//navbar
    private Button btnGen;
    private FloatingActionButton btnAchieve;
    private GridView myGridView;
    ArrayList<Item> myArrayList = new ArrayList<>();

    //Goal Variables
    private int totalNumberOfCards = 0; //The amount of cards in the collection
    private int totalUniqueNumberOfCards = 0; //The amount of unique cards in the collection
    private int colGoal; //The goal for the collection
    private TextView goalUniqueTextView;
    private TextView totalGoalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_in_collection);
        addItemBtn=findViewById(R.id.addItem);
        btnSelect = findViewById(R.id.btnSelectItem);
        btnAddToDeck = findViewById(R.id.btnAddtoDeck);
        goalUniqueTextView = findViewById(R.id.txtDisplayGoal);
        totalGoalTextView = findViewById(R.id.txtDisplayTotalGoal);
        btnGen = findViewById(R.id.btnGen);
        btnAchieve = findViewById(R.id.fabAchievements);
        myGridView = findViewById(R.id.myGridView);



// Adding to List View------------------------------------------------------------------------------
        List<String> itemListName = new ArrayList<>();
        List<String> itemListType = new ArrayList<>();
        List<String> itemListNumCards = new ArrayList<>();
        //ListView lstvCardsName = findViewById(R.id.lstvCardsName);
        //ListView lstvCardsType = findViewById(R.id.lstvCardsType);
        //ListView lstvCardsNumCards = findViewById(R.id.lstvCardsNumCards);
        String userid = MainActivity.UserID;
        Spinner itemSpinner = findViewById(R.id.spnSelectItem);

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Item item = pulledOrder.getValue(Item.class);
                    if (Objects.equals(item.getUserID(), userid) && Objects.equals(item.getCollectionName(),Home_Page.sendSelectedCollection))
                    {
                        itemListName.add(item.getCardName());
                        itemListType.add(item.getCardType());
                        itemListNumCards.add(item.getNumberOfCards().toString());
                        totalUniqueNumberOfCards ++;
                        totalNumberOfCards += item.getNumberOfCards();
                        myArrayList.add(item);

                    }
                }
            /*
                ArrayAdapter<String> itemNameAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListName);
                lstvCardsName.setAdapter(itemNameAdapter);
                ArrayAdapter<String> itemTypeAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListType);
                lstvCardsType.setAdapter(itemTypeAdapter);
                ArrayAdapter<String> itemNumAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListNumCards);
                lstvCardsNumCards.setAdapter(itemNumAdapter);
                */
                MyAdapter myAdapter = new MyAdapter(Cards_In_Collection.this, myArrayList);
                myGridView.setAdapter(myAdapter);

                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_spinner_item, itemListName);
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                itemSpinner.setAdapter(spnAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cards_In_Collection.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });
        collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Collection myCollection = pulledOrder.getValue(Collection.class);
                    if(Objects.equals(Home_Page.sendSelectedCollection, myCollection.getCollectionName()))
                    {
                        colGoal = myCollection.getGoalItems();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addItemBtn.setOnClickListener(view -> {
            startActivity(new Intent(Cards_In_Collection.this,Add_Item.class));
         });
        btnGen.setOnClickListener(view -> {
            generateData();
        });

        btnSelect.setOnClickListener(view ->{
            // TODO Check that the item exists (if not the display an error)
            // TODO Once the item is found go to edit items screen
            selectedItem = itemSpinner.getSelectedItem().toString();
            startActivity(new Intent(Cards_In_Collection.this,View_Item.class));
        });

        // TODO Remove button and put in edit item
        btnAddToDeck.setOnClickListener(view -> {
            // TODO Check deck exists
            // TODO Add selected card to entered deck
        });
        //The handler to make the TextView text change be delayed by 1200ms
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goalUniqueTextView.setText("Goal(Unique Cards): " + totalUniqueNumberOfCards + "/" + colGoal);
                totalGoalTextView.setText("Goal(Total Cards): " + totalNumberOfCards + " / " + colGoal);
            }
        }, 1200);

        btnAchieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cards_In_Collection.this, Achiements.class));
            }
        });

// NAV DRAWER---------------------------------------------------------------------------------------
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);//DylanA EVERY PAGE NEEDS A DRAWERLAYOUT ID
        mDrawerLayout.addDrawerListener(mToggle);//DylanA

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();//DylanA

        navView = findViewById(R.id.nav_side_menu) ;
        navView.setNavigationItemSelectedListener(this);
// -------------------------------------------------------------------------------------------------
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//DylanA

        if(mToggle.onOptionsItemSelected(item)){//DylanA
            return true;//DylanA
        }

        return super.onOptionsItemSelected(item);//DylanA
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_myCollections:
                startActivity(new Intent(Cards_In_Collection.this, Home_Page.class));
                break;
            case R.id.nav_decks:
                startActivity(new Intent(Cards_In_Collection.this, Deck_Screen.class));//
                break;
            case R.id.nav_stats:
                startActivity(new Intent(Cards_In_Collection.this, GoalsAndStats.class));
                break;
            case R.id.nav_signOut:
                startActivity(new Intent(Cards_In_Collection.this, MainActivity.class));//Sends User to Login Screen
                break;
        }
        return true;
    }

    public void generateData(){
        //Preset
        String userID = MainActivity.UserID;
        String collectionName = Home_Page.sendSelectedCollection;
        Integer iSN = 1;
    //--------------------------------------------------------------------------------------------//
        Deck newDeck1 = new Deck(1, "Green White", collectionName, 8, userID);
        deckRef.push().setValue(newDeck1);

        Item newItem1 = new Item(iSN.toString(), "Emma Tandris", "Creature", 1, "Default.jpg", collectionName, userID, "01-01-2019", 1);
        itemRef.push().setValue(newItem1);
        iSN++;

        Item newItem2 = new Item(iSN.toString(), "Blossoming Sands", "Land", 4, "Default.jpg", collectionName, userID, "01-01-2019", 1);
        itemRef.push().setValue(newItem2);
        iSN++;

        Item newItem3 = new Item(iSN.toString(), "Wilt-Leaf Liege", "Creature", 1, "Default.jpg", collectionName, userID, "01-01-2019", 1);
        itemRef.push().setValue(newItem3);
        iSN++;

        Item newItem4 = new Item(iSN.toString(), "Sigarda, Heron's Grace", "Creature", 1, "Default.jpg", collectionName, userID, "01-01-2019", 1);
        itemRef.push().setValue(newItem4);
        iSN++;

        //Deck 2------------------------------------------------------------------------------------
        Deck newDeck2 = new Deck(2, "Green", collectionName, 25, userID);
        deckRef.push().setValue(newDeck2);

        Item newItem5 = new Item(iSN.toString(), "Full Art Forest", "Land", 2, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem5);
        iSN++;

        Item newItem6 = new Item(iSN.toString(), "Forest", "Land", 8, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem6);
        iSN++;

        Item newItem7 = new Item(iSN.toString(), "Forest", "Land", 8, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem7);
        iSN++;

        Item newItem8 = new Item(iSN.toString(), "Hydra Broodmaster", "Creature", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem8);
        iSN++;

        Item newItem9 = new Item(iSN.toString(), "Mossbridge Troll", "Creature", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem9);
        iSN++;

        Item newItem10 = new Item(iSN.toString(), "Fog", "Instant", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem10);
        iSN++;

        Item newItem11 = new Item(iSN.toString(), "Elvish Mystic", "Creature", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem11);
        iSN++;

        Item newItem12 = new Item(iSN.toString(), "Zendikar's Roil", "Enchantment", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem12);
        iSN++;

        Item newItem13 = new Item(iSN.toString(), "Groundskeeper", "Creature", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem13);
        iSN++;

        Item newItem14 = new Item(iSN.toString(), "Beast Within", "Instant", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem14);
        iSN++;

        Item newItem15 = new Item(iSN.toString(), "From Beyond", "Enchantment", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem15);
        iSN++;

        Item newItem16 = new Item(iSN.toString(), "Worldspin Wurm", "Creature", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem16);
        iSN++;

        Item newItem17 = new Item(iSN.toString(), "Garruk, Primal Hunter", "Planeswalker", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem17);
        iSN++;

        Item newItem18 = new Item(iSN.toString(), "Gladeheart Cavalry", "Creature", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem18);
        iSN++;

        Item newItem19 = new Item(iSN.toString(), "Second Harvest", "Instant", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem19);
        iSN++;

        Item newItem20 = new Item(iSN.toString(), "Pulse of Murasa", "Instant", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem20);
        iSN++;

        Item newItem21 = new Item(iSN.toString(), "Zendikar Resurgent", "Enchantment", 1, "Default.jpg", collectionName, userID, "29-04-2019", 2);
        itemRef.push().setValue(newItem21);
        iSN++;

        //Deck 3------------------------------------------------------------------------------------
        Deck newDeck3 = new Deck(3, "Artifacts", collectionName, 7, userID);
        deckRef.push().setValue(newDeck3);

        itemRef.push().setValue(new Item(iSN.toString(), "Sol Ring", "Artifact", 1, "Default.jpg", collectionName, userID, "07-07-2019", 3));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Akroma's Memorial", "Artifact", 1, "Default.jpg", collectionName, userID, "07-07-2019", 3));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Traveler's Amulet", "Artifact", 1, "Default.jpg", collectionName, userID, "07-07-2019", 3));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Loxodon Warhammer", "Artifact", 1, "Default.jpg", collectionName, userID, "07-07-2019", 3));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Elixir of Immortality", "Artifact", 1, "Default.jpg", collectionName, userID, "07-07-2019", 3));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Though Vessel", "Artifact", 1, "Default.jpg", collectionName, userID, "07-07-2019", 3));
        iSN++;

        //Deck 4------------------------------------------------------------------------------------
        deckRef.push().setValue(new Deck(4, "White", collectionName, 24, userID));

        itemRef.push().setValue(new Item(iSN.toString(), "Full Art Plain", "Land", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Plain", "Land", 8, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Espeth, Sun's Champion", "Planeswalker", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Intangible Virtue", "Enchantment", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Eyes in the Skies", "Instant", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Erase", "Instant", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Dazzling Reflection", "Instant", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Felidar Sovereign", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Inquisitor Exarch", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Mentor of the Meek", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Brimaz, King of Oreskos", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Fortified Rampart", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Pride Guardian", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Geist-Honored Monk", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Hanweir Militia Captain", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;
        itemRef.push().setValue(new Item(iSN.toString(), "Suture Priest", "Creature", 1, "Default.jpg", collectionName, userID, "07-08-2019", 4));
        iSN++;





    }
}