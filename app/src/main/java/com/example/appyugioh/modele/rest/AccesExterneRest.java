package com.example.appyugioh.modele.rest;

        import android.app.Activity;
        import android.os.Handler;
        import android.os.Looper;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

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
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class AccesExterneRest {

    private static final String API_URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
    private static final int BUTTON_SIZE_DP = 115;
    private static final int IMAGES_PER_PAGE = 50;

    private final Button btn_prev;
    private final Button btn_next;
    private int currentPage = 0;
    private List<CarteYuGiOh> finalListeCarteYuGiOh;
    private LinearLayout layoutResultatRecherche;
    private ScrollView scrollView;
    private Activity activity;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    protected MappeurCarteRest2CarteYuGiOh mappeurCarteRest2CarteYuGiOh = new MappeurCarteRest2CarteYuGiOh();

    public AccesExterneRest(Button btn_prev, Button btn_next, ScrollView scrollView) {
        this.btn_prev = btn_prev;
        this.btn_next = btn_next;
        this.scrollView=scrollView;

        btn_prev.setOnClickListener(v -> {
            Log.d("AccesExterneRest", "Bouton précédent cliqué");
            loadPreviousImages();
        });
        btn_next.setOnClickListener(v -> {
            Log.d("AccesExterneRest", "Bouton suivant cliqué");
            loadNextImages();
        });
    }


    public void appRest(String nomCarte, LinearLayout layoutResultatRecherche, Activity activity) {
        this.layoutResultatRecherche = layoutResultatRecherche;
        this.activity = activity;

        executorService.execute(() -> {
            List<CarteYuGiOh> listeCarteYuGiOh = new ArrayList<>();
            currentPage=0;

            try {
                URL url = new URL(API_URL + "?fname=" + nomCarte + "&language=fr");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                connection.disconnect();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray cardsArray = jsonResponse.getJSONArray("data");
                listeCarteYuGiOh = mappeurCarteRest2CarteYuGiOh.mapperListeCarteRest2ListeCarteYuGiOh(cardsArray);
            } catch (IOException | JSONException e) {
                Log.w("REQUETE", "Erreur: Aucune carte trouvée");
                e.printStackTrace();
            }

            finalListeCarteYuGiOh = listeCarteYuGiOh;
            afficher_carte(currentPage);
        });
    }

    private void afficher_carte(int page) {
        if (finalListeCarteYuGiOh == null || finalListeCarteYuGiOh.isEmpty()) {
            return;
        }

        int startIndex = page * IMAGES_PER_PAGE;
        int endIndex = Math.min((page + 1) * IMAGES_PER_PAGE, finalListeCarteYuGiOh.size());
        int totalImages = endIndex - startIndex;
        Log.d("TOTAL", String.valueOf(totalImages));
        Log.d("startIndex", String.valueOf(startIndex));
        Log.d("endIndex", String.valueOf(endIndex));
        Log.d("TAILLE", String.valueOf(finalListeCarteYuGiOh.size()));

        int buttonSizePx = convertDpToPx(BUTTON_SIZE_DP);
        int buttonsPerRow = 3;

        activity.runOnUiThread(() -> {
            // Vérifier si layoutResultatRecherche est null avant d'ajouter des vues
            if (layoutResultatRecherche == null) {
                Log.e("AccesExterneRest", "layoutResultatRecherche est null");
                return;
            }

            // Définir la position de défilement à 0
            layoutResultatRecherche.scrollTo(0, 0);
            layoutResultatRecherche.removeAllViews();

            LinearLayout rowLayout = null;

            for (int i = startIndex; i < endIndex; i++) {
                CarteYuGiOh carteYuGiOh = finalListeCarteYuGiOh.get(i);

                if (i % buttonsPerRow == 0) {
                    rowLayout = new LinearLayout(activity);
                    rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    layoutResultatRecherche.addView(rowLayout);
                }
                if(rowLayout!=null) {
                    ImageButton cardInfo = new ImageButton(activity);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx + 100);
                    params.setMargins(0, 0, 16, 16);
                    cardInfo.setLayoutParams(params);
                    Picasso.get().load(carteYuGiOh.getLienImage()).resize(buttonSizePx, buttonSizePx + 100).into(cardInfo);
                    rowLayout.addView(cardInfo);
                }
            }

            // Afficher les boutons suivant et précédent si nécessaire
            if (finalListeCarteYuGiOh.size()>IMAGES_PER_PAGE) {
                btn_prev.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);
            } else {
                btn_prev.setVisibility(View.GONE);
                btn_next.setVisibility(View.GONE);
            }
            scrollView.post(() -> {
                scrollView.smoothScrollTo(0, 0);
            });
        });
    }


    private int convertDpToPx(int dp) {
        float density = activity.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void loadNextImages() {
        if (finalListeCarteYuGiOh != null && !finalListeCarteYuGiOh.isEmpty()) {
            if (currentPage < finalListeCarteYuGiOh.size() / IMAGES_PER_PAGE) {
                currentPage++;
                afficher_carte(currentPage);
            }
        }
    }

    private void loadPreviousImages() {
        if (currentPage > 0) {
            currentPage--;
            afficher_carte(currentPage);
        }
    }
}

