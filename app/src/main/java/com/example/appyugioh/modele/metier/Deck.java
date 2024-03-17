package com.example.appyugioh.modele.metier;

import java.util.ArrayList;
import java.util.List;

public class Deck
{
    protected List<CarteYuGiOh> listeCarteYuGiOh = new ArrayList<>();
    protected String nom;


    public List<CarteYuGiOh> getListeCarteYuGiOh() {
        return listeCarteYuGiOh;
    }

    public void setListeCarteYuGiOh(List<CarteYuGiOh> listeCarteYuGiOh) {
        this.listeCarteYuGiOh = listeCarteYuGiOh;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
