package com.example.appyugioh.modele;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.example.appyugioh.R;
import com.example.appyugioh.vue.AffichageCarte;
import com.example.appyugioh.vue.AffichageDeck;
import com.example.appyugioh.vue.EnregistrerCarte;
import com.example.appyugioh.vue.MainActivity;
import com.example.appyugioh.vue.RechercheCarte;
import com.example.appyugioh.vue.RechercheDeck;

public class ComportementMenu {
    public boolean initItemMenu(@androidx.annotation.NonNull MenuItem item, Activity activity) {
        if (item.getItemId() == R.id.menu_bouton_accueil)
        {
            Intent rechercheCarte = new Intent(activity.getApplicationContext(), MainActivity.class);
            activity.startActivity(rechercheCarte);
            activity.finish();
        }
        if (item.getItemId() == R.id.menu_bouton_recherche_carte)
        {
            Intent rechercheCarte = new Intent(activity.getApplicationContext(), RechercheCarte.class);
            activity.startActivity(rechercheCarte);
            activity.finish();
        }
        if (item.getItemId() == R.id.menu_bouton_recherche_deck)
        {
            Intent rechercheDeck = new Intent(activity.getApplicationContext(), RechercheDeck.class);
            activity.startActivity(rechercheDeck);
            activity.finish();
        }
        if (item.getItemId() == R.id.menu_bouton_mes_cartes)
        {
            Intent mesCartes = new Intent(activity.getApplicationContext(), AffichageCarte.class);
            activity.startActivity(mesCartes);
            activity.finish();
        }
        if (item.getItemId() == R.id.menu_bouton_mes_decks)
        {
            Intent mesDecks = new Intent(activity.getApplicationContext(), AffichageDeck.class);
            activity.startActivity(mesDecks);
            activity.finish();
        }
        if (item.getItemId() == R.id.menu_bouton_enregistrer_carte)
        {
            Intent enregsitrerCarte = new Intent(activity.getApplicationContext(), EnregistrerCarte.class);
            activity.startActivity(enregsitrerCarte);
            activity.finish();
        }
        return true;
    }
}
