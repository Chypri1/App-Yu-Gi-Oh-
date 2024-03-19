package com.example.appyugioh.vue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurEnregistrerCarte;
import com.google.android.material.navigation.NavigationView;


public class EnregistrerCarte extends Activity {

    protected int REQUEST_CAMERA_PERMISSION = 101;

    protected int RETOUR_PRENDRE_PHOTO = 1;
    protected DrawerLayout drawerLayout;
    protected EditText nomCarte;

    protected EditText nomEdition;

    protected ImageButton boutonMenuDeroulant;

    protected LinearLayout layoutResultatCam;

    protected ImageView imageCam;

    protected Button boutonAccesCamera;

    protected Button boutonEnregistrementCarte;

    protected NavigationView navigationView;

    protected ControlleurEnregistrerCarte controlleurEnregistrerCarte;


    public int getREQUEST_CAMERA_PERMISSION() {
        return REQUEST_CAMERA_PERMISSION;
    }

    public void setREQUEST_CAMERA_PERMISSION(int REQUEST_CAMERA_PERMISSION) {
        this.REQUEST_CAMERA_PERMISSION = REQUEST_CAMERA_PERMISSION;
    }

    public int getRETOUR_PRENDRE_PHOTO() {
        return RETOUR_PRENDRE_PHOTO;
    }

    public void setRETOUR_PRENDRE_PHOTO(int RETOUR_PRENDRE_PHOTO) {
        this.RETOUR_PRENDRE_PHOTO = RETOUR_PRENDRE_PHOTO;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public EditText getNomCarte() {
        return nomCarte;
    }

    public void setNomCarte(EditText nomCarte) {
        this.nomCarte = nomCarte;
    }

    public EditText getNomEdition() {
        return nomEdition;
    }

    public void setNomEdition(EditText nomEdition) {
        this.nomEdition = nomEdition;
    }

    public LinearLayout getLayoutResultatCam() {
        return layoutResultatCam;
    }

    public void setLayoutResultatCam(LinearLayout layoutResultatCam) {
        this.layoutResultatCam = layoutResultatCam;
    }

    public ImageView getImageCam() {
        return imageCam;
    }

    public void setImageCam(ImageView imageCam) {
        this.imageCam = imageCam;
    }

    public Button getBoutonAccesCamera() {
        return boutonAccesCamera;
    }

    public void setBoutonAccesCamera(Button boutonAccesCamera) {
        this.boutonAccesCamera = boutonAccesCamera;
    }

    public Button getBoutonEnregistrementCarte() {
        return boutonEnregistrementCarte;
    }

    public void setBoutonEnregistrementCarte(Button boutonEnregistrementCarte) {
        this.boutonEnregistrementCarte = boutonEnregistrementCarte;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrercarte);
        controlleurEnregistrerCarte = new ControlleurEnregistrerCarte(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETOUR_PRENDRE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    // Utiliser l'image capturée
                    imageCam.setImageBitmap(imageBitmap);

                    // Obtenez la largeur de l'écran
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int screenWidth = size.x;

                    // Définir la largeur de l'imageCam sur 50% de la largeur de l'écran
                    ViewGroup.LayoutParams layoutParams = imageCam.getLayoutParams();
                    layoutParams.width = screenWidth / 2;
                    imageCam.setLayoutParams(layoutParams);
                }
            }
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        controlleurEnregistrerCarte.retourPermission(requestCode, permissions,grantResults);
    }
}


