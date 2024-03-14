package com.example.appyugioh.vue;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurMainActivity;
import com.example.appyugioh.modele.comportementFront.ImageAdapter;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends Activity {

    protected DrawerLayout drawerLayout;
    protected Button boutonRechercheCarte;
    protected Button boutonRechercheDeck;
    protected NavigationView navigationView;
    protected ImageButton boutonMenuDeroulant;

    /*Logo*/
    private MediaPlayer mediaPlayer;
    private ImageView logoImageView;

    protected RecyclerView recyclerView;

    protected ImageAdapter adapter;

    protected ControlleurMainActivity controlleurMainActivity;

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public Button getBoutonRechercheCarte() {
        return boutonRechercheCarte;
    }

    public void setBoutonRechercheCarte(Button boutonRechercheCarte) {
        this.boutonRechercheCarte = boutonRechercheCarte;
    }

    public Button getBoutonRechercheDeck() {
        return boutonRechercheDeck;
    }

    public void setBoutonRechercheDeck(Button boutonRechercheDeck) {
        this.boutonRechercheDeck = boutonRechercheDeck;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public ImageButton getBoutonMenuDeroulant() {
        return boutonMenuDeroulant;
    }

    public void setBoutonMenuDeroulant(ImageButton boutonMenuDeroulant) {
        this.boutonMenuDeroulant = boutonMenuDeroulant;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public ImageView getLogoImageView() {
        return logoImageView;
    }

    public void setLogoImageView(ImageView logoImageView) {
        this.logoImageView = logoImageView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ImageAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ImageAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        controlleurMainActivity = new ControlleurMainActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release(); // Libérer le lecteur
    }
}
