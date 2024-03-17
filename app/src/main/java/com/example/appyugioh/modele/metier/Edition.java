package com.example.appyugioh.modele.metier;

import kotlin.collections.FloatIterator;

public class Edition
{

    protected String nom;

    protected String code;

    protected String rarete;

    protected Float prix;



    public Edition(String nom, String code, String rarete, Float prix){
        this.nom=nom;
        this.code=code;
        this.rarete=rarete;
        this.prix=prix;
    }
    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getRarete() {
        return rarete;
    }

    public void setRarete(String rarete) {
        this.rarete = rarete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
