package com.example.appyugioh.controlleur;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.appyugioh.vue.AffichageUneCarte;
import com.example.appyugioh.vue.MainActivity;
import com.example.appyugioh.vue.RechercheCarte;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;

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
        activite.setTexteTitreDeck(activite.findViewById(R.id.texteTitreDeck));
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
        this.deck = (Deck) activite.getIntent().getSerializableExtra("deck");
        activite.getTexteTitreDeck().setText(deck.getNom());
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
        activite.getScrollView().setOnTouchListener(new OnSwipeTouchListener(activite) {
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

        int i=0;
        int buttonsPerRow=3;
        LinearLayout rowLayout = null;
        for(CarteYuGiOh carteYuGiOh:deck.getListeCarteYuGiOh())
        {
            if(carteYuGiOh!=null) {
                // Créer un bouton d'image pour afficher la carte
                ImageButton cardInfo = new ImageButton(activite);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f / buttonsPerRow); // 1.0f divisé par le nombre de boutons par ligne
                params.weight = 1; // Utiliser le poids pour équilibrer la largeur des boutons
                cardInfo.setLayoutParams(params);
                cardInfo.setPadding(0, 0, 0, 0); // Ajuster la marge à zéro
                cardInfo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                cardInfo.setAdjustViewBounds(true);
                Picasso.get().load(carteYuGiOh.getLienImage()).into(cardInfo);
                // Extraire le chemin de l'image de l'intention
                String imagePath;
                if (!carteYuGiOh.getLienImage().contains("https")) {
                    activite.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get().load(new File(carteYuGiOh.getLienImage())).into(cardInfo);
                        }
                    });
                } else {
                    activite.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get().load(carteYuGiOh.getLienImage()).into(cardInfo);
                        }
                    });
                }

                // Ajouter le bouton d'image au LinearLayout de la rangée actuelle
                if (i % buttonsPerRow == 0) {
                    // Créer un nouveau LinearLayout pour une nouvelle rangée
                    rowLayout = new LinearLayout(activite);
                    rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                    activite.getLayoutResultatRecherche().addView(rowLayout);
                }
                i++;
                // Ajouter le bouton d'image au LinearLayout de la rangée actuelle
                if (rowLayout != null) {
                    rowLayout.addView(cardInfo);
                }
                cardInfo.setOnClickListener(v -> {
                    Intent affichageUneCarte = new Intent(activite.getApplicationContext(), AffichageUneCarte.class);
                    affichageUneCarte.putExtra("carteYuGiOh", carteYuGiOh);
                    activite.startActivity(affichageUneCarte);
                });
            }
        }
    }
}
