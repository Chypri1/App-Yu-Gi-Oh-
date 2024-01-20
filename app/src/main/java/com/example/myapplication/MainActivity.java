package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected Button bouttonRechercheCarte;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouttonRechercheCarte = findViewById(R.id.boutonRecherche);

        bouttonRechercheCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent versPageRecherche = new Intent(getApplicationContext(),RechercheCarte.class);
                startActivity(versPageRecherche);
                finish();
            }
        });
    }

}
