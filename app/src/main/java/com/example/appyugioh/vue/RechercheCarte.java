package com.example.appyugioh.vue;

import static com.example.appyugioh.R.*;

import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
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


public class RechercheCarte extends Activity {
    protected DrawerLayout drawerLayout;

    protected EditText rechercheCarte;

    protected ImageButton boutonRechercheCarte;

    protected LinearLayout layoutResultatRecherche;

    protected Button boutonFiltre;

    protected AccesExterneRest accesExterneRest;
    protected Button btn_prev;
    protected Button btn_next;

    protected ComportementMenu comportementMenu;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.recherchecarte);

        drawerLayout=findViewById(id.drawerLayout);
        boutonRechercheCarte = findViewById(id.boutonRechercheCarte);
        rechercheCarte = findViewById(R.id.rechercheCarte);
        layoutResultatRecherche = findViewById(R.id.layoutResultatRecherche);
        boutonFiltre = findViewById(id.boutonFiltre);
        btn_next=findViewById(id.nextButton);
        btn_prev=findViewById(id.previousButton);
        accesExterneRest = new AccesExterneRest(btn_prev,btn_next,findViewById(id.scrollViewRecherche));

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

        boutonRechercheCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutResultatRecherche.removeAllViews();
                accesExterneRest.appRest(rechercheCarte.getText().toString(), layoutResultatRecherche,activity);
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

        rechercheCarte.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Vérifiez si l'EditText a le focus
                if (hasFocus) {
                    // Si oui, effacez le texte par défaut
                    rechercheCarte.getText().clear();
                } else {
                    // Si non, réinitialisez le texte par défaut si le champ est vide
                    if (rechercheCarte.getText().toString().isEmpty()) {
                        rechercheCarte.setText("nom Carte");
                    }
                }
            }
        });
    }
}
