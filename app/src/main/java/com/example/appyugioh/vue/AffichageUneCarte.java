package com.example.appyugioh.vue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurAffichageUneCarte;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

public class AffichageUneCarte extends Activity {
    protected DrawerLayout drawerLayout;
    protected ScrollView scrollView;
    protected ImageButton boutonMenuDeroulant;
    protected TextView texteViewNomCarte;
    protected ImageView imageViewImage;

    protected NavigationView navigationView;

    protected ControlleurAffichageUneCarte controlleurAffichageUneCarte;

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public ImageButton getBoutonMenuDeroulant() {
        return boutonMenuDeroulant;
    }

    public void setBoutonMenuDeroulant(ImageButton boutonMenuDeroulant) {
        this.boutonMenuDeroulant = boutonMenuDeroulant;
    }

    public TextView getTexteViewNomCarte() {
        return texteViewNomCarte;
    }

    public void setTexteViewNomCarte(TextView texteViewNomCarte) {
        this.texteViewNomCarte = texteViewNomCarte;
    }

    public ImageView getImageViewImage() {
        return imageViewImage;
    }

    public void setImageViewImage(ImageView imageViewImage) {
        this.imageViewImage = imageViewImage;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageunecarte);

        comportementMenu = new ComportementMenu();
        comportementAffichageMesCartes = new ComportementAffichageMesCartes();
        this.drawerLayout = findViewById(R.id.drawerLayout);
        scrollView= findViewById(R.id.scrollView);
        boutonMenuDeroulant = findViewById(R.id.menuDeroulant);

        texteViewNomCarte = findViewById(R.id.textViewNomCarte);
        String nomCarte = getIntent().getStringExtra("nomCarte");
        if(nomCarte != null)
        {
            texteViewNomCarte.setText(nomCarte);
        }


        imageViewImage = findViewById(R.id.imageViewCarte);
        // Extraire le chemin de l'image de l'intention
        String imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            // Charger l'image depuis le chemin de l'image
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
            Bitmap imageBitmapResized = comportementAffichageMesCartes.resizeBitmap(imageBitmap, 3.0f);
            // Afficher l'image dans l'ImageView
            imageViewImage.setImageBitmap(imageBitmapResized);
        }


        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



        this.scrollView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);

                }
            }
        });

        this.drawerLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);

                }
            }
        });

        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);


        final Activity activity = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return comportementMenu.initItemMenu(item,activity);
            }
        });

    }
}
