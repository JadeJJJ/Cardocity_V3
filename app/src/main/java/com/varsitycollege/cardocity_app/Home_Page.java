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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button CreateCollectionBtn;
    private Button SelectCollectionBTN;
    private DrawerLayout mDrawerLayout; //DylanA
    private ActionBarDrawerToggle mToggle; //DylanA
    private ListView lstvCollections;
    private ArrayAdapter<String> collAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference collRef = database.getReference("Collection");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_10_collection_screen);
        CreateCollectionBtn = findViewById(R.id.HP_Create_Collection);
        SelectCollectionBTN = findViewById(R.id.HP_Select_Collection);
        lstvCollections = findViewById(R.id.lstvCollections);
        List<String> collList;

// ListView Show------------------------------------------------------------------------------------
        collList = new ArrayList<>();

        collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Collection coll = pulledOrder.getValue(Collection.class);
                    collList.add(coll.StringOut());
                }

                // create the adapter to display the items
                collAdapter = new ArrayAdapter<String>(Home_Page.this, android.R.layout.simple_list_item_1, collList);
                lstvCollections.setAdapter(collAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_Page.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });

// Button Clicks------------------------------------------------------------------------------------
        CreateCollectionBtn.setOnClickListener(view -> {
            startActivity(new Intent(Home_Page.this,Create_Collection.class));
        });

        SelectCollectionBTN.setOnClickListener(view ->{
            startActivity(new Intent(Home_Page.this,Cards_In_Collection.class));
        });

// NAV DRAWER---------------------------------------------------------------------------------------
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//DylanA
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);//DylanA EVERY PAGE NEEDS A DRAWERLAYOUT ID
        mDrawerLayout.addDrawerListener(mToggle);//DylanA

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close); //DylanA
        mToggle.syncState();//DylanA
// -------------------------------------------------------------------------------------------------
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
            case R.id.nav_decks:
                startActivity(new Intent(Home_Page.this, Create_Collection.class));
                break;

        }
        return true;
    }
}