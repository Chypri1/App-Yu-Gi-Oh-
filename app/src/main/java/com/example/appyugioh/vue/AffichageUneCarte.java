package com.example.appyugioh.vue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurAffichageUneCarte;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.google.android.material.navigation.NavigationView;

public class AffichageUneCarte extends Activity {
    protected DrawerLayout drawerLayout;
    protected ScrollView scrollView;
    protected ImageButton boutonMenuDeroulant;
    protected TextView texteViewNomCarte;
    protected ImageView imageViewImage;

    protected TextView descriptionCarte;
    protected NavigationView navigationView;
    protected TextView textViewRarete;
    protected TextView textViewPrix;
    protected Button boutonListeEdition;

    protected Button boutonAjoutMesCartes;

    public Button getBoutonAjoutMesCartes() {
        return boutonAjoutMesCartes;
    }

    public void setBoutonAjoutMesCartes(Button boutonAjoutMesCartes) {
        this.boutonAjoutMesCartes = boutonAjoutMesCartes;
    }

    protected ControlleurAffichageUneCarte controlleurAffichageUneCarte;

    public TextView getTextViewRarete() {
        return textViewRarete;
    }

    public void setTextViewRarete(TextView textViewRarete) {
        this.textViewRarete = textViewRarete;
    }

    public TextView getTextViewPrix() {
        return textViewPrix;
    }

    public void setTextViewPrix(TextView textViewPrix) {
        this.textViewPrix = textViewPrix;
    }

    public Button getBoutonListeEdition() {
        return boutonListeEdition;
    }

    public void setBoutonListeEdition(Button boutonListeEdition) {
        this.boutonListeEdition = boutonListeEdition;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public TextView getDescriptionCarte() {
        return descriptionCarte;
    }

    public void setDescriptionCarte(TextView descriptionCarte) {
        this.descriptionCarte = descriptionCarte;
    }

    public ControlleurAffichageUneCarte getControlleurAffichageUneCarte() {
        return controlleurAffichageUneCarte;
    }

    public void setControlleurAffichageUneCarte(ControlleurAffichageUneCarte controlleurAffichageUneCarte) {
        this.controlleurAffichageUneCarte = controlleurAffichageUneCarte;
    }

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageunecarte);
        controlleurAffichageUneCarte = new ControlleurAffichageUneCarte(this);

    }



}
