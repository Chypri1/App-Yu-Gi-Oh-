package com.example.appyugioh.controlleur;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementEnregistrementCarte;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.EnregistrerCarte;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

public class ControlleurEnregistrerCarte {


    protected EnregistrerCarte activite;

    protected ComportementMenu comportementMenu;

    protected ComportementEnregistrementCarte comportementEnregistrementCarte;

    protected AccesExterneRest accesExterneRest;

    public ControlleurEnregistrerCarte (EnregistrerCarte activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementEnregistrementCarte = new ComportementEnregistrementCarte();
        initialiseActivite();
        initialiseComportement();
        observateur();
    }

    public EnregistrerCarte getActivite() {
        return activite;
    }

    public void setActivite(EnregistrerCarte activite) {
        this.activite = activite;
    }

    public ComportementMenu getComportementMenu() {
        return comportementMenu;
    }

    public void setComportementMenu(ComportementMenu comportementMenu) {
        this.comportementMenu = comportementMenu;
    }

    public ComportementEnregistrementCarte getComportementEnregistrementCarte() {
        return comportementEnregistrementCarte;
    }

    public void setComportementEnregistrementCarte(ComportementEnregistrementCarte comportementEnregistrementCarte) {
        this.comportementEnregistrementCarte = comportementEnregistrementCarte;
    }

    public AccesExterneRest getAccesExterneRest() {
        return accesExterneRest;
    }

    public void setAccesExterneRest(AccesExterneRest accesExterneRest) {
        this.accesExterneRest = accesExterneRest;
    }

    public void initialiseActivite()
    {
        activite.setDrawerLayout(activite.findViewById(R.id.drawerLayout));

        activite.setBoutonMenuDeroulant(activite.findViewById(R.id.menuDeroulant));

        activite.setNomCarte(activite.findViewById(R.id.nomCarte));

        activite.setNomEdition(activite.findViewById(R.id.nomEdition));

        activite.setLayoutResultatCam(activite.findViewById(R.id.layoutResultatCam));

        activite.setImageCam(activite.findViewById(R.id.imageCam));

        activite.setBoutonAccesCamera( activite.findViewById(R.id.boutonAccesCamera));

        activite.setBoutonEnregistrementCarte(activite.findViewById(R.id.boutonEnregistrementCarte));

        activite.setNavigationView(activite.findViewById(R.id.nav_view));

        Menu menu = activite.getNavigationView().getMenu();

        this.comportementMenu = new ComportementMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);


    }


    public void initialiseComportement()
    {
    }

    public void observateur()
    {




        activite.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activite);
            }
        });

        activite.getBoutonAccesCamera().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comportementEnregistrementCarte.demanderPermissionCamera(activite);
            }
        });

        activite.getBoutonEnregistrementCarte().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    comportementEnregistrementCarte.enregistrementCarte(activite.getNomCarte(), activite.getNomEdition(), activite.getImageCam(),activite);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        activite.getBoutonMenuDeroulant().setOnClickListener(new View.OnClickListener() {
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

    public void retourPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == activite.getREQUEST_CAMERA_PERMISSION()) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                comportementEnregistrementCarte.ouvrirCamera(activite);
            } else {
                // La permission a été refusée. Vous pouvez afficher un message à l'utilisateur.
                Toast.makeText(activite, "Permission de la caméra refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
