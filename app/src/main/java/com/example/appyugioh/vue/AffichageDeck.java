package com.example.appyugioh.vue;

import android.app.Activity;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;

public class AffichageDeck extends Activity {

    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //this.drawerLayout=findViewById(R.id.drawerLayout);
        // Configuration du geste de balayage pour ouvrir le tiroir de navigation
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
