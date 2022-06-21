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

public class GoalsAndStats extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button UpdateChartBTN;
    private DrawerLayout mDrawerLayout; //navbar
    private ActionBarDrawerToggle mToggle; //navbar
    private NavigationView navView;//navbar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_and_goals);
// Button Clicks------------------------------------------------------------------------------------
        UpdateChartBTN = findViewById(R.id.btnUpdateChart);
        //TODO: Update Pie Chart FUNCTION
        //TODO: Pie chart needs to be percentages of cards in deck
        //The sections work just like a list so it is easy to add more
        //follow this link for tut:
        //https://www.geeksforgeeks.org/how-to-add-a-pie-chart-into-an-android-application/

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