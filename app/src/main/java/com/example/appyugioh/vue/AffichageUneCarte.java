package com.example.appyugioh.vue;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageunecarte);

        controlleurAffichageUneCarte = new ControlleurAffichageUneCarte(this);
    }
}
