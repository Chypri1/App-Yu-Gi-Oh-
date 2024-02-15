package com.example.appyugioh.interfacegraphique;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.rest.AccesExterneRest;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected Button boutonRechercheCarte;

    protected Button boutonRechercheDeck;

    protected Button menuBoutonRechercheCarte;

    protected NavigationView drawer;

    protected ImageView imageView;

    AccesExterneRest accesExterneRest;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        this.drawerLayout = findViewById(R.id.drawerLayout);

        this.boutonRechercheCarte = findViewById(R.id.boutonRechercheCarte);

        this.boutonRechercheDeck= findViewById(R.id.boutonRechercheDeck);

        /* créer une classe pour le menu en lui même */
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_item2);

        this.imageView = findViewById(R.id.imageView);

        drawer = findViewById(R.id.nav_view);

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
                int variableItem = item.getItemId();

                switch (variableItem) {
                    case R.id.menu_bouton_recherche_carte:
                        Intent rechercheDeck = new Intent(getApplicationContext(),RechercheDeck.class);
                        startActivity(rechercheDeck);
                        finish();
                        return true;
                    case R.id.menu_item2:
                        // Actions à effectuer lorsque l'élément de menu 2 est sélectionné
                        return true;
                    // Ajoutez d'autres cas pour d'autres éléments de menu si nécessaire
                    default:
                        return false;
                }
            }
        });


    }
}
