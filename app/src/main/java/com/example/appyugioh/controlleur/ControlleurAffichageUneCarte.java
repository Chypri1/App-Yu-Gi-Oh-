package com.example.appyugioh.controlleur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.appyugioh.vue.AffichageUneCarte;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.google.android.material.navigation.NavigationView;

public class ControlleurAffichageUneCarte {


    protected AffichageUneCarte activite;

    protected ComportementMenu comportementMenu;

    protected ComportementAffichageMesCartes comportementAffichageMesCartes;

    protected AccesExterneRest accesExterneRest;

    public ControlleurAffichageUneCarte (AffichageUneCarte activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementAffichageMesCartes = new ComportementAffichageMesCartes();
        initialiseActivite();
        initialiseComportement();
        observateur();
    }

    public AffichageUneCarte getActivite() {
        return activite;
    }

    public void setActivite(AffichageUneCarte activite) {
        this.activite = activite;
    }

    public ComportementMenu getComportementMenu() {
        return comportementMenu;
    }

    public void setComportementMenu(ComportementMenu comportementMenu) {
        this.comportementMenu = comportementMenu;
    }

    public ComportementAffichageMesCartes getComportementAffichageMesCartes() {
        return comportementAffichageMesCartes;
    }

    public void setComportementAffichageMesCartes(ComportementAffichageMesCartes comportementAffichageMesCartes) {
        this.comportementAffichageMesCartes = comportementAffichageMesCartes;
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
        activite.setTexteViewNomCarte(activite.findViewById(R.id.textViewNomCarte));
        activite.setImageViewImage(activite.findViewById(R.id.imageViewCarte));

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
    }

    public void observateur()
    {


        String nomCarte = activite.getIntent().getStringExtra("nomCarte");
        if(nomCarte != null)
        {
            activite.getTexteViewNomCarte().setText(nomCarte);
        }


        // Extraire le chemin de l'image de l'intention
        String imagePath = activite.getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            // Charger l'image depuis le chemin de l'image
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
            Bitmap imageBitmapResized = comportementAffichageMesCartes.resizeBitmap(imageBitmap, 3.0f);
            // Afficher l'image dans l'ImageView
            activite.getImageViewImage().setImageBitmap(imageBitmapResized);
        }

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
