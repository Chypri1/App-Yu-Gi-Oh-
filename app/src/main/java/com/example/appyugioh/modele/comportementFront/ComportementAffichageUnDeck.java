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
}
