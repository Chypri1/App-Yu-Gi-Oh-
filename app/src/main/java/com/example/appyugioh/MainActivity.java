package com.example.appyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    protected Button boutonRechercheCarte;

    protected Button boutonRechercheDeck;

    protected ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        this.boutonRechercheCarte = findViewById(R.id.boutonRechercheCarte);

        this.boutonRechercheDeck= findViewById(R.id.boutonRechercheDeck);

        this.imageView = findViewById(R.id.imageView);
        boutonRechercheCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheCarte = new Intent(getApplicationContext(),RechercheCarte.class);
                startActivity(rechercheCarte);
                finish();
            }
        });


        boutonRechercheDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheDeck = new Intent(getApplicationContext(),RechercheDeck.class);
                startActivity(rechercheDeck);
                finish();
            }
        });


    }
}
