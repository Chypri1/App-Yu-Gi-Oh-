package com.example.appyugioh.controlleur;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesDecks;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageUnDeck;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.metier.Deck;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.AffichageUnDeck;
import com.example.appyugioh.vue.RechercheCarte;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class ControlleurAffichageUnDeck {

    protected AffichageUnDeck activite;

    protected Deck deck;

    protected AccesExterneRest accesExterneRest;
    protected ComportementMenu comportementMenu;

    protected ComportementAffichageUnDeck comportementAffichageUnDeck;
    public ControlleurAffichageUnDeck (AffichageUnDeck activity){
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementAffichageUnDeck = new ComportementAffichageUnDeck(accesExterneRest);
        initialiseActivite();
        initialiseComportement();
        observateur();
    }

    public void initialiseActivite()
    {
        activite.setDrawerLayout(activite.findViewById(R.id.drawerLayout));
        activite.setLayoutResultatRecherche(activite.findViewById(R.id.layoutResultatRecherche));
        activite.setBoutonAjoutUneCarte(activite.findViewById(R.id.boutonAjoutUneCarte));
        activite.setNavigationView(activite.findViewById(R.id.nav_view));
        Menu menu = activite.getNavigationView().getMenu();
        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
    }
    public void initialiseComportement()
    {
        this.deck = (Deck) activite.getIntent().getSerializableExtra("deck");
    }
    public void observateur()
    {

        activite.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activite);
            }
        });

        ImageButton boutonMenuDeroulant=activite.findViewById(R.id.menuDeroulant);
        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite.getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });
        // Configuration du geste de balayage pour ouvrir le tiroir de navigation
        activite.getDrawerLayout().setOnTouchListener(new OnSwipeTouchListener(activite) {
            @Override
            public void onSwipeRight() {
                if (!activite.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
                    activite.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });

        activite.getBoutonAjoutUneCarte().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheCarte = new Intent(activite.getApplicationContext(), RechercheCarte.class);
                activite.startActivity(rechercheCarte);
                activite.finish();
            }
        });

        for(CarteYuGiOh carte:deck.getListeCarteYuGiOh())
        {
            ImageButton imageButton = new ImageButton(activite);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            imageButton.setLayoutParams(layoutParams);
            Picasso.get().load(carte.getLienImage()).into(imageButton);
            activite.getLayoutResultatRecherche().addView(imageButton);
        }
    }
}
