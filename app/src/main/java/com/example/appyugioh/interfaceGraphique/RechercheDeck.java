package com.example.appyugioh.interfacegraphique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyugioh.R;
import com.example.appyugioh.metier.CarteYuGiOh;
import com.example.appyugioh.rest.AccesExterneRest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RechercheDeck extends AppCompatActivity {

    EditText rechercheDeck;

    LinearLayout layoutResultatRecherche;

    HorizontalScrollView listeFiltre;

    LinearLayout layoutFiltre;

    RecyclerView filtrePrix;

    RecyclerView triPrix;
    AccesExterneRest accesExterneRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherchedeck);

        rechercheDeck = findViewById(R.id.rechercheDeck);
        layoutResultatRecherche = findViewById(R.id.layoutResultatRecherche);
        listeFiltre = findViewById(R.id.listeFiltre);
        layoutFiltre = findViewById(R.id.layoutFiltre);
        filtrePrix = findViewById(R.id.filtrePrix);
        triPrix = findViewById(R.id.triPrix);

        // appel Rest
        // TODO: a changer en fonction du texteEdit recherheDeck
        final List<CarteYuGiOh> resultat = accesExterneRest.appeRest("Magicien Sombre");

        // intégration dans l'interface
        TextView cardInfoTextView = new TextView(this);
        for (CarteYuGiOh carteYuGiOh:resultat)
        {
            cardInfoTextView.setText(carteYuGiOh.toString());
        }

    }




}
