package com.example.appyugioh.controlleur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementMainActivity;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.ImageAdapter;
import com.example.appyugioh.vue.MainActivity;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.example.appyugioh.vue.RechercheCarte;
import com.example.appyugioh.vue.RechercheDeck;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ControlleurMainActivity {
    protected MainActivity activite;
    protected ComportementMenu comportementMenu;
    protected ComportementMainActivity comportementMainActivity;

    public ControlleurMainActivity(MainActivity activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementMainActivity = new ComportementMainActivity();
        initialiseActivite();
        observateur();
    }

    public void initialiseActivite()
    {
        activite.setDrawerLayout(activite.findViewById(R.id.drawerLayout));

        activite.setBoutonMenuDeroulant(activite.findViewById(R.id.menuDeroulant));

        activite.setBoutonRechercheCarte(activite.findViewById(R.id.boutonRechercheCarte));

        activite.setBoutonRechercheDeck(activite.findViewById(R.id.boutonRechercheDeck));

        activite.setRecyclerView(activite.findViewById(R.id.recycler));

        activite.setMediaPlayer(MediaPlayer.create(activite,R.raw.yugiohtransition));

        activite.setLogoImageView(activite.findViewById(R.id.logo));

        ArrayList<String> arrayList = new ArrayList<>();
        //Add multiple images to arraylist.
        arrayList.add("android.resource://" + activite.getPackageName() + "/drawable/yugi");
        arrayList.add("android.resource://" + activite.getPackageName() + "/drawable/kaiba");
        arrayList.add("android.resource://" + activite.getPackageName() + "/drawable/cardmarket");

        activite.setAdapter(new ImageAdapter(activite, arrayList));
        activite.getRecyclerView().setAdapter(activite.getAdapter());
        activite.getRecyclerView().setLayoutManager(new CarouselLayoutManager());
        activite.getAdapter().attachSnapHelper(activite.getRecyclerView());

        activite.setNavigationView(activite.findViewById(R.id.nav_view));
        Menu menu = activite.getNavigationView().getMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void observateur()
    {

        activite.getAdapter().setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                if (path.equals("android.resource://" + activite.getPackageName() + "/drawable/yugi")) {
                    // Ouvrir le lien dans un navigateur Web
                    String url = "https://www.yugioh-card.com/eu/fr/";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    activite.startActivity(intent);
                }
                if (path.equals("android.resource://" + activite.getPackageName() + "/drawable/kaiba")) {
                    // Ouvrir le lien dans un navigateur Web
                    String url = "https://www.yugioh-card.com/eu/fr/events/";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    activite.startActivity(intent);
                }
                if (path.equals("android.resource://" + activite.getPackageName() + "/drawable/cardmarket")) {
                    // Ouvrir le lien dans un navigateur Web
                    String url = "https://www.cardmarket.com/fr/YuGiOh";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    activite.startActivity(intent);
                }
            }
        });


        activite.getBoutonRechercheCarte().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheCarte = new Intent(activite.getApplicationContext(), RechercheCarte.class);
                activite.startActivity(rechercheCarte);
                activite.finish();
            }
        });


        activite.getBoutonRechercheDeck().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercheDeck = new Intent(activite.getApplicationContext(), RechercheDeck.class);
                activite.startActivity(rechercheDeck);
                activite.finish();
            }
        });

        activite.getBoutonMenuDeroulant().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite.getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });

        activite.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return comportementMenu.initItemMenu(item,activite);
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

        // Ajout d'un OnClickListener au logo
        activite.getLogoImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comportementMainActivity.toggleMusic(activite.getMediaPlayer()); // Appeler la méthode pour démarrer ou mettre en pause la musique
            }
        });
    }
}
