package com.example.appyugioh.vue;

import static com.example.appyugioh.R.*;

import com.example.appyugioh.controlleur.ControlleurRechercheCarte;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.rest.AccesExterneRest;


public class RechercheCarte extends Activity {
    protected DrawerLayout drawerLayout;

    protected EditText rechercheCarte;

    protected ImageButton boutonRechercheCarte;

    protected LinearLayout layoutResultatRecherche;

    protected Button boutonFiltre;

    protected NavigationView navigationView;

    protected AccesExterneRest accesExterneRest;
    protected Button btn_prev;
    protected Button btn_next;

    protected ControlleurRechercheCarte controlleurRechercheCarte;

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public EditText getRechercheCarte() {
        return rechercheCarte;
    }

    public void setRechercheCarte(EditText rechercheCarte) {
        this.rechercheCarte = rechercheCarte;
    }

    public ImageButton getBoutonRechercheCarte() {
        return boutonRechercheCarte;
    }

    public void setBoutonRechercheCarte(ImageButton boutonRechercheCarte) {
        this.boutonRechercheCarte = boutonRechercheCarte;
    }

    public LinearLayout getLayoutResultatRecherche() {
        return layoutResultatRecherche;
    }

    public void setLayoutResultatRecherche(LinearLayout layoutResultatRecherche) {
        this.layoutResultatRecherche = layoutResultatRecherche;
    }

    public Button getBoutonFiltre() {
        return boutonFiltre;
    }

    public void setBoutonFiltre(Button boutonFiltre) {
        this.boutonFiltre = boutonFiltre;
    }

    public AccesExterneRest getAccesExterneRest() {
        return accesExterneRest;
    }

    public void setAccesExterneRest(AccesExterneRest accesExterneRest) {
        this.accesExterneRest = accesExterneRest;
    }

    public Button getBtn_prev() {
        return btn_prev;
    }

    public void setBtn_prev(Button btn_prev) {
        this.btn_prev = btn_prev;
    }

    public Button getBtn_next() {
        return btn_next;
    }

    public void setBtn_next(Button btn_next) {
        this.btn_next = btn_next;
    }

    public ControlleurRechercheCarte getControlleurRechercheCarte() {
        return controlleurRechercheCarte;
    }

    public void setControlleurRechercheCarte(ControlleurRechercheCarte controlleurRechercheCarte) {
        this.controlleurRechercheCarte = controlleurRechercheCarte;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.recherchecarte);
        controlleurRechercheCarte = new ControlleurRechercheCarte(this);
    }
}
