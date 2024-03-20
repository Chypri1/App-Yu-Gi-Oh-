package com.example.appyugioh.vue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurAffichageMesCartes;
import com.example.appyugioh.controlleur.ControlleurAffichageMesDecks;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.google.android.material.navigation.NavigationView;

public class AffichageMesDecks extends Activity {


    protected DrawerLayout drawerLayout;

    protected LinearLayout layoutResultatRecherche;

    protected NavigationView navigationView;

    protected EditText rechercheDeck;

    protected Button BoutonNouveauDeck;

    protected ControlleurAffichageMesDecks controlleurAffichageMesDecks;

    protected NestedScrollView scrollView;

    public NestedScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(NestedScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public EditText getRechercheDeck() {
        return rechercheDeck;
    }

    public void setRechercheDeck(EditText rechercheDeck) {
        this.rechercheDeck = rechercheDeck;
    }

    public Button getBoutonNouveauDeck() {
        return BoutonNouveauDeck;
    }

    public void setBoutonNouveauDeck(Button boutonNouveauDeck) {
        BoutonNouveauDeck = boutonNouveauDeck;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public LinearLayout getLayoutResultatRecherche() {
        return layoutResultatRecherche;
    }

    public void setLayoutResultatRecherche(LinearLayout layoutResultatRecherche) {
        this.layoutResultatRecherche = layoutResultatRecherche;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public ControlleurAffichageMesDecks getControlleurAffichageMesDecks() {
        return controlleurAffichageMesDecks;
    }

    public void setControlleurAffichageMesDecks(ControlleurAffichageMesDecks controlleurAffichageMesDecks) {
        this.controlleurAffichageMesDecks = controlleurAffichageMesDecks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagemesdecks);
        this.controlleurAffichageMesDecks = new ControlleurAffichageMesDecks(this);
    }

}
