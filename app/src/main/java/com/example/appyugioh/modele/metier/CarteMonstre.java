package com.example.appyugioh.modele.metier;

import android.util.Log;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // Appelle la méthode toString de la classe parente (CarteYuGiOh)
        sb.append("Attaque: ").append(attaque).append("\n");
        sb.append("Défense: ").append(defense).append("\n");
        sb.append("Niveau: ").append(niveau).append("\n");
        sb.append("Attribut: ").append(attribut).append("\n");
        return sb.toString();
    }

    public boolean matchesFilter(String option, String filterValue) {
        String lowercaseType = type.toLowerCase(Locale.getDefault());
        String lowercaseRace = race.toLowerCase(Locale.getDefault());
        String lowercaseFilterValue = filterValue.toLowerCase();
        switch (option) {
            case "type":
                if(lowercaseFilterValue.equals("monstre")) {
                    return lowercaseType.contains("monster");
                }else return false;
            case "monstre":
                switch (lowercaseFilterValue) {
                    case "effet":
                        return lowercaseType.contains("effect");
                    case "rituel":
                        return lowercaseType.contains("ritual");
                    case "pendule":
                        return lowercaseType.contains("pendulum");
                    default:
                        return lowercaseType.contains(filterValue.toLowerCase(Locale.getDefault()));
                }
            case "level":
                return String.valueOf(niveau).contains(lowercaseFilterValue);
            case "attribut":
                return attribut.toLowerCase().contains(lowercaseFilterValue);
            default:
                return false;
        }
    }
}
