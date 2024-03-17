package com.example.appyugioh.modele.comportementFront;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.appyugioh.R;

public class ComportementRechercheCarte {
    public void masquerClavier(Activity activite) {
        View view = activite.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activite.getSystemService(activite.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
