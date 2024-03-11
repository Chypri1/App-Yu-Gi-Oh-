package com.example.appyugioh.vue;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.*;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appyugioh.modele.ComportementEnregistrementCarte;
import com.example.appyugioh.modele.ComportementMenu;
import com.example.appyugioh.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnregistrerCarte extends Activity {

    protected int REQUEST_CAMERA_PERMISSION = 101;

    protected int RETOUR_PRENDRE_PHOTO = 1;
    protected EditText nomCarte;

    protected EditText nomEdition;

    protected LinearLayout layoutResultatCam;

    protected ImageView imageCam;

    protected Button boutonAccesCamera;

    protected Button boutonEnregistrementCarte;

    protected NavigationView navigationView;

    protected ComportementMenu comportementMenu;

    protected ComportementEnregistrementCarte comportementEnregistrementCarte;

    protected Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrercarte);

        nomCarte = findViewById(R.id.nomCarte);

        nomEdition = findViewById(R.id.nomEdition);

        layoutResultatCam = findViewById(R.id.layoutResultatCam);

        imageCam = findViewById(R.id.imageCam);

        boutonAccesCamera = findViewById(R.id.boutonAccesCamera);

        boutonEnregistrementCarte = findViewById(R.id.boutonEnregistrementCarte);

        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        this.comportementMenu = new ComportementMenu();

        this.comportementEnregistrementCarte = new ComportementEnregistrementCarte();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activity);
            }
        });

        boutonAccesCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comportementEnregistrementCarte.demanderPermissionCamera(activity);
            }
        });

        boutonEnregistrementCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    comportementEnregistrementCarte.enregistrementCarte(nomCarte, nomEdition, imageCam,activity);
                    comportementEnregistrementCarte.afficherConfirmationEnregistrementCarte(activity);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETOUR_PRENDRE_PHOTO) {
            if (resultCode == RESULT_OK ) {
                // La photo a été prise et le résultat est valide
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    // Utiliser l'image capturée
                    imageCam.setImageBitmap(imageBitmap);

                }
            }
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                comportementEnregistrementCarte.ouvrirCamera(activity);
            } else {
                // La permission a été refusée. Vous pouvez afficher un message à l'utilisateur.
                Toast.makeText(this, "Permission de la caméra refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


