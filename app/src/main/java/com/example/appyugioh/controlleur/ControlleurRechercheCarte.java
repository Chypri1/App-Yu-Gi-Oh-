package com.example.appyugioh.controlleur;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.OnSwipeTouchListener;
import com.example.appyugioh.vue.RechercheCarte;
import com.google.android.material.navigation.NavigationView;

public class ControlleurRechercheCarte {

    RechercheCarte activite;

    public RechercheCarte getActivite() {
        return activite;
    }

    public void setActivite(RechercheCarte activite) {
        this.activite = activite;
    }

    public ComportementMenu getComportementMenu() {
        return comportementMenu;
    }

    public void setComportementMenu(ComportementMenu comportementMenu) {
        this.comportementMenu = comportementMenu;
    }

    public AccesExterneRest getAccesExterneRest() {
        return accesExterneRest;
    }

    public void setAccesExterneRest(AccesExterneRest accesExterneRest) {
        this.accesExterneRest = accesExterneRest;
    }

    ComportementMenu comportementMenu;

    protected AccesExterneRest accesExterneRest;

    public ControlleurRechercheCarte (RechercheCarte activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        initialiseActivite();
        initialiseComportement();
        observateur();
    }

    public void initialiseActivite()
    {
        activite.setDrawerLayout( activite.findViewById(R.id.drawerLayout));
        activite.setBoutonRechercheCarte(  activite.findViewById(R.id.boutonRechercheCarte));
        activite.setRechercheCarte( activite.findViewById(R.id.rechercheCarte));
        activite.setLayoutResultatRecherche(activite.findViewById(R.id.layoutResultatRecherche));
        activite.setBoutonFiltre(activite.findViewById(R.id.boutonFiltre));

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
        this.comportementMenu = new ComportementMenu();
        this.accesExterneRest = new AccesExterneRest();
    }

    public void observateur()
    {


        activite.getBoutonRechercheCarte().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite.getLayoutResultatRecherche().removeAllViews();
                accesExterneRest.appRest(activite.getRechercheCarte().getText().toString(), activite.getLayoutResultatRecherche(),activite);
            }
        });

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
    }
}