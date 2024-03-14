package com.example.appyugioh.controlleur;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.AffichageMesCartes;
import com.example.appyugioh.vue.OnSwipeTouchListener;
import com.example.appyugioh.vue.RechercheCarte;
import com.google.android.material.navigation.NavigationView;

public class ControlleurAffichageMesCartes {


    protected AffichageMesCartes activite;

    protected ComportementMenu comportementMenu;

    protected ComportementAffichageMesCartes comportementAffichageMesCartes;

    protected AccesExterneRest accesExterneRest;

    public ControlleurAffichageMesCartes (AffichageMesCartes activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementAffichageMesCartes = new ComportementAffichageMesCartes();
        initialiseActivite();
        initialiseComportement();
        observateur();
    }


    public AffichageMesCartes getActivite() {
        return activite;
    }

    public void setActivite(AffichageMesCartes activite) {
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

    public void initialiseActivite()
    {
        activite.setDrawerLayout( activite.findViewById(R.id.drawerLayout));
        activite.setLayoutResultatRecherche(activite.findViewById(R.id.layoutResultatRecherche));
        activite.setComportementAffichageMesCartes(new ComportementAffichageMesCartes());

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
        comportementAffichageMesCartes.afficherImagesEnregistrees(activite.getLayoutResultatRecherche(), activite);
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
    }
}
