package com.varsitycollege.cardocity_app;

import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseCPrt2 {

    //The connection to the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    //References
    //User Table
    // private DatabaseReference userRef = database.getReference("tableName");

    private static FirebaseAuth userRef = FirebaseAuth.getInstance();

    //Item Table
    private DatabaseReference itemRef = database.getReference("Item");

    //Deck Table
    private DatabaseReference deckRef = database.getReference("Deck");

    //Collection Table
    private DatabaseReference collRef = database.getReference("Collection");

    //Storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Get a non-default Storage bucket
    FirebaseStorage imageStorage = FirebaseStorage.getInstance("gs://imageStorage");
    // Create a storage reference from our app
    StorageReference storeRef = storage.getReference();
    StorageReference imgRef = storeRef.child("images");

    //Lists for storing the data
    private List<Item> itemList = new ArrayList<Item>();
    private List<Deck> deckList = new ArrayList<Deck>();
    private List<Collection> collList = new ArrayList<Collection>();

    private static boolean flag = false;

    //---------------------------------GetLogin-----------------------------//
  /*  public static boolean GetLogin(String email, String password) {
        userRef = FirebaseAuth.getInstance();
        userRef.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag; // Returns list to the other class
        //If loginList = null then must show the toast message in the Activity
    }

    //---------------------------------SetLogin-----------------------------//
    public static boolean SetLogin(String email, String password) {
        userRef.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        });
        return flag;
    } */

    //---------------------------------GetItem-----------------------------//
    public List<Item> GetItem(){
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledLogin : snapshot.getChildren()) {
                    Item item = pulledLogin.getValue(Item.class);
                    itemList.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });

        return itemList;
    }

    //---------------------------------SetItem-----------------------------//
    public void SetItem(Item newItem, Bitmap image) {
        StorageReference newRef = storeRef.child(newItem.getCardImageLink());
        StorageReference newImgRef = storeRef.child("images/"+newItem.getCardImageLink());
        newRef.getName().equals(newImgRef.getName());
        newRef.getPath().equals(newImgRef.getPath());

        ByteArrayOutputStream outStr = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, outStr);
        byte[] myData = outStr.toByteArray();

        UploadTask upTask = newRef.putBytes(myData);
        upTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //If failed
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Was successful
            }
        });

        itemRef.push().setValue(newItem);
    }

    //---------------------------------GetDeck-----------------------------//
    public List<Deck> GetDeck(){
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledLogin : snapshot.getChildren()) {
                    Deck deck = pulledLogin.getValue(Deck.class);
                    deckList.add(deck);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });

        return deckList;
    }

    //---------------------------------SetDeck-----------------------------//
    public void SetDeck(Deck newDeck) {
        deckRef.push().setValue(newDeck);
    }

    //---------------------------------GetCollection-----------------------------//
    public List<Collection> GetCollection(){
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledLogin : snapshot.getChildren()) {
                    Collection coll = pulledLogin.getValue(Collection.class);
                    collList.add(coll);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });

        return collList;
    }

    //---------------------------------SetCollection-----------------------------//
    public void SetCollection(Collection newColl) {
        collRef.push().setValue(newColl);
    }

    //---------------------------------UpdateCollection-----------------------------//
    private void updateCollection(Integer collectionID, String collectionName, Collection edtColl) {
        /* HashMap myMap = new HashMap();
        myMap.put("Collection ID", edtColl.getCollectionID());
        myMap.put("Collection Name", edtColl.getCollectionName());
        myMap.put("Goal Items", edtColl.getGoalItems());*/
        collRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledData : snapshot.getChildren())
                {
                    Collection coll = pulledData.getValue(Collection.class);
                    if (coll.getCollectionID() == collectionID)
                    {
                        String key = pulledData.getKey();
                        collRef.child(key).setValue(edtColl);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pulledData : snapshot.getChildren())
                {
                    Item item = pulledData.getValue(Item.class);
                    if(item.getCollectionName().equals(collectionName))
                    {
                        String key = pulledData.getKey();
                        itemRef.child(key).child("CollectionID").setValue(edtColl.getCollectionName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}