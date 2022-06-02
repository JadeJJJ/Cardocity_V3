package com.varsitycollege.cardocity_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class Create_Collection extends AppCompatActivity {
    EditText collectionID;
    EditText collectionName;
    EditText goalItems;
    Button createCollectionBtn;
    private DrawerLayout mDrawerLayout; //DylanA
    private ActionBarDrawerToggle mToggle; //DylanA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);
        collectionID = findViewById(R.id.collectionID);
        collectionName = findViewById(R.id.collectionName);
        goalItems = findViewById(R.id.goalItems);
        createCollectionBtn = findViewById(R.id.Create_Collection);

        createCollectionBtn.setOnClickListener(view -> {

            String ID = collectionID.getText().toString();
            String Name = collectionName.getText().toString();
            Integer Goal = 0;
            boolean bFlag = true;
            DatabaseCPrt2 db = new DatabaseCPrt2();
            InputValidation iv = new InputValidation();

            if (!iv.NotNullorEmpty(ID))
            {
                bFlag = false;
                collectionID.setError("Please enter a Collection ID!!");
            }

            if (!iv.NotNullorEmpty(Name))
            {
                bFlag = false;
                collectionName.setError("Please enter a Collection Name!!");
            }

            if (!iv.NotNullorEmpty(goalItems.getText().toString()))
            {
                bFlag = false;
                goalItems.setError("Please enter a Goal Number!!");
            }
            else
            {
                try
                {
                    Goal = Integer.parseInt(goalItems.getText().toString());
                } catch(Exception ex)
                {
                    bFlag = false;
                    goalItems.setError("Goal Number is invalid!!");
                }
            }

            if (bFlag)
            {
                Collection coll = new Collection(ID, Name, Goal);
                db.SetDeck(coll);
                iv.msg("Collection Created!!", Create_Collection.this);
                startActivity(new Intent(Create_Collection.this,Cards_In_Collection.class));
            }
            else
                iv.msg("Failed to Create Collection!!", Create_Collection.this);


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//DylanA

        if(mToggle.onOptionsItemSelected(item)){//DylanA
            return true;//DylanA
        }

        return super.onOptionsItemSelected(item);//DylanA
    }


}