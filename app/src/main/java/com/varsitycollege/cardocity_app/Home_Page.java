package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class Home_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button CreateCollectionBtn;
    private Button SelectCollectionBTN;
    //private Spinner collSpinner;
    private DrawerLayout mDrawerLayout; //DylanA
    private ActionBarDrawerToggle mToggle; //DylanA
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference collRef = database.getReference("Collection");
    private NavigationView navView; //DylanA

    //public static String selectedCollection;
    public static String sendSelectedCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_10_collection_screen);

// Adding to List View------------------------------------------------------------------------------
        List<String> collListID = new ArrayList<>();
        List<String> collListName = new ArrayList<>();
        ListView lstvCollectionsID = findViewById(R.id.lstvCollectionsID);
        ListView lstvCollectionsName = findViewById(R.id.lstvCollectionsName);
        Spinner collSpinner = findViewById(R.id.spnCollections);
        String userid = MainActivity.UserID;
        collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    com.varsitycollege.cardocity_app.Collection coll = pulledOrder.getValue(com.varsitycollege.cardocity_app.Collection.class);
                    if (Objects.equals(coll.getUserID(), userid))
                    {
                        collListID.add(coll.getCollectionID().toString());
                        collListName.add(coll.getCollectionName());
                    }


                }

                ArrayAdapter<String> collAdapter = new ArrayAdapter<String>(Home_Page.this, android.R.layout.simple_list_item_1, collListID);
                lstvCollectionsID.setAdapter(collAdapter);
                ArrayAdapter<String> collAdapterName = new ArrayAdapter<String>(Home_Page.this, android.R.layout.simple_list_item_1, collListName);
                lstvCollectionsName.setAdapter(collAdapterName);



                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Home_Page.this, android.R.layout.simple_spinner_item, collListName);
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                collSpinner.setAdapter(spnAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_Page.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

// NAV DRAWER---------------------------------------------------------------------------------------
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//DylanA
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);//DylanA EVERY PAGE NEEDS A DRAWERLAYOUT ID
        mDrawerLayout.addDrawerListener(mToggle);//DylanA

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close); //DylanA
        mToggle.syncState();//DylanA

        navView = findViewById(R.id.nav_side_menu) ;
        navView.setNavigationItemSelectedListener(this);
// -------------------------------------------------------------------------------------------------

    //Spinner listener
       /* collSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCollection = collSpinner.getSelectedItem().toString();
                Toast.makeText(Home_Page.this, selectedCollection, Toast.LENGTH_SHORT).show();
                //selectedCollection = adapterView.getItemAtPosition(i).toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

// Button Clicks------------------------------------------------------------------------------------
        CreateCollectionBtn = findViewById(R.id.HP_Create_Collection);
        CreateCollectionBtn.setOnClickListener(view -> {
            startActivity(new Intent(Home_Page.this,Create_Collection.class));
        });

        SelectCollectionBTN = findViewById(R.id.HP_Select_Collection);
        SelectCollectionBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCollection = collSpinner.getSelectedItem().toString();
                sendSelectedCollection = selectedCollection;
                if (selectedCollection.equals(null) || selectedCollection.equals(""))
                {
                    Toast.makeText(Home_Page.this, "Please select a collection!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(new Intent(Home_Page.this,Cards_In_Collection.class));
                }
            }
        });
    }
//enable action bar tabbing DylanA


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
                startActivity(new Intent(Home_Page.this, Home_Page.class));
                break;
            case R.id.nav_decks:
                startActivity(new Intent(Home_Page.this, Deck_Screen.class));
                break;
            case R.id.nav_stats:
                startActivity(new Intent(Home_Page.this, GoalsAndStats.class));
                break;
            case R.id.nav_signOut:
                startActivity(new Intent(Home_Page.this, MainActivity.class));//Sends User to Login Screen
                break;
        }
        return true;
    }
}