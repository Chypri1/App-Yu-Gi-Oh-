package com.example.appyugioh.modele.rest;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Handler;
        import android.os.Looper;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import androidx.core.widget.NestedScrollView;

        import com.example.appyugioh.R;
        import com.example.appyugioh.modele.mappeur.MappeurCarteRest2CarteYuGiOh;
        import com.example.appyugioh.modele.metier.CarteYuGiOh;
        import com.example.appyugioh.vue.AffichageUneCarte;
        import com.example.appyugioh.vue.RechercheDeck;
        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.Serializable;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.CopyOnWriteArrayList;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
        import java.util.concurrent.TimeUnit;

public class AccesExterneRest {

    private static final String API_URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
    private static final int BUTTON_SIZE_DP = 115;
    private static final int IMAGES_PER_PAGE = 50;
    private Button btn_prev = null;
    private Button btn_next = null;

    private int currentPage = 0;
    private List<CarteYuGiOh> finalListeCarteYuGiOh;
    private List<CarteYuGiOh> listeFiltreCarteYuGiOh;
    private LinearLayout layoutResultatRecherche;
    private NestedScrollView scrollView;
    private Activity activity;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    protected MappeurCarteRest2CarteYuGiOh mappeurCarteRest2CarteYuGiOh = new MappeurCarteRest2CarteYuGiOh();


    public List<CarteYuGiOh> getFinalListeCarteYuGiOh() {
        return finalListeCarteYuGiOh;
    }

    public List<CarteYuGiOh> getListeFiltreCarteYuGiOh() {
        return listeFiltreCarteYuGiOh;
    }

    public void setListeFiltreCarteYuGiOh(List<CarteYuGiOh> listeFiltreCarteYuGiOh) {
        this.listeFiltreCarteYuGiOh = listeFiltreCarteYuGiOh;
        currentPage=0;
        afficher_carte(currentPage);
    }

    public AccesExterneRest(Button btn_prev, Button btn_next, NestedScrollView scrollView) {
        this.btn_prev = btn_prev;
        this.btn_next = btn_next;
        this.scrollView=scrollView;

        btn_prev.setOnClickListener(v -> {
            loadPreviousImages();
        });
        btn_next.setOnClickListener(v -> {
            loadNextImages();
        });
    }
    public AccesExterneRest() {
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
                activity.runOnUiThread(() -> {
                    layoutResultatRecherche.removeAllViews();
                    TextView noCardsTextView = new TextView(activity);
                    noCardsTextView.setText("Aucune carte trouvée");
                    noCardsTextView.setGravity(Gravity.CENTER);
                    layoutResultatRecherche.addView(noCardsTextView);
                    if(finalListeCarteYuGiOh != null)
                        finalListeCarteYuGiOh.clear();
                    if(listeFiltreCarteYuGiOh != null)
                        listeFiltreCarteYuGiOh.clear();
                });
                return;
            }

            finalListeCarteYuGiOh = listeCarteYuGiOh;
            listeFiltreCarteYuGiOh = listeCarteYuGiOh;
            afficher_carte(currentPage);
        });
    }

    public List<CarteYuGiOh> appRestExact(String nomCarte) {
        final List<CarteYuGiOh> listeCarteYuGiOh = new CopyOnWriteArrayList<>();

        executorService.execute(() -> {
            try {
                URL url = new URL(API_URL + "?name=" + nomCarte + "&language=fr");
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
                listeCarteYuGiOh.addAll(mappeurCarteRest2CarteYuGiOh.mapperListeCarteRest2ListeCarteYuGiOh(cardsArray));
            } catch (IOException | JSONException e) {
                Log.w("REQUETE", "Erreur: Aucune carte trouvée");
                e.printStackTrace();
            }

        });
        return listeCarteYuGiOh;
    }


    private void afficher_carte(int page) {
        if (listeFiltreCarteYuGiOh == null || listeFiltreCarteYuGiOh.isEmpty()) {
            layoutResultatRecherche.removeAllViews();
            return;
        }

        int startIndex = page * IMAGES_PER_PAGE;
        int endIndex = Math.min((page + 1) * IMAGES_PER_PAGE, listeFiltreCarteYuGiOh.size());
        int totalImages = endIndex - startIndex;

        int buttonSizePx = convertDpToPx(BUTTON_SIZE_DP);
        int buttonsPerRow = 3;

        activity.runOnUiThread(() -> {

            // Vérifier si layoutResultatRecherche est null avant d'ajouter des vues
            if (layoutResultatRecherche == null) {
                Log.e("AccesExterneRest", "layoutResultatRecherche est null");
                return;
            }
            layoutResultatRecherche.removeAllViews();

            LinearLayout rowLayout = null;

            for (int i = startIndex; i < endIndex; i++) {
                CarteYuGiOh carteYuGiOh = listeFiltreCarteYuGiOh.get(i);

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
                    rowLayout.addView(cardInfo);
                    cardInfo.setOnClickListener(v -> {
                        Intent affichageUneCarte = new Intent(activity.getApplicationContext(), AffichageUneCarte.class);
                        affichageUneCarte.putExtra("carteYuGiOh", carteYuGiOh);
                        activity.startActivity(affichageUneCarte);
                    });
                }
            }

            // Afficher les boutons suivant et précédent si nécessaire
            if (listeFiltreCarteYuGiOh.size()>IMAGES_PER_PAGE) {
                btn_prev.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);
            } else {
                btn_prev.setVisibility(View.GONE);
                btn_next.setVisibility(View.GONE);
            }
            if(currentPage==0)
                btn_prev.setVisibility(View.GONE);
            if(currentPage>=listeFiltreCarteYuGiOh.size() / IMAGES_PER_PAGE)
                btn_next.setVisibility(View.GONE);
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
        if (listeFiltreCarteYuGiOh != null && !listeFiltreCarteYuGiOh.isEmpty()) {
            if (currentPage < listeFiltreCarteYuGiOh.size() / IMAGES_PER_PAGE) {
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

