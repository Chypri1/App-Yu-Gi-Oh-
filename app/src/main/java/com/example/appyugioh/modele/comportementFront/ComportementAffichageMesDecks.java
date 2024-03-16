package com.example.appyugioh.modele.comportementFront;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.metier.Deck;

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

    public LinearLayout layoutResultatRecherche;

    public ComportementAffichageMesDecks(LinearLayout layoutResultatRecherche) {
        this.layoutResultatRecherche = layoutResultatRecherche;
    }
    public void afficherDecks(LinearLayout layoutResultatRecherche, Activity activite) {
        List<Deck> decks = new ArrayList<>();
        try {
            File jsonFile = new File(activite.getFilesDir(), "decks.json");
            if (!jsonFile.exists()) {
                // Si le fichier n'existe pas, créez-le avec une liste de decks vide
                jsonFile.createNewFile();
                FileWriter fileWriter = new FileWriter(jsonFile);
                fileWriter.write("[]"); // Initialiser le fichier avec une liste JSON vide
                fileWriter.close();
            }

            // Lire le contenu du fichier JSON
            FileInputStream fis = activite.openFileInput("decks.json");
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = fis.read()) != -1) {
                stringBuilder.append((char) character);
            }
            fis.close();

            // Analyser le contenu JSON en une liste de decks
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Deck deck = new Deck();
                deck.setNom(jsonObject.getString("nom"));
                decks.add(deck);
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
                textPrix.setText("Prix: $" + 20);
                layoutResultatRecherche.addView(deckView);

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


    private void saveDecksToFile(List<Deck> decks, Activity activite) {
        // Création du fichier JSONArray pour stocker les informations des decks
        JSONArray jsonArray = new JSONArray();

        // Récupération du fichier de decks s'il existe
        File jsonFile = new File(activite.getFilesDir(), "decks.json");

        // Vérification de l'existence du fichier
        if (jsonFile.exists()) {
            // Si le fichier existe, essayez de le lire
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                // Convertir la chaîne JSON en un JSONArray
                jsonArray = new JSONArray(stringBuilder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e("DeckManager", "Erreur lors de la lecture du fichier JSON");
                return;
            }
        }

        // Ajouter les informations du nouveau deck à la JSONArray
        for (Deck deck : decks) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nom", deck.getNom());
                jsonObject.put("nombreCartes", deck.getListeCarteYuGiOh().size());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("DeckManager", "Erreur lors de l'ajout du deck au JSONArray");
            }
        }

        // Enregistrer la JSONArray mise à jour dans le fichier
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DeckManager", "Erreur lors de l'enregistrement des decks dans le fichier JSON");
        }
    }

    @SuppressLint("MissingInflatedId")
    public void afficherPopupNouveauDeck(Activity activite) {
        // Créez une vue pour votre popup en inflatant le layout XML
        View popupView = LayoutInflater.from(activite).inflate(R.layout.nouveau_deck_popup, null);

        // Trouvez les vues nécessaires dans le layout de la popup
         EditText editTextNomDeck = popupView.findViewById(R.id.editTextNomDeck);

        // Créez une boîte de dialogue AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activite);
        builder.setView(popupView);
        builder.setTitle("Nouveau Deck");
        LinearLayout layoutResultat = this.layoutResultatRecherche;
        builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Récupérez les valeurs des champs de la popup
                Deck deck = new Deck();
                      deck.setNom(editTextNomDeck.getText().toString());

                // Affichez un message pour indiquer que le deck a été créé
                afficherMessage("Nouveau Deck", "Le deck " + deck.getNom() + " a été créé avec succès !",activite);
                saveDecksToFile(List.of(deck),activite);
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
