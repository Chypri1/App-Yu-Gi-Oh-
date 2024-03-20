package com.example.appyugioh.modele.comportementFront;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.mappeur.MappeurCarteJson2CarteYuGiOh;
import com.example.appyugioh.modele.mappeur.MappeurCarteRest2CarteYuGiOh;
import com.example.appyugioh.modele.metier.CarteMonstre;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.metier.Deck;
import com.example.appyugioh.modele.metier.Edition;
import com.example.appyugioh.vue.AffichageUnDeck;
import com.example.appyugioh.vue.RechercheDeck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComportementAffichageMesDecks {


    public void afficherDecks(LinearLayout layoutResultatRecherche, Activity activite) {
        List<Deck> decks = new ArrayList<>();
        try {
            File jsonFile = new File(activite.getFilesDir(), "decks.json");
            if (!jsonFile.exists()) {
                if (jsonFile.createNewFile()) {
                    FileWriter fileWriter = new FileWriter(jsonFile);
                    fileWriter.write("[]"); // Initialiser le fichier avec une liste JSON vide
                    fileWriter.close();
                }
            }

            // Lire le contenu du fichier JSON
            FileInputStream fis = activite.openFileInput("decks.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Analyser le contenu JSON en une liste de decks
            String jsonString = stringBuilder.toString().trim();  // Trim pour supprimer les espaces vides
            if (jsonString.isEmpty())
                return;
            JSONArray jsonArray = new JSONArray(jsonString);
            Log.d("array",String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Deck deck = new Deck();
                    deck.setNom(jsonObject.getString("nom"));

                    JSONArray listeCartesJson = jsonObject.getJSONArray("listeCartes");
                    List<CarteYuGiOh> listeCartes = new ArrayList<>();
                    for (int j = 0; j < listeCartesJson.length(); j++) {
                        JSONObject carteJson = listeCartesJson.getJSONObject(j);
                        // Créer un objet CarteYuGiOh et le remplir avec les données JSON
                        MappeurCarteJson2CarteYuGiOh mappeurCarteRest2CarteYuGiOh = new MappeurCarteJson2CarteYuGiOh();
                        CarteYuGiOh carte = mappeurCarteRest2CarteYuGiOh.mapperCarteRest2CarteYuGiOh(carteJson);
                        listeCartes.add(carte);
                    }
                    deck.setListeCarteYuGiOh(listeCartes);
                    decks.add(deck);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("DeckManager", "Erreur lors de la lecture du deck JSON");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // Afficher les decks dans le layoutResultatRecherche
        if (decks != null) {
            for (Deck deck : decks) {
                View deckView = LayoutInflater.from(activite).inflate(R.layout.layout_deck_item, null);
                TextView textNomDeck = deckView.findViewById(R.id.textNomDeck);
                TextView textNombreCartes = deckView.findViewById(R.id.textNombreCartes);
                TextView textPrix = deckView.findViewById(R.id.textPrix);
                textNomDeck.setText(deck.getNom());
                textNombreCartes.setText("Nombre de cartes: " + deck.getListeCarteYuGiOh().size());
                textPrix.setText("Prix: $" + "prix qu'il faut mettre");
                layoutResultatRecherche.addView(deckView);

                deckView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent affichageUnDeck = new Intent(activite.getApplicationContext(), AffichageUnDeck.class);
                        affichageUnDeck.putExtra("deck",deck);
                        activite.startActivity(affichageUnDeck);

                    }
                });
                deckView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activite);
                        builder.setTitle("Confirmation de suppression");
                        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce deck ?");
                        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                supprimerDeckDansFichierJSON(deck.getNom(), activite);
                                layoutResultatRecherche.removeAllViews();
                                afficherDecks(layoutResultatRecherche, activite);
                            }


                        });
                        builder.setNegativeButton("Non", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        return true; // Indique que l'événement a été consommé
                    }
                });
            }
        } else {
            Log.e("DeckManager", "Erreur lors de la récupération des decks");
        }

    }


    public void saveDecksToFile(List<Deck> decks, Activity activite) {
        // Création du fichier JSONArray pour stocker les informations des decks
        JSONArray jsonArray = new JSONArray();

        // Ajouter les informations de chaque nouveau deck à la JSONArray
        for (Deck deck : decks) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nom", deck.getNom());
                jsonObject.put("nombreCartes", deck.getListeCarteYuGiOh().size());

                // Créer un tableau JSON pour stocker les informations sur chaque carte
                JSONArray cartesArray = new JSONArray();
                for (CarteYuGiOh carte : deck.getListeCarteYuGiOh()) {
                    JSONObject carteJson = new JSONObject();

                    // Ajouter les informations de la carte à l'objet JSON représentant la carte
                    // Vous devez remplacer ces valeurs par les propriétés réelles de votre objet CarteYuGiOh
                    carteJson.put("name", carte.getNom());
                    carteJson.put("lienImage", carte.getLienImage());
                    carteJson.put("desc", carte.getDesc());
                    carteJson.put("type", carte.getType());
                    carteJson.put("frameType",carte.getTypeFrame());
                    carteJson.put("race",carte.getRace());
                    if(carte.getType().contains("Monster")) {
                        carteJson.put("atk", ((CarteMonstre) carte).getAttaque());
                        carteJson.put("def", ((CarteMonstre) carte).getDefense());
                        carteJson.put("attribute", ((CarteMonstre) carte).getAttribut());
                        carteJson.put("level", ((CarteMonstre) carte).getNiveau());
                    }
                    JSONArray listeEdition = new JSONArray();
                    for(Edition edition:carte.getListeEdition())
                    {
                        JSONObject editionJson = new JSONObject();
                        editionJson.put("nom",edition.getNom());
                        editionJson.put("code",edition.getCode());
                        editionJson.put("rarete",edition.getRarete());
                        editionJson.put("prix",edition.getPrix());
                        listeEdition.put(editionJson);
                    }
                    carteJson.put("editionCarte",listeEdition);

                    // mettre liste des editions

                    // Ajouter l'objet JSON représentant la carte au tableau JSON
                    cartesArray.put(carteJson);
                }

                // Ajouter le tableau JSON des cartes au deck JSON
                jsonObject.put("listeCartes", cartesArray);

                // Ajouter le deck JSON au tableau JSON principal
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("DeckManager", "Erreur lors de l'ajout du deck au JSONArray");
            }
        }

        // Enregistrer la JSONArray mise à jour dans le fichier
        try {
            FileWriter fileWriter = new FileWriter(new File(activite.getFilesDir(), "decks.json"));
            fileWriter.write(jsonArray.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DeckManager", "Erreur lors de l'enregistrement des decks dans le fichier JSON");
        }
    }

    private JSONArray loadExistingDecks(Activity activite) {
        JSONArray jsonArray = new JSONArray();
        try {
            File decksFile = new File(activite.getFilesDir(), "decks.json");
            if (decksFile.exists()) {
                // Lire le contenu du fichier et le convertir en JSONArray
                BufferedReader bufferedReader = new BufferedReader(new FileReader(decksFile));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                jsonArray = new JSONArray(stringBuilder.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("DeckManager", "Erreur lors de la lecture du fichier JSON des decks");
        }
        return jsonArray;
    }


    @SuppressLint("MissingInflatedId")
    public void afficherPopupNouveauDeck(Activity activite,LinearLayout layoutResultatRecherche) {
        // Créez une vue pour votre popup en inflatant le layout XML
        View popupView = LayoutInflater.from(activite).inflate(R.layout.nouveau_deck_popup, null);

        // Trouvez les vues nécessaires dans le layout de la popup
        EditText editTextNomDeck = popupView.findViewById(R.id.editTextNomDeck);

        // Créez une boîte de dialogue AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activite);
        builder.setView(popupView);
        builder.setTitle("Nouveau Deck");

        builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Récupérez les valeurs des champs de la popup
                Deck deck = new Deck();
                deck.setNom(editTextNomDeck.getText().toString());

                // Affichez un message pour indiquer que le deck a été créé
                afficherMessage("Nouveau Deck", "Le deck " + deck.getNom() + " a été créé avec succès !",activite);
                saveDecksToFile(List.of(deck),activite);
                layoutResultatRecherche.removeAllViews();
                afficherDecks(layoutResultatRecherche, activite);
            }
        });
        builder.setNegativeButton("Annuler", null); // Ajoutez une option pour annuler la création du deck

        // Affichez la boîte de dialogue AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void afficherMessage(String titre, String message,Activity activite) {
        // Créez une boîte de dialogue AlertDialog pour afficher le message
        AlertDialog.Builder builder = new AlertDialog.Builder(activite);
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void supprimerDeckDansFichierJSON(String nomDeck, Activity activite) {
        try {
            // Lire le fichier JSON contenant les informations sur les decks
            File jsonFile = new File(activite.getFilesDir(), "decks.json");
            FileInputStream fis = new FileInputStream(jsonFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Convertir la chaîne JSON en un tableau JSON
            String jsonString = stringBuilder.toString();
            JSONArray jsonArray = new JSONArray(jsonString);

            // Rechercher le deck à supprimer par son nom
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nom = jsonObject.getString("nom");
                if (nom.equals(nomDeck)) {
                    // Supprimer le deck trouvé
                    jsonArray.remove(i);
                    break; // Arrêter la recherche car le deck a été trouvé et supprimé
                }
            }

            // Écrire le nouveau contenu JSON dans le fichier
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString());
            fileWriter.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("DeckManager", "Erreur lors de la suppression du deck dans le fichier JSON");
        }
    }

}