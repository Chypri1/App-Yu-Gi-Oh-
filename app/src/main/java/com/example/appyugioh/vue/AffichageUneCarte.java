package com.example.appyugioh.vue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

public class AffichageUneCarte extends Activity {

    protected DrawerLayout drawerLayout;
    protected ImageView imageViewImage;

    protected ImageButton boutonMenuDeroulant;
    protected NavigationView navigationView;
    protected ComportementMenu comportementMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageunecarte);

        this.drawerLayout = findViewById(R.id.drawerLayout);

        this.boutonMenuDeroulant = findViewById(R.id.menuDeroulant);

        imageViewImage = findViewById(R.id.imageViewImage);

        // Extraire le chemin de l'image de l'intention
        String imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            // Charger l'image depuis le chemin de l'image
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
            // Afficher l'image dans l'ImageView
            imageViewImage.setImageBitmap(imageBitmap);
        }


        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);

        this.comportementMenu = new ComportementMenu();

        final Activity activity = this;



        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return comportementMenu.initItemMenu(item,activity);
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

    }
}
