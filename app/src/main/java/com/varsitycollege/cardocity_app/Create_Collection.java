package com.varsitycollege.cardocity_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Create_Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText collectionName;
    EditText goalItems;
    Button createCollectionBtn;
    private DrawerLayout mDrawerLayout; //navbar
    private ActionBarDrawerToggle mToggle; //navbar
    private NavigationView navView;//navbar

    private Integer GenID()
    {
        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference itemRef = database.getReference("Collection");
        List<String> itemList = new ArrayList<>();

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Collection coll = pulledOrder.getValue(Collection.class);
                    itemList.add(coll.toString());
                }
                id = itemList.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Create_Collection.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }/
        }); */
        boolean bFlag = true;
        Integer id = 0;
        Integer i1= (int)Math.floor(Math.random()*(9-1+1)+1);
        Integer i2= (int)Math.floor(Math.random()*(9-0+1)+0);
        Integer i3= (int)Math.floor(Math.random()*(9-0+1)+0);
        Integer i4= (int)Math.floor(Math.random()*(9-0+1)+0);
        Integer i5= (int)Math.floor(Math.random()*(9-0+1)+0);
        Integer i6= (int)Math.floor(Math.random()*(9-0+1)+0);
        String newId = i1.toString()+i2.toString()+i3.toString()+i4.toString()+i5.toString()+i6.toString();
            try {
                id = Integer.parseInt(newId);
                bFlag = false;
            } catch (Exception ex)
            {
                Toast.makeText(Create_Collection.this, ex.toString(), Toast.LENGTH_SHORT).show();
            }

        return id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);
        collectionName = findViewById(R.id.collectionName);
        goalItems = findViewById(R.id.goalItems);
        createCollectionBtn = findViewById(R.id.Create_Collection);

        createCollectionBtn.setOnClickListener(view -> {

            Integer ID = GenID();
            String Name = collectionName.getText().toString();
            Integer Goal = 0;
            boolean bFlag = true;
            DatabaseCPrt2 db = new DatabaseCPrt2();
            InputValidation iv = new InputValidation();

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
                Collection coll = new Collection(ID, Name, Goal, MainActivity.UserID);
                db.SetCollection(coll);
                iv.msg("Collection Created!!", Create_Collection.this);
                startActivity(new Intent(Create_Collection.this, Home_Page.class));
            }
            else
                iv.msg("Failed to Create Collection!!", Create_Collection.this);

        });

// NAV DRAWER---------------------------------------------------------------------------------------
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);// EVERY PAGE NEEDS A DRAWERLAYOUT ID
        mDrawerLayout.addDrawerListener(mToggle);

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
                startActivity(new Intent(Create_Collection.this, Home_Page.class));
                break;
            case R.id.nav_decks:
                startActivity(new Intent(Create_Collection.this, Deck_Screen.class));
                break;
            case R.id.nav_stats:
                startActivity(new Intent(Create_Collection.this, GoalsAndStats.class));
                break;
            case R.id.nav_signOut:
                startActivity(new Intent(Create_Collection.this, MainActivity.class));//Sends User to Login Screen
                break;
        }
        return true;
    }
}