package com.example.appyugioh.modele.metier;

import android.util.Log;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nom: ").append(nom).append("\n");
        sb.append("Type: ").append(type).append("\n");
        sb.append("Type de cadre: ").append(typeFrame).append("\n");
        sb.append("Description: ").append(desc).append("\n");
        sb.append("Race: ").append(race).append("\n");
        sb.append("Archétype: ").append(archetype != null ? archetype : "N/A").append("\n");
        sb.append("Liste d'éditions: ").append(listeEdition).append("\n");
        sb.append("Lien de l'image: ").append(lienImage).append("\n");
        return sb.toString();
    }

    public boolean matchesFilter(String option, String filterValue) {
        String lowercaseType = type.toLowerCase(Locale.getDefault());
        String lowercaseRace = race.toLowerCase(Locale.getDefault());
        String lowercaseFilterValue = filterValue.toLowerCase(Locale.getDefault());
        switch (option) {
            case "type":

                switch (lowercaseFilterValue) {
                    case "monstre":
                        return lowercaseType.contains("monster");
                    case "magie":
                        return lowercaseType.contains("spell");
                    case "piège":
                        return lowercaseType.contains("trap");
                    default:
                        return lowercaseType.contains(lowercaseFilterValue);
                }
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
            case "spell":
                switch (lowercaseFilterValue) {
                    case "normale":
                        return lowercaseRace.contains("normal");
                    case "rituel":
                        return lowercaseRace.contains("ritual");
                    case "terrain":
                        return lowercaseRace.contains("field");
                    case "equipement":
                        return lowercaseRace.contains("equip");
                    case "continue":
                        return lowercaseRace.contains("continuous");
                    case "rapide":
                        return lowercaseRace.contains("quick-play");
                    default:
                        return lowercaseRace.contains(filterValue.toLowerCase(Locale.getDefault()));
                }
            case "trap":
                switch (lowercaseFilterValue) {
                    case "normale":
                        return lowercaseRace.contains("normal");
                    case "continue":
                        return lowercaseRace.contains("continuous");
                    case "contre":
                        return lowercaseRace.contains("counter");
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

}
