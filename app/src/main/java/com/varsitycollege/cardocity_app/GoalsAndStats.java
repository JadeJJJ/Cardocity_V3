package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.Button;

public class GoalsAndStats extends AppCompatActivity {
    private Button UpdateChartBTN;
    private DrawerLayout mDrawerLayout; //DylanA
    private ActionBarDrawerToggle mToggle; //DylanA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_and_goals);
// Button Clicks------------------------------------------------------------------------------------
        UpdateChartBTN = findViewById(R.id.btnUpdateChart);
        //TODO: Update Pie Chart FUNCTION

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


}