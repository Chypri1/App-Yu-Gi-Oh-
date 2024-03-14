package com.example.appyugioh.modele.comportementFront;

import android.media.MediaParser;
import android.media.MediaPlayer;

public class ComportementMainActivity {

    public void toggleMusic(MediaPlayer mediaPlayer) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // Mettre en pause si déjà en cours de lecture
        } else {
            mediaPlayer.start(); // Démarrer la lecture
        }
    }
}
