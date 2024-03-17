package com.example.appyugioh.modele.metier;

import java.io.Serializable;
import java.util.List;

public class CarteMonstre extends CarteYuGiOh implements Serializable {
    protected int attaque;

    protected int defense;

    protected int niveau;

    protected String attribut;

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getAttribut() {
        return attribut;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }
}
