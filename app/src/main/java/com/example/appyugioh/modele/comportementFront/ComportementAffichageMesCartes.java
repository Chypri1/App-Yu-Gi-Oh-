package com.example.appyugioh.modele.comportementFront;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
            int buttonsPerRow= 3;

            // Créer un LinearLayout pour chaque rangée d'images
            LinearLayout rowLayout = null;

            // Pour chaque carte dans le tableau JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                MappeurCarteJson2CarteYuGiOh mappeurCarteJson2CarteYuGiOh = new MappeurCarteJson2CarteYuGiOh();
                CarteYuGiOh carteYuGiOh = mappeurCarteJson2CarteYuGiOh.mapperCarteRest2CarteYuGiOh(jsonObject);
                if(carteYuGiOh!=null) {
                    // Créer un bouton d'image pour afficher la carte
                    ImageButton cardInfo = new ImageButton(activity);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1.0f / buttonsPerRow); // 1.0f divisé par le nombre de boutons par ligne
                    params.weight = 1; // Utiliser le poids pour équilibrer la largeur des boutons
                    cardInfo.setLayoutParams(params);
                    cardInfo.setPadding(0, 0, 0, 0); // Ajuster la marge à zéro
                    cardInfo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    cardInfo.setAdjustViewBounds(true);
                    Picasso.get().load(carteYuGiOh.getLienImage()).into(cardInfo);
                    // Extraire le chemin de l'image de l'intention
                    String imagePath;
                    if (!carteYuGiOh.getLienImage().contains("https")) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(new File(carteYuGiOh.getLienImage())).into(cardInfo);
                            }
                        });
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(carteYuGiOh.getLienImage()).into(cardInfo);
                            }
                        });
                    }

                    // Ajouter le bouton d'image au LinearLayout de la rangée actuelle
                    if (i % buttonsPerRow == 0) {
                        // Créer un nouveau LinearLayout pour une nouvelle rangée
                        rowLayout = new LinearLayout(activity);
                        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                        layoutResultatRecherche.addView(rowLayout);
                    }
                    // Ajouter le bouton d'image au LinearLayout de la rangée actuelle
                    if (rowLayout != null) {
                        rowLayout.addView(cardInfo);
                    }
                    cardInfo.setOnClickListener(v -> {
                        Intent affichageUneCarte = new Intent(activity.getApplicationContext(), AffichageUneCarte.class);
                        affichageUneCarte.putExtra("carteYuGiOh", carteYuGiOh);
                        activity.startActivity(affichageUneCarte);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap resizeBitmap(Bitmap originalBitmap, float scaleFactor) {
        int newWidth = (int) (originalBitmap.getWidth() * scaleFactor);
        int newHeight = (int) (originalBitmap.getHeight() * scaleFactor);
        return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false);
    }


}
