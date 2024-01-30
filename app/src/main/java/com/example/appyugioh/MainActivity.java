package com.example.appyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    Button boutonRechercheCarte;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        this.boutonRechercheCarte = findViewById(R.id.boutonRecherche);
        boutonRechercheCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recherche = new Intent(getApplicationContext(),RechercheCarte.class);
                startActivity(recherche);
                finish();
            }
        });
    }
}
