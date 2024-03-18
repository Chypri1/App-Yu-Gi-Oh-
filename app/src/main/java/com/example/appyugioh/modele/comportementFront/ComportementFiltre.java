package com.example.appyugioh.modele.comportementFront;

import android.util.Log;
import android.widget.CheckBox;

import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.vue.Filtre;

import java.util.ArrayList;
import java.util.List;

public class ComportementFiltre {
    private List<String> filterOptionsMonster;
    private List<String> filterOptionsSpell;
    private List<String> filterOptionsTrap;

    public ComportementFiltre() {
        // Initialisation des options de filtre
        filterOptionsMonster = new ArrayList<>();
        filterOptionsSpell = new ArrayList<>();
        filterOptionsTrap = new ArrayList<>();
    }
    public void filtre(Filtre filtre, AccesExterneRest accesExterneRest){
        filterOptionsMonster.clear();
        filterOptionsSpell.clear();
        filterOptionsTrap.clear();
        if(!isNoCheckBoxesChecked(filtre)) {
            List<CarteYuGiOh> listFiltree = getListByFilter(filtre,accesExterneRest);
                accesExterneRest.setListeFiltreCarteYuGiOh(listFiltree);
        }else{
            accesExterneRest.setListeFiltreCarteYuGiOh(accesExterneRest.getFinalListeCarteYuGiOh());
        }
    }

    private List<CarteYuGiOh> getListByFilter(Filtre filtre,AccesExterneRest accesExterneRest){
        List<CarteYuGiOh> filteredList = new ArrayList<>();
        if(isCheckBoxChecked(filtre.getCheckBoxCardType())){
            if(filtre.getCheckBoxCardType("monstre").isChecked()){
                filterOptionsMonster.add("type");
            }
            if(filtre.getCheckBoxCardType("magie").isChecked()){
                filterOptionsSpell.add("type");
            }
            if(filtre.getCheckBoxCardType("piège").isChecked()){
                filterOptionsTrap.add("type");
            }
        }
        if(isCheckBoxChecked(filtre.getCheckBoxMonsterType())){
            filterOptionsMonster.add("monstre");
        }
        if(isCheckBoxChecked(filtre.getCheckBoxSpellType())){
            filterOptionsSpell.add("spell");
        }
        if(isCheckBoxChecked(filtre.getCheckBoxTrapType())){
            filterOptionsTrap.add("trap");
        }
        if(isCheckBoxChecked(filtre.getCheckBoxLevel())){
            filterOptionsMonster.add("level");
        }
        if(isCheckBoxChecked(filtre.getCheckBoxLinkClass())){
            filterOptionsMonster.add("link_class");
        }
        if(isCheckBoxChecked(filtre.getCheckBoxLinkMark())){
            filterOptionsMonster.add("link_mark");
        }
        if(isCheckBoxChecked(filtre.getCheckBoxAttribut())){
            filterOptionsMonster.add("attribut");
        }
        for (CarteYuGiOh carte : accesExterneRest.getFinalListeCarteYuGiOh()) {
            Log.d("type carte",carte.getType());
            if(!filterOptionsMonster.isEmpty())
                if(carte.getType().toLowerCase().contains("monster"))
                    if (isCarteMatchesFilters(carte, filtre,filterOptionsMonster)) {
                        filteredList.add(carte);
                    }
            if(!filterOptionsSpell.isEmpty())
                if(carte.getType().toLowerCase().contains("spell"))
                    if (isCarteMatchesFilters(carte, filtre,filterOptionsSpell)) {
                        filteredList.add(carte);
                    }
            if(!filterOptionsTrap.isEmpty())
                if(carte.getType().toLowerCase().contains("trap"))
                    if (isCarteMatchesFilters(carte, filtre,filterOptionsTrap)) {
                        filteredList.add(carte);
                    }

        }
        return filteredList;
    }

    private boolean isCarteMatchesFilters(CarteYuGiOh carte, Filtre filtre,List<String>filterOptions) {
        for (String option : filterOptions) {
            if (!isCarteMatchesFilter(carte, filtre.getCheckBoxesForFilter(option), option)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCarteMatchesFilter(CarteYuGiOh carte, List<CheckBox> checkBoxes, String option) {
        boolean atLeastOneMatch = false; // Indique si au moins une case cochée correspond à la carte

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked() && carte.matchesFilter(option, checkBox.getText().toString())) {
                atLeastOneMatch = true; // Au moins une case cochée correspond à la carte pour cette option
            }
        }

        return atLeastOneMatch;
    }
    private boolean isNoCheckBoxesChecked(Filtre filtre) {
        List<CheckBox> allCheckBoxes = new ArrayList<>();
        allCheckBoxes.addAll(filtre.getCheckBoxCardType());
        allCheckBoxes.addAll(filtre.getCheckBoxSpellType());
        allCheckBoxes.addAll(filtre.getCheckBoxMonsterType());
        allCheckBoxes.addAll(filtre.getCheckBoxLevel());
        allCheckBoxes.addAll(filtre.getCheckBoxAttribut());
        allCheckBoxes.addAll(filtre.getCheckBoxTrapType());
        allCheckBoxes.addAll(filtre.getCheckBoxLinkClass());
        allCheckBoxes.addAll(filtre.getCheckBoxLinkMark());

        for (CheckBox checkBox : allCheckBoxes) {
            if (checkBox.isChecked()) {
                return false; // Au moins une case à cocher est cochée
            }
        }
        return true; // Aucune case à cocher n'est cochée
    }

    private boolean isCheckBoxChecked(List<CheckBox> checkBoxList){
        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public void reset(Filtre filtre) {
        for (CheckBox checkBox : filtre.getCheckBoxCardType()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxLevel()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxMonsterType()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxSpellType()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxTrapType()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxLinkClass()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxLinkMark()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxAttribut()) {
            checkBox.setChecked(false);
        }
        for (CheckBox checkBox : filtre.getCheckBoxRace()) {
            checkBox.setChecked(false);
        }
    }
}
