package com.example.appyugioh.modele.metier;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class CarteLink extends CarteYuGiOh implements Serializable {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // Appelle la méthode toString de la classe parente (CarteYuGiOh)
        sb.append("Attaque: ").append(attaque).append("\n");
        sb.append("Attribut: ").append(attribut).append("\n");
        sb.append("Nombre de liens: ").append(nombreLien).append("\n");
        sb.append("Emplacements des marqueurs: ").append(listeEmplacementMarqueurs).append("\n");
        return sb.toString();
    }

    public boolean matchesFilter(String option, String filterValue) {
        String lowercaseType = type.toLowerCase(Locale.getDefault());
        String lowercaseRace = race.toLowerCase(Locale.getDefault());
        String lowercaseFilterValue = filterValue.toLowerCase(Locale.getDefault());
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
            case "link_class":
                return String.valueOf(nombreLien).contains(lowercaseFilterValue);
            case "link_mark":
                for(String s : listeEmplacementMarqueurs){
                    if(s.toLowerCase().contains(lowercaseFilterValue)){
                        return true;
                    }
                }
                return false;
            case "attribut":
                return attribut.toLowerCase().contains(lowercaseFilterValue);
            default:
                return false;
        }
    }
}
