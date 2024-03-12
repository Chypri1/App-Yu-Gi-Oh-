package com.example.appyugioh.vue;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.modele.comportementFront.ComportementEnregistrementCarte;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.R;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONException;


public class EnregistrerCarte extends Activity {

    protected int REQUEST_CAMERA_PERMISSION = 101;

    protected int RETOUR_PRENDRE_PHOTO = 1;

    protected DrawerLayout drawerLayout;
    protected EditText nomCarte;

    protected EditText nomEdition;

    protected LinearLayout layoutResultatCam;

    protected ImageView imageCam;

    protected Button boutonAccesCamera;

    protected Button boutonEnregistrementCarte;

    protected NavigationView navigationView;

    protected ComportementMenu comportementMenu;

    protected ComportementEnregistrementCarte comportementEnregistrementCarte;

    protected ImageButton boutonMenuDeroulant;

    protected Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrercarte);

        drawerLayout = findViewById(R.id.drawerLayout);

        boutonMenuDeroulant = findViewById(R.id.menuDeroulant);

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

        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
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


