package com.example.appyugioh.modele.metier;

import java.io.Serializable;
import java.util.List;

public class CarteYuGiOh implements Serializable {
    protected String nom;
    protected String type;

    protected String typeFrame; // si type = monstre a effet -> effet, si skill card -> skill, si trap card-> trap, si spell card -> spell
    protected String desc;
    protected String race;
    protected String archetype; // il y a un nom ou alors c'est null
    protected List<Edition> listeEdition; // commun a tous
    protected String lienImage;

    public String getTypeFrame() {
        return typeFrame;
    }

    public void setTypeFrame(String typeFrame) {
        this.typeFrame = typeFrame;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getNom() {
        return nom;
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

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

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
}
