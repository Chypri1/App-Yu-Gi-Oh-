package com.example.appyugioh.modele.comportementFront;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appyugioh.modele.mappeur.MappeurCarteJson2CarteYuGiOh;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.vue.AffichageUneCarte;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
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
                System.out.print(line);
            }
            bufferedReader.close();

            // Convertir la chaîne JSON en un tableau JSON
            String jsonString = stringBuilder.toString();
            JSONArray jsonArray = new JSONArray(jsonString);

            // Pour chaque carte dans le tableau JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                MappeurCarteJson2CarteYuGiOh mappeurCarteJson2CarteYuGiOh = new MappeurCarteJson2CarteYuGiOh();
                CarteYuGiOh carteYuGiOh = mappeurCarteJson2CarteYuGiOh.mapperCarteRest2CarteYuGiOh(jsonObject);

                // Créer un bouton d'image pour afficher la carte
                ImageButton imageButton = new ImageButton(activity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                imageButton.setLayoutParams(layoutParams);

                // Extraire le chemin de l'image de l'intention
                String imagePath;
                if(!carteYuGiOh.getLienImage().contains("https"))
                {
                    Picasso.get().load(new File(carteYuGiOh.getLienImage())).resize(100,125).into(imageButton);
                }
                else
                {
                    Picasso.get().load(carteYuGiOh.getLienImage()).resize(100,125).into(imageButton);
                }

                // Ajouter un écouteur de clic au bouton d'image
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Rediriger l'utilisateur vers la page d'affichage de la carte
                        Intent affichageUneCarte = new Intent(activity, AffichageUneCarte.class);
                        affichageUneCarte.putExtra("carteYuGiOh", carteYuGiOh);
                        activity.startActivity(affichageUneCarte);
                        activity.finish();
                    }
                });

                // Ajouter le bouton d'image au layoutResultatRecherche
                layoutResultatRecherche.addView(imageButton);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public Bitmap resizeBitmap(Bitmap originalBitmap, float scaleFactor) {
        int newWidth = (int) (originalBitmap.getWidth() * scaleFactor);
        int newHeight = (int) (originalBitmap.getHeight() * scaleFactor);
        return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false);
    }


}
