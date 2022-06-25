package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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

public class Cards_In_Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button addItemBtn;
    private Button btnSelect;
    private Button btnAddToDeck;
    private ListView lstvOrderHistory;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference itemRef = database.getReference("Item");
    private DatabaseReference collRef = database.getReference("Collection");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public static String selectedItem;
    private DrawerLayout mDrawerLayout; //navbar
    private ActionBarDrawerToggle mToggle; //navbar
    private NavigationView navView;//navbar

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



        // TODO Add items from the database to the list in a collection
// Adding to List View------------------------------------------------------------------------------
        List<String> itemListName = new ArrayList<>();
        List<String> itemListType = new ArrayList<>();
        List<String> itemListNumCards = new ArrayList<>();
        ListView lstvCardsName = findViewById(R.id.lstvCardsName);
        ListView lstvCardsType = findViewById(R.id.lstvCardsType);
        ListView lstvCardsNumCards = findViewById(R.id.lstvCardsNumCards);
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

                    }
                }

                ArrayAdapter<String> itemNameAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListName);
                lstvCardsName.setAdapter(itemNameAdapter);
                ArrayAdapter<String> itemTypeAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListType);
                lstvCardsType.setAdapter(itemTypeAdapter);
                ArrayAdapter<String> itemNumAdapter = new ArrayAdapter<String>(Cards_In_Collection.this, android.R.layout.simple_list_item_1, itemListNumCards);
                lstvCardsNumCards.setAdapter(itemNumAdapter);

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
}