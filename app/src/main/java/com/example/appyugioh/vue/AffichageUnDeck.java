package com.example.appyugioh.vue;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurAffichageMesCartes;
import com.example.appyugioh.controlleur.ControlleurAffichageMesDecks;
import com.example.appyugioh.controlleur.ControlleurAffichageUnDeck;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

public class AffichageUnDeck extends Activity {

    protected DrawerLayout drawerLayout;
    protected LinearLayout layoutResultatRecherche;
    protected NavigationView navigationView;
    protected ControlleurAffichageUnDeck controlleurAffichageUnDeck;

    protected TextView texteTitreDeck;

    protected Button boutonAjoutUneCarte;

    protected NestedScrollView scrollView;

    public NestedScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(NestedScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public TextView getTexteTitreDeck() {
        return texteTitreDeck;
    }

    public void setTexteTitreDeck(TextView texteTitreDeck) {
        this.texteTitreDeck = texteTitreDeck;
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

    public ControlleurAffichageUnDeck getControlleurAffichageUnDeck() {
        return controlleurAffichageUnDeck;
    }

    public void setControlleurAffichageUnDeck(ControlleurAffichageUnDeck controlleurAffichageUnDeck) {
        this.controlleurAffichageUnDeck = controlleurAffichageUnDeck;
    }

    public Button getBoutonAjoutUneCarte() {
        return boutonAjoutUneCarte;
    }

    public void setBoutonAjoutUneCarte(Button boutonAjoutUneCarte) {
        this.boutonAjoutUneCarte = boutonAjoutUneCarte;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageundeck);
        this.controlleurAffichageUnDeck = new ControlleurAffichageUnDeck(this);
    }


}
