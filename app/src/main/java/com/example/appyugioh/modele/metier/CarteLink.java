package com.example.appyugioh.modele.metier;

import java.util.List;

public class CarteLink extends CarteYuGiOh{

    protected int attaque;

    protected String attribut;

    protected int nombreLien;

    protected List<String> listeEmplacementMarqueurs;

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public String getAttribut() {
        return attribut;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }

    public int getNombreLien() {
        return nombreLien;
    }

    public void setNombreLien(int nombreLien) {
        this.nombreLien = nombreLien;
    }

    public List<String> getListeEmplacementMarqueurs() {
        return listeEmplacementMarqueurs;
    }

    public void setListeEmplacementMarqueurs(List<String> listeEmplacementMarqueurs) {
        this.listeEmplacementMarqueurs = listeEmplacementMarqueurs;
    }
}
