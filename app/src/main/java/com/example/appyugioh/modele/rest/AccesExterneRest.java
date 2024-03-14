package com.example.appyugioh.modele.rest;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appyugioh.modele.mappeur.MappeurCarteRest2CarteYuGiOh;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccesExterneRest {

    private static final String API_URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
    private static final int BUTTON_SIZE_DP = 115; // Taille du bouton en dp

    protected MappeurCarteRest2CarteYuGiOh mappeurCarteRest2CarteYuGiOh = new MappeurCarteRest2CarteYuGiOh();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public void appRest(String nomCarte, LinearLayout layoutResultatRecherche, Activity activity) {
        executorService.execute(() -> {
            List<CarteYuGiOh> listeCarteYuGiOh = new ArrayList<>();

            try {
                // Créer l'URL avec le nom de la carte
                URL url = new URL(API_URL + "?fname=" + nomCarte + "&language=fr");

                // Ouvrir la connexion HTTP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Lire la réponse de l'API
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                // Fermer la connexion
                connection.disconnect();

                // Convertir la réponse en objet JSON
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Récupérer le tableau de cartes
                JSONArray cardsArray = jsonResponse.getJSONArray("data");

                listeCarteYuGiOh = mappeurCarteRest2CarteYuGiOh.mapperListeCarteRest2ListeCarteYuGiOh(cardsArray);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            // Envoyer les résultats sur le thread principal via le handler
            List<CarteYuGiOh> finalListeCarteYuGiOh = listeCarteYuGiOh;
            handler.post(() -> afficher_carte(finalListeCarteYuGiOh, layoutResultatRecherche, activity));
        });
    }

    private void afficher_carte(List<CarteYuGiOh> finalListeCarteYuGiOh, LinearLayout layoutResultatRecherche, Activity activity) {
        int buttonSizePx = convertDpToPx(BUTTON_SIZE_DP, activity);
        int buttonsPerRow = 3; // Nombre de boutons par ligne

        LinearLayout rowLayout = null; // Layout temporaire pour chaque ligne

        for (int i = 0; i < finalListeCarteYuGiOh.size(); i++) {
            CarteYuGiOh carteYuGiOh = finalListeCarteYuGiOh.get(i);

            // Créer une nouvelle ligne si nécessaire (tous les 3 boutons)
            if (i % buttonsPerRow == 0) {
                rowLayout = new LinearLayout(activity);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                layoutResultatRecherche.addView(rowLayout);
            }

            // Créer et ajouter l'ImageButton à la ligne actuelle
            ImageButton cardInfo = new ImageButton(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx+100);
            params.setMargins(0, 0, 16, 16);
            cardInfo.setLayoutParams(params);
            Picasso.get().load(carteYuGiOh.getLienImage()).resize(buttonSizePx, buttonSizePx+100).into(cardInfo);
            rowLayout.addView(cardInfo);
        }
    }


    private int convertDpToPx(int dp, Activity activity) {
        float density = activity.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
