package com.example.appyugioh;

import static com.example.appyugioh.R.*;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class RechercheCarte extends AppCompatActivity {

    ProgressBar chargement;

    ImageView image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.recherchecarte);

        chargement = findViewById(id.progressBar);

        this.image1 = findViewById(id.imageView);
        image1.setVisibility(View.INVISIBLE);
    }
}
