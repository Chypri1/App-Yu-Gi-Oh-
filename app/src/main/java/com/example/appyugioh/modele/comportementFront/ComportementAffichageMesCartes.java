package com.example.appyugioh.modele.comportementFront;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appyugioh.vue.AffichageUneCarte;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ComportementAffichageMesCartes
{


    public void afficherImagesEnregistrees(LinearLayout layoutResultatRecherche, Activity activity) {
        try {
            // Lire le fichier JSON contenant les informations sur les images enregistrées
            File jsonFile = new File(activity.getFilesDir(), "carte.json");
            FileInputStream fis = new FileInputStream(jsonFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Convertir la chaîne JSON en un objet JSON
            String jsonString = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);


            // TODO : faire la liste des cartes et pas une puis les afficher dans le layout

            // Récupérer les informations de la carte
            String nomCarte = jsonObject.getString("nomCarte");
            String nomEdition = jsonObject.getString("nomEdition");
            String imagePath = jsonObject.getString("imagePath");

            // Créer un cadre ImageView pour afficher l'image
            ImageButton imageButton = new ImageButton(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            imageButton.setLayoutParams(layoutParams);

            // Charger l'image depuis le chemin de l'image
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
            // Afficher l'image dans l'ImageView
            imageButton.setImageBitmap(imageBitmap);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Rediriger l'utilisateur vers une autre page
                    Intent affichageUneCarte = new Intent(activity, AffichageUneCarte.class); // Remplacez NouvellePage par le nom de votre classe d'activité cible
                    affichageUneCarte.putExtra("nomCarte",nomCarte);
                    affichageUneCarte.putExtra("nomEdition", nomEdition);
                    affichageUneCarte.putExtra("imagePath", imagePath);
                    activity.startActivity(affichageUneCarte);
                    activity.finish();
                }
            });

            // Ajouter l'ImageView au layoutResultatRecherche
            layoutResultatRecherche.addView(imageButton);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
