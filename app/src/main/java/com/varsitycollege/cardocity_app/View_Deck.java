package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class View_Deck extends AppCompatActivity {

    //Buttons
    private Button btnMakeEditable;
    private Button btnDelete;
    private Button btnFinalise;
    private Button btnCancel;

    //TextViews
    private TextView tvDeckName;
    private TextView tvCollName;
    private TextView tvTotalNoCards;

    //Database Stuff
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference deckRef = database.getReference("Deck");
    DatabaseCPrt2 dataHand = new DatabaseCPrt2();
    private Integer deckid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deck);

        // Setting IDs
        btnMakeEditable = findViewById(R.id.btnEditDeck);
        btnDelete = findViewById(R.id.btnDeleteDeck);
        btnFinalise = findViewById(R.id.btnFinaliseDeck);
        btnCancel = findViewById(R.id.btnCancelDeckEdit);
        tvDeckName = findViewById(R.id.editDeckName);
        tvCollName = findViewById(R.id.editColl);
        tvTotalNoCards = findViewById(R.id.editTot);

        btnFinalise.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        String userid = MainActivity.UserID;

        String selectedDeck = Deck_Screen.selectedDeck;

        //Giving the Edits their values
        deckRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot pulledOrder : snapshot.getChildren()){
                    Deck deck = pulledOrder.getValue(Deck.class);

                    if (Objects.equals(deck.getUserID(), userid) && Objects.equals(deck.getDeckName(), selectedDeck))
                    {
                        tvDeckName.setText(deck.getDeckName());
                        tvCollName.setText(deck.getChooseCollection());
                        tvTotalNoCards.setText(deck.getTotalNumCars().toString());
                        deckid = deck.getDeckID();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(View_Deck.this, "Error Reading from Database", Toast.LENGTH_SHORT).show();
            }
        });


        btnMakeEditable.setOnClickListener(view ->{
            tvDeckName.setClickable(true);
            tvCollName.setClickable(true);
            tvTotalNoCards.setClickable(true);

            btnDelete.setVisibility(View.GONE);
            btnMakeEditable.setVisibility(View.GONE);
            btnFinalise.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        });

        btnFinalise.setOnClickListener(view ->{
            String newDeckName = tvDeckName.getText().toString();
            String newCollName = tvCollName.getText().toString();
            Integer newTotCards = Integer.parseInt(tvTotalNoCards.getText().toString());

            if (!newDeckName.isEmpty() && !newCollName.isEmpty() && newTotCards != 0)
            {
                Deck newDeck = new Deck(deckid, newDeckName, newCollName, newTotCards, userid);
                dataHand.updateDeck(deckid, selectedDeck, newDeck, userid);
                Toast.makeText(View_Deck.this, "Deck Updated!!", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(View_Deck.this, "Please Make sure all fields have Value", Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(View_Deck.this, Deck_Screen.class));
        });

        btnCancel.setOnClickListener(view ->{
            startActivity(new Intent(View_Deck.this, Deck_Screen.class));
        });

        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dataHand.deleteDeck(deckid);
                    Toast.makeText(View_Deck.this, "Deck Deleted!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(View_Deck.this, Deck_Screen.class));
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        });

    }
}