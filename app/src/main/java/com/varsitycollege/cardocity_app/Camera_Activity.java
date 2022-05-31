package com.varsitycollege.cardocity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Camera_Activity extends AppCompatActivity {

    private FloatingActionButton fabButton;
    private ImageView camImage;
    private static final int requestImageCapture = 0;
    private static final int requestImageCapPer = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        fabButton = findViewById(R.id.photoFab);
        camImage = findViewById(R.id.camImage);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}