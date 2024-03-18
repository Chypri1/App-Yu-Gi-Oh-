package com.example.appyugioh.controlleur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesCartes;
import com.example.appyugioh.modele.comportementFront.ComportementAffichageMesDecks;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.metier.Deck;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.AffichageUneCarte;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ControlleurAffichageUneCarte {


    protected AffichageUneCarte activite;

    protected ComportementMenu comportementMenu;

    protected ComportementAffichageMesCartes comportementAffichageMesCartes;

    protected CarteYuGiOh carteYuGiOh;

    protected AccesExterneRest accesExterneRest;

    public ControlleurAffichageUneCarte (AffichageUneCarte activity)
    {
        this.activite = activity;
        this.comportementMenu = new ComportementMenu();
        this.comportementAffichageMesCartes = new ComportementAffichageMesCartes();
        initialiseActivite();
        initialiseComportement();
        observateur();
    }

    public AffichageUneCarte getActivite() {
        return activite;
    }

    public void setActivite(AffichageUneCarte activite) {
        this.activite = activite;
    }

    public ComportementMenu getComportementMenu() {
        return comportementMenu;
    }

    public void setComportementMenu(ComportementMenu comportementMenu) {
        this.comportementMenu = comportementMenu;
    }

    public ComportementAffichageMesCartes getComportementAffichageMesCartes() {
        return comportementAffichageMesCartes;
    }

    public void setComportementAffichageMesCartes(ComportementAffichageMesCartes comportementAffichageMesCartes) {
        this.comportementAffichageMesCartes = comportementAffichageMesCartes;
    }

    public AccesExterneRest getAccesExterneRest() {
        return accesExterneRest;
    }

    public void setAccesExterneRest(AccesExterneRest accesExterneRest) {
        this.accesExterneRest = accesExterneRest;
    }

    public void initialiseActivite()
    {
        activite.setDrawerLayout( activite.findViewById(R.id.drawerLayout));
        activite.setTexteViewNomCarte(activite.findViewById(R.id.textViewNomCarte));
        activite.setImageViewImage(activite.findViewById(R.id.imageViewCarte));
        activite.setDescriptionCarte(activite.findViewById(R.id.descriptionCarte));
        activite.setBoutonListeEdition(activite.findViewById(R.id.boutonListeEdition));
        activite.setTextViewRarete(activite.findViewById(R.id.textViewRarete));
        activite.setTextViewPrix(activite.findViewById(R.id.textViewPrix));
        activite.setBoutonAjoutMesCartes((activite.findViewById(R.id.boutonAjoutMesCartes)));

        activite.setNavigationView(activite.findViewById(R.id.nav_view));
        Menu menu = activite.getNavigationView().getMenu();
        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
    }


    public void initialiseComportement()
    {
    }

    public void observateur()
    {


        activite.getBoutonAjoutMesCartes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherPopupListeDecks();
            }
        });

        String nomCarte;
        if((nomCarte = activite.getIntent().getStringExtra("nomCarte"))!= null)
        {

            nomCarte = activite.getIntent().getStringExtra("nomCarte");
            activite.getTexteViewNomCarte().setText(nomCarte);
        }
        else{
            this.carteYuGiOh = (CarteYuGiOh) activite.getIntent().getSerializableExtra("carteYuGiOh");
            activite.getTexteViewNomCarte().setText(carteYuGiOh.getNom());
        }




        // Extraire le chemin de l'image de l'intention
        String imagePath;
        if((imagePath = activite.getIntent().getStringExtra("imagePath")) != null)
        {
                Picasso.get().load(new File(imagePath)).resize(550,800).into(activite.getImageViewImage());
        }
        else
        {
            Picasso.get().load(carteYuGiOh.getLienImage()).resize(550,800).into(activite.getImageViewImage());
        }
        if(carteYuGiOh != null)
        {
            final int n = 0;
            activite.getDescriptionCarte().setText(carteYuGiOh.getDesc());
            activite.getTextViewPrix().setText(carteYuGiOh.getListeEdition().get(n).getPrix().toString());
            activite.getTextViewRarete().setText(carteYuGiOh.getListeEdition().get(n).getRarete());
            activite.getBoutonListeEdition().setText(carteYuGiOh.getListeEdition().get(n).getCode());
            activite.getBoutonListeEdition().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int ni = (n + 1) % carteYuGiOh.getListeEdition().size();

                    activite.getTextViewPrix().setText(carteYuGiOh.getListeEdition().get(ni).getPrix().toString());
                    activite.getTextViewRarete().setText(carteYuGiOh.getListeEdition().get(ni).getRarete());
                    activite.getBoutonListeEdition().setText(carteYuGiOh.getListeEdition().get(ni).getCode());
                }
            });
        }

        activite.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activite);
            }
        });

        ImageButton boutonMenuDeroulant=activite.findViewById(R.id.menuDeroulant);
        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite.getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });


        // Configuration du geste de balayage pour ouvrir le tiroir de navigation
        activite.getDrawerLayout().setOnTouchListener(new OnSwipeTouchListener(activite) {
            @Override
            public void onSwipeRight() {
                if (!activite.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
                    activite.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void afficherPopupListeDecks() {

        // Récupérer la liste des noms de decks
        List<String> nomsDecks = new ArrayList<>();
        List<Deck> decks = new ArrayList<>();
        try {
            FileInputStream fis = activite.openFileInput("decks.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Créer un objet Deck à partir des données JSON
                String nom = jsonObject.getString("nom");
                // JSONArray listeCarte = jsonObject.getJSONArray("listeCarte");

                // Ajouter le deck à la liste
                Deck deck = new Deck();
                deck.setNom(nom);
                // Ajoutez les autres propriétés du deck ici, si nécessaire
                decks.add(deck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Deck deck : decks) {
            nomsDecks.add(deck.getNom());
        }

        // Créer une popup avec la liste des noms de decks
        AlertDialog.Builder builder = new AlertDialog.Builder(activite);
        builder.setTitle("Sélectionner un deck");
        builder.setItems(nomsDecks.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Récupérer le nom du deck sélectionné
                String nomDeckSelectionne = nomsDecks.get(which);

                // Trouver le deck correspondant au nom sélectionné
                Deck deckSelectionne = null;
                for (Deck deck : decks) {
                    if (deck.getNom().equals(nomDeckSelectionne)) {
                        deckSelectionne = deck;
                        break;
                    }
                }

                if (deckSelectionne != null) {
                    // Ajouter la carteYuGiOh au deck sélectionné
                    deckSelectionne.getListeCarteYuGiOh().add(carteYuGiOh);

                    // Enregistrer les modifications dans le fichier JSON
                    ComportementAffichageMesDecks comportementAffichageMesDecks = new ComportementAffichageMesDecks();
                    comportementAffichageMesDecks.saveDecksToFile(decks, activite);
                } else {
                    // Gérer le cas où aucun deck correspondant n'est trouvé
                    Toast.makeText(activite, "Aucun deck correspondant trouvé", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();

    }


}
