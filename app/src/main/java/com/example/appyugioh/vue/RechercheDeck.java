package com.example.appyugioh.vue;

import static com.example.appyugioh.R.*;

import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.rest.AccesExterneRest;

import java.util.ArrayList;
import java.util.List;


public class RechercheDeck extends Activity {

    protected EditText rechercheDeck;

    protected ImageButton boutonRechercheDeck;

    protected LinearLayout layoutResultatRecherche;

    protected Button boutonFiltre;

    protected AccesExterneRest accesExterneRest;

    protected ComportementMenu comportementMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.recherchedeck);

        boutonRechercheDeck = findViewById(id.boutonRechercheDeck);
        rechercheDeck = findViewById(R.id.rechercheDeck);
        layoutResultatRecherche = findViewById(R.id.layoutResultatRecherche);
        boutonFiltre = findViewById(id.boutonFiltre);
        accesExterneRest = new AccesExterneRest();

        /* créer une classe pour le menu en lui même */
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(id.menu_bouton_mes_decks);

        this.comportementMenu = new ComportementMenu();

        final Activity activity = this;

        ImageButton cardInfoImageButton = new ImageButton(this);
        boutonRechercheDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CarteYuGiOh> resultat = new ArrayList<>();
                resultat = accesExterneRest.appeRest(rechercheDeck.getText().toString());

                // intégration dans l'interface
                for (CarteYuGiOh carteYuGiOh:resultat)
                {
                    Picasso.get().load(carteYuGiOh.getLienImage()).into(cardInfoImageButton);

                }

            }
        });
        layoutResultatRecherche.addView(cardInfoImageButton);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activity);
            }
        });
    }
}
