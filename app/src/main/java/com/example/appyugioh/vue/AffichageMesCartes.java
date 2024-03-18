package com.example.appyugioh.vue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

public class AffichageMesCartes extends Activity {

    protected DrawerLayout drawerLayout;
    protected LinearLayout layoutResultatRecherche;

    protected NavigationView navigationView;

    protected EditText rechercheCarte;

    protected ComportementAffichageMesCartes comportementAffichageMesCartes;


    protected ControlleurAffichageMesCartes controlleurAffichageMesCartes;

    public EditText getRechercheCarte() {
        return rechercheCarte;
    }

    public void setRechercheCarte(EditText rechercheCarte) {
        this.rechercheCarte = rechercheCarte;
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

    public ComportementAffichageMesCartes getComportementAffichageMesCartes() {
        return comportementAffichageMesCartes;
    }

    public void setComportementAffichageMesCartes(ComportementAffichageMesCartes comportementAffichageMesCartes) {
        this.comportementAffichageMesCartes = comportementAffichageMesCartes;
    }


    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagemescartes);
        this.controlleurAffichageMesCartes = new ControlleurAffichageMesCartes(this);
    }
}
