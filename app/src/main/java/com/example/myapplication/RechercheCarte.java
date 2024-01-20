package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RechercheCarte extends AppCompatActivity {

    EditText nomCarte;
    LinearLayout tableauResultat;

    Button boutonLancementRecherche;

    // TODO: ajouter bouton pour l'appareil photo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_carte);

        this.nomCarte = (EditText) findViewById(R.id.nomCarte);
        this.tableauResultat = findViewById(R.id.tableauResultat);
        this.boutonLancementRecherche = findViewById(R.id.boutonEnvoiRecherche);
        tableauResultat.setVisibility(View.INVISIBLE);

        boutonLancementRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableauResultat.setVisibility(View.VISIBLE);
                if(!nomCarte.toString().isEmpty())
                {
                    // TODO: recherche JSON ou BDD
                    tableauResultat.addView(new View.);
                    );

                }
            }
        });

    }
}