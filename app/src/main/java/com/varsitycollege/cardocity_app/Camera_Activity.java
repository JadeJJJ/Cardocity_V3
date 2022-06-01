package com.varsitycollege.cardocity_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//Reference STManual StartSucks Activity
public class Camera_Activity extends AppCompatActivity {
    public Class previousAct; //Stores previous activity
    private FloatingActionButton fabButton;
    public ImageView camImage;
    private Button storePhoto;
    private static final int requestImageCapture = 0;
    private static final int requestImageCapPer = 100;

    public ImageView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        fabButton = findViewById(R.id.photoFab);
        camImage = findViewById(R.id.camImage);
        storePhoto = findViewById(R.id.btnStorePhoto);


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Camera_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    final String[] permissions = {Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(Camera_Activity.this, permissions, requestImageCapPer);
                }
                else
                {
                    takePhoto();
                }
            }
        });

        storePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Camera_Activity.this, Add_Item.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestImageCapture && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            camImage.setImageBitmap(bitmap);
        }
    }

    private void takePhoto()
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, requestImageCapture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestImageCapPer && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            takePhoto();
        }
    }
}