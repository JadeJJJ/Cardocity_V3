package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class Home_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button CreateCollectionBtn;
    Button SelectCollectionBTN;
    Button ViewGoalsBTN;
    private DrawerLayout mDrawerLayout; //DylanA
    private ActionBarDrawerToggle mToggle; //DylanA


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_10_collection_screen);
        //Create Collection Button------------------------------------------------------------------
        CreateCollectionBtn = findViewById(R.id.HP_Create_Collection);
        CreateCollectionBtn.setOnClickListener(view -> {
            startActivity(new Intent(Home_Page.this,Create_Collection.class));
        });
        //Select Collection Button------------------------------------------------------------------
        SelectCollectionBTN = findViewById(R.id.HP_Select_Collection);
        SelectCollectionBTN.setOnClickListener(view ->{
            startActivity(new Intent(Home_Page.this,Cards_In_Collection.class));
        });
        //View Goals Button-------------------------------------------------------------------------
        ViewGoalsBTN = findViewById(R.id.HP_View_Goals);
        ViewGoalsBTN.setOnClickListener(view ->{
            startActivity(new Intent(Home_Page.this,Cards_In_Collection.class));//TO DO: Create ViewGoals Activity
        } );
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