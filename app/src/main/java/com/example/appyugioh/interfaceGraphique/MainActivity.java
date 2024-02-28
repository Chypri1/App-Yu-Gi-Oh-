package com.example.appyugioh.interfacegraphique;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.appyugioh.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends Activity {

    protected DrawerLayout drawerLayout;
    protected Button boutonRechercheCarte;
    protected Button boutonRechercheDeck;
    protected NavigationView navigationView;

    protected ImageButton boutonMenuDeroulant;

    /*Carrousel*/
    private ViewPager mViewPager;
    private ImagePagerAdapter mAdapter;

    private int[] mImages = {R.drawable.dragon_blanc_aux_yeux_bleus, R.drawable.dragon_zombie_aux_yeux_rouges, R.drawable.yugioh_card_back};


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        /*création des boutons */
        this.drawerLayout = findViewById(R.id.drawerLayout);

        this.boutonRechercheCarte = findViewById(R.id.boutonRechercheCarte);

        this.boutonRechercheDeck = findViewById(R.id.boutonRechercheDeck);

        this.boutonMenuDeroulant = findViewById(R.id.menuDeroulant);

        /* Carrousel */
        mViewPager = findViewById(R.id.viewPager);
        mAdapter = new ImagePagerAdapter(this, mImages);
        mViewPager.setAdapter(mAdapter);

        /* créer une classe pour le menu en lui même */
        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);



        boutonRechercheCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheCarte = new Intent(getApplicationContext(),RechercheCarte.class);
                startActivity(rechercheCarte);
                finish();
            }
        });


        boutonRechercheDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheDeck = new Intent(getApplicationContext(),RechercheDeck.class);
                startActivity(rechercheDeck);
                finish();
            }
        });

        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.menu_bouton_accueil)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_recherche_carte)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),RechercheCarte.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_recherche_deck)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),RechercheDeck.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_mes_cartes)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),AffichageCarte.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_mes_decks)
                {
                    Intent rechercheCarte = new Intent(getApplicationContext(),AffichageDeck.class);
                    startActivity(rechercheCarte);
                    finish();
                }
                return true;
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
    }


}
