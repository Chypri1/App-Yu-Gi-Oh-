package com.example.appyugioh.vue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AffichageMesCartes extends Activity {

    protected LinearLayout layoutResultatRecherche;

    protected NavigationView navigationView;

    protected ComportementMenu comportementMenu;

    protected ComportementAffichageMesCartes comportementAffichageMesCartes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagemescartes);

        this.comportementAffichageMesCartes = new ComportementAffichageMesCartes();
        layoutResultatRecherche = findViewById(R.id.layoutResultatRecherche);
        comportementAffichageMesCartes.afficherImagesEnregistrees(layoutResultatRecherche, this);



        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        this.comportementMenu = new ComportementMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);

        final Activity elem1 = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, elem1);
            }
        });
    }
}
