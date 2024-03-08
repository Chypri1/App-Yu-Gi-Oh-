package com.example.appyugioh.modele.metier;

import java.util.List;

public class CarteYuGiOh {

    protected String nom;

    protected String type;

    protected String desc;

    protected int attaque;

    protected int defense;

    protected int niveau;

    protected String race;

    protected String archetype;

    protected String attribut;

    protected List<Edition> listeEdition;

    protected String lienImage;

    public List<Edition> getListeEdition() {
        return listeEdition;
    }

    public void setListeEdition(List<Edition> listeEdition) {
        this.listeEdition = listeEdition;
    }

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }

    public String getNom() {
        return nom;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAttribut() {
        return attribut;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }

    @Override
    public String toString() {
        return "CarteYuGiOh{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", attaque=" + attaque +
                ", defense=" + defense +
                ", niveau=" + niveau +
                ", race='" + race + '\'' +
                ", archetype='" + archetype + '\'' +
                ", attribut='" + attribut + '\'' +
                ", listeEdition=" + listeEdition +
                ", lienImage='" + lienImage + '\'' +
                '}';
    }
}
