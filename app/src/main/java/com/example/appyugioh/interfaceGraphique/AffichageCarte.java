package com.example.appyugioh.interfacegraphique;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appyugioh.R;

public class AffichageCarte extends Activity {

    LinearLayout layoutTop;

    LinearLayout layoutFiltre;

    LinearLayout layoutInformations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagecarte);

        layoutTop = findViewById(R.id.layoutTop);

        layoutFiltre = findViewById(R.id.layoutFiltre);

        layoutTop = findViewById(R.id.layoutInformations);
    }
}
