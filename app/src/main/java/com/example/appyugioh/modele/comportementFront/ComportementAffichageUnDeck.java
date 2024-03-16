package com.example.appyugioh.modele.comportementFront;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.metier.Deck;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.AffichageUnDeck;
import com.example.appyugioh.vue.AffichageUneCarte;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ComportementAffichageUnDeck {

    protected AccesExterneRest accesExterneRest;

    public ComportementAffichageUnDeck(AccesExterneRest accesExterneRest) {
        this.accesExterneRest = accesExterneRest;
    }

    public void afficherPopupNomCarte(Activity activity, LinearLayout layoutResultatRecherche) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Entrez le nom de la carte");

        // Set up the input
        final EditText input = new EditText(activity);
        // Specify the type of input expected; this, for example, sets the input as a text, and will also enable autocorrect
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nomCarte = input.getText().toString();
                afficherPopupAvecImage(activity,layoutResultatRecherche, nomCarte);
                afficherImageDansResultatRecherche(layoutResultatRecherche,activity,nomCarte);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void afficherPopupAvecImage(Activity activite,LinearLayout layoutResultatRecherche, String nomCarte) {
        // Créer une vue personnalisée pour la popup avec l'image et le nom de la carte
        View popupView = LayoutInflater.from(activite).inflate(R.layout.layout_popup_image, null);

        // Récupérer les vues de la vue de la popup
        ImageView imageCarte = popupView.findViewById(R.id.imageCarte);
        TextView textNomCarte = popupView.findViewById(R.id.textNomCarte);

        accesExterneRest.appRest(nomCarte,layoutResultatRecherche,activite);
        List<CarteYuGiOh> listeCarteYuGiOh = accesExterneRest.getFinalListeCarteYuGiOh();

        for(CarteYuGiOh carte :listeCarteYuGiOh) {
            // Récupérer l'ID de ressource de l'image en fonction de son nom
            Picasso.get().load(carte.getLienImage()).into(imageCarte);


                // Afficher le nom de la carte dans le TextView
                textNomCarte.setText(nomCarte);

                // Créer et afficher la popup avec la vue personnalisée
                AlertDialog.Builder builder = new AlertDialog.Builder(activite);
                builder.setView(popupView);
                builder.setPositiveButton("OK", null); // Vous pouvez ajouter un bouton OK ou annuler si nécessaire
                AlertDialog dialog = builder.create();
                dialog.show();
        }
    }

    public void afficherImageDansResultatRecherche(LinearLayout layoutResultatRecherche, Activity activite, String nomCarte) {
        // Créer un ImageView pour afficher l'image de la carte
        ImageButton imageView = new ImageButton(activite);

        // Récupérer l'ID de ressource de l'image en fonction de son nom
        int imageResourceId = activite.getResources().getIdentifier(nomCarte.toLowerCase(), "drawable", activite.getPackageName());

        if (imageResourceId != 0) { // Vérifier si l'ID de ressource est valide
            // Définir l'image à afficher dans l'ImageView
            imageView.setImageResource(imageResourceId);

            // Définir les paramètres de mise en page pour l'ImageView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent affichageUneCarte = new Intent(activite.getApplicationContext(), AffichageUneCarte.class);
                    activite.startActivity(affichageUneCarte);
                    activite.finish();
                }
            });

            // Ajouter l'ImageView au layoutResultatRecherche
            layoutResultatRecherche.addView(imageView);
        } else {
            // Afficher un message d'erreur si l'image n'est pas trouvée
            Toast.makeText(activite, "Image non trouvée pour " + nomCarte, Toast.LENGTH_SHORT).show();
        }
    }



}
