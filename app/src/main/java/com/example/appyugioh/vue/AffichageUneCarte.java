package com.example.appyugioh.vue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.R;

public class AffichageUneCarte extends Activity {
    protected DrawerLayout drawerLayout;

    protected TextView texteViewNomCarte;
    protected ImageView imageViewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageunecarte);


        texteViewNomCarte = findViewById(R.id.textViewNomCarte);
        String nomCarte = getIntent().getStringExtra("nomCarte");
        if(nomCarte != null)
        {
            texteViewNomCarte.setText(nomCarte);
        }

        imageViewImage = findViewById(R.id.imageViewCarte);
        // Extraire le chemin de l'image de l'intention
        String imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            // Charger l'image depuis le chemin de l'image
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
            // Afficher l'image dans l'ImageView
            imageViewImage.setImageBitmap(imageBitmap);
        }


        ImageButton boutonMenuDeroulant = findViewById(R.id.menuDeroulant);
        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        // Configuration du geste de balayage pour ouvrir le tiroir de navigation

        this.drawerLayout = findViewById(R.id.drawerLayout);

        this.drawerLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);

                }
            }
        });
    }
}
