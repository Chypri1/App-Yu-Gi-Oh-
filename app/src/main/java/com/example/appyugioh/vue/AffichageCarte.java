package com.example.appyugioh.vue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.ComportementMenu;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AffichageCarte extends Activity {

    protected LinearLayout layoutResultatRecherche;

    protected NavigationView navigationView;

    protected ComportementMenu comportementMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagecarte);

        layoutResultatRecherche = findViewById(R.id.layoutResultatRecherche);
        afficherImagesEnregistrees();



        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        this.comportementMenu = new ComportementMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);

        final Activity elem1 = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, elem1);
            }
        });



    }

    private void afficherImagesEnregistrees() {
        try {
            // Lire le fichier JSON contenant les informations sur les images enregistrées
            File jsonFile = new File(getFilesDir(), "carte.json");
            FileInputStream fis = new FileInputStream(jsonFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line = stringBuilder.toString();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Convertir la chaîne JSON en un objet JSON
            String jsonString = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);




                // Récupérer les informations de la carte
                String nomCarte = jsonObject.getString("nomCarte");
                String nomEdition = jsonObject.getString("nomEdition");
                String imagePath = jsonObject.getString("imagePath");

                // Créer un cadre ImageView pour afficher l'image
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                imageView.setLayoutParams(layoutParams);

                // Charger l'image depuis le chemin de l'image
                Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
                // Afficher l'image dans l'ImageView
                imageView.setImageBitmap(imageBitmap);

                // Ajouter l'ImageView au layoutResultatRecherche
                layoutResultatRecherche.addView(imageView);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
