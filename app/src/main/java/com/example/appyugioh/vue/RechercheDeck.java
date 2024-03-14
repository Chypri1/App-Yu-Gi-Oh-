package com.example.appyugioh.vue;

import static com.example.appyugioh.R.*;

import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.google.android.material.navigation.NavigationView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.rest.AccesExterneRest;


public class RechercheDeck extends Activity {
    protected DrawerLayout drawerLayout;

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

        drawerLayout=findViewById(id.drawerLayout);
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

        boutonRechercheDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutResultatRecherche.removeAllViews();
                accesExterneRest.appRest(rechercheDeck.getText().toString(),layoutResultatRecherche,activity);

            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activity);
            }
        });

        ImageButton boutonMenuDeroulant=findViewById(R.id.menuDeroulant);
        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        // Configuration du geste de balayage pour ouvrir le tiroir de navigation
        this.drawerLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }
}
