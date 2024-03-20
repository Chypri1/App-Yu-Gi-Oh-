package com.example.appyugioh.controlleur;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesDecks;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.example.appyugioh.modele.metier.Deck;
import com.example.appyugioh.vue.AffichageMesDecks;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ControlleurAffichageMesDecks {

    protected AffichageMesDecks activite;

    protected ComportementMenu comportementMenu;

    protected ComportementAffichageMesDecks comportementAffichageMesDecks;
    public ControlleurAffichageMesDecks (AffichageMesDecks activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementAffichageMesDecks = new ComportementAffichageMesDecks();
        initialiseActivite();
        initialiseComportement();
        observateur();
    }

    public void initialiseActivite()
    {
        activite.setDrawerLayout( activite.findViewById(R.id.drawerLayout));
        activite.setLayoutResultatRecherche(activite.findViewById(R.id.layoutResultatRecherche));
        activite.setBoutonNouveauDeck(activite.findViewById(R.id.boutonNouveauDeck));
        activite.setRechercheDeck(activite.findViewById(R.id.rechercheDeck));
        activite.setNavigationView(activite.findViewById(R.id.nav_view));
        activite.setScrollView(activite.findViewById(R.id.scrollViewRecherche));
        Menu menu = activite.getNavigationView().getMenu();
        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);

    }
    public void initialiseComportement()
    {

    }
    public void observateur()
    {

        activite.getRechercheDeck().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Vérifiez si l'EditText a le focus
                if (hasFocus) {
                    // Si oui, effacez le texte par défaut
                    activite.getRechercheDeck().getText().clear();
                } else {
                    // Si non, réinitialisez le texte par défaut si le champ est vide
                    if (activite.getRechercheDeck().getText().toString().isEmpty()) {
                        activite.getRechercheDeck().setText("nom Deck");
                    }
                }
            }
        });


        activite.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activite);
            }
        });

        activite.getBoutonNouveauDeck().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comportementAffichageMesDecks.afficherPopupNouveauDeck(activite,activite.getLayoutResultatRecherche());
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
        activite.getScrollView().setOnTouchListener(new OnSwipeTouchListener(activite) {
            @Override
            public void onSwipeRight() {
                if (!activite.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
                    activite.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });

        comportementAffichageMesDecks.afficherDecks(activite.findViewById(R.id.layoutResultatRecherche),activite);
    }


}

