package com.example.appyugioh.vue;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends Activity {

    protected DrawerLayout drawerLayout;
    protected Button boutonRechercheCarte;
    protected Button boutonRechercheDeck;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        this.drawerLayout = findViewById(R.id.drawerLayout);

        this.boutonRechercheCarte = findViewById(R.id.boutonRechercheCarte);

        this.boutonRechercheDeck= findViewById(R.id.boutonRechercheDeck);


        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);



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


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.menu_bouton_accueil)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_recherche_carte)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),RechercheCarte.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_recherche_deck)
                {
                    Intent rechercheDeck = new Intent(getApplicationContext(),RechercheDeck.class);
                    startActivity(rechercheDeck);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_mes_cartes)
                {
                    Intent mesCartes = new Intent(getApplicationContext(),AffichageCarte.class);
                    startActivity(mesCartes);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_mes_decks)
                {
                    Intent mesDecks = new Intent(getApplicationContext(),AffichageDeck.class);
                    startActivity(mesDecks);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_enregistrer_carte)
                {
                    Intent enregsitrerCarte = new Intent(getApplicationContext(),EnregistrerCarte.class);
                    startActivity(enregsitrerCarte);
                    finish();
                }
                return true;
            }
        });
    }
}
