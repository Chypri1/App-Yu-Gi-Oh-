package com.example.appyugioh.vue;

import android.app.Activity;
import android.os.Bundle;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurAffichageMesDecks;
import com.example.appyugioh.controlleur.ControlleurAffichageUnDeck;

public class AffichageUnDeck extends Activity {

    protected ControlleurAffichageUnDeck controlleurAffichageUnDeck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagemescartes);
        this.controlleurAffichageUnDeck = new ControlleurAffichageUnDeck(this);
    }


}
