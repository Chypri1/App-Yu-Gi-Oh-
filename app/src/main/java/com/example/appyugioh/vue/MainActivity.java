package com.example.appyugioh.vue;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appyugioh.R;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    protected DrawerLayout drawerLayout;
    protected Button boutonRechercheCarte;
    protected Button boutonRechercheDeck;
    protected NavigationView navigationView;
    protected ImageButton boutonMenuDeroulant;

    /*Carrousel*/


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        this.drawerLayout = findViewById(R.id.drawerLayout);

        this.boutonRechercheCarte = findViewById(R.id.boutonRechercheCarte);

        this.boutonRechercheDeck= findViewById(R.id.boutonRechercheDeck);

        this.boutonMenuDeroulant = findViewById(R.id.menuDeroulant);
        /* Carrousel */
        RecyclerView recyclerView = findViewById(R.id.recycler);


        ArrayList<String> arrayList = new ArrayList<>();

        //Add multiple images to arraylist.
        arrayList.add("android.resource://" + getPackageName() + "/drawable/yugi");
        arrayList.add("android.resource://" + getPackageName() + "/drawable/kaiba");
        arrayList.add("android.resource://" + getPackageName() + "/drawable/cardmarket");

        ImageAdapter adapter = new ImageAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LoopingLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new CarouselLayoutManager());
        adapter.attachSnapHelper(recyclerView);


        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                if (path.equals("android.resource://" + getPackageName() + "/drawable/yugi")) {
                    // Ouvrir le lien dans un navigateur Web
                    String url = "https://www.yugioh-card.com/eu/fr/";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                if (path.equals("android.resource://" + getPackageName() + "/drawable/kaiba")) {
                    // Ouvrir le lien dans un navigateur Web
                    String url = "https://www.yugioh-card.com/eu/fr/events/";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                if (path.equals("android.resource://" + getPackageName() + "/drawable/cardmarket")) {
                    // Ouvrir le lien dans un navigateur Web
                    String url = "https://www.cardmarket.com/fr/YuGiOh";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
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
                    Intent rechercheDeck = new Intent(getApplicationContext(),RechercheDeck.class);
                    startActivity(rechercheDeck);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_mes_cartes)
                {
                    Intent mesCartes = new Intent(getApplicationContext(),AffichageCarte.class);
                    startActivity(mesCartes);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_mes_decks)
                {
                    Intent mesDecks = new Intent(getApplicationContext(),AffichageDeck.class);
                    startActivity(mesDecks);
                    finish();
                }
                if (item.getItemId() == R.id.menu_bouton_enregistrer_carte)
                {
                    Intent enregsitrerCarte = new Intent(getApplicationContext(),EnregistrerCarte.class);
                    startActivity(enregsitrerCarte);
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
