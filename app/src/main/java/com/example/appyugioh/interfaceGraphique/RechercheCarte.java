package com.example.appyugioh.interfacegraphique;

import static com.example.appyugioh.R.*;

import com.squareup.picasso.Picasso;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyugioh.R;
import com.example.appyugioh.metier.CarteYuGiOh;
import com.example.appyugioh.rest.AccesExterneRest;

import java.util.ArrayList;
import java.util.List;


public class RechercheCarte extends AppCompatActivity {

    EditText rechercheCarte;

    Button boutonRechercheCarte;

    LinearLayout layoutResultatRecherche;

    HorizontalScrollView listeFiltre;

    LinearLayout layoutFiltre;

    RecyclerView filtrePrix;

    RecyclerView triPrix;
    AccesExterneRest accesExterneRest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.recherchecarte);

        boutonRechercheCarte = findViewById(id.boutonRechercheCarte);
        rechercheCarte = findViewById(R.id.rechercheCarte);
        layoutResultatRecherche = findViewById(R.id.layoutResultatRecherche);
        listeFiltre = findViewById(R.id.listeFiltre);
        layoutFiltre = findViewById(R.id.layoutFiltre);
        filtrePrix = findViewById(R.id.filtrePrix);
        triPrix = findViewById(R.id.triPrix);
        accesExterneRest = new AccesExterneRest();

        ImageButton cardInfoImageButton = new ImageButton(this);
        boutonRechercheCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CarteYuGiOh> resultat = new ArrayList<>();
                // TODO: a changer en fonction du texteEdit recherheCarte
                resultat = accesExterneRest.appeRest("Magicien Sombre");

                // intégration dans l'interface
                for (CarteYuGiOh carteYuGiOh:resultat)
                {
                    Picasso.get().load(carteYuGiOh.getLienImage()).into(cardInfoImageButton);

                }

            }
        });
        layoutResultatRecherche.addView(cardInfoImageButton);

    }
}
