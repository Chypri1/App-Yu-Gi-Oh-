package com.example.appyugioh.vue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.appyugioh.R;
import com.example.appyugioh.controlleur.ControlleurRechercheCarte;

import java.util.ArrayList;
import java.util.List;

public class Filtre{
    private List<CheckBox> checkBoxCardType;
    private List<CheckBox> checkBoxLevel;
    private List<CheckBox> checkBoxMonsterType;
    private List<CheckBox> checkBoxSpellType;
    private List<CheckBox> checkBoxTrapType;
    private List<CheckBox> checkBoxLinkClass;
    private List<CheckBox> checkBoxLinkMark;
    private List<CheckBox> checkBoxAttribut;
    private List<CheckBox> checkBoxRace;
    private Button btn_reset;
    private Button btn_ok;
    private Button btn_retour;
    private LinearLayout layoutCardType;
    private LinearLayout layoutLevel;
    private LinearLayout layoutMonsterType;
    private LinearLayout layoutSpellType;
    private LinearLayout layoutTrapType;
    private LinearLayout layoutLinkClass;
    private LinearLayout layoutLinkMark;
    private LinearLayout layoutAttribut;
    private LinearLayout layoutRace;



    public List<CheckBox> getCheckBoxCardType(){return this.checkBoxCardType;}

    public void setCheckBoxCardType(List<CheckBox> checkBoxCardType){this.checkBoxCardType=checkBoxCardType;}

    public CheckBox getCheckBoxCardType(String label) {
        for (CheckBox checkBox : checkBoxCardType) {
            if (checkBox.getText().toString().equals(label)) {
                return checkBox;
            }
        }
        return null;
    }

    public List<CheckBox> getCheckBoxLevel(){return this.checkBoxLevel;}

    public void setCheckBoxLevel(List<CheckBox> checkBoxCardLevel){this.checkBoxLevel=checkBoxCardLevel;}

    public List<CheckBox> getCheckBoxMonsterType(){return this.checkBoxMonsterType;}

    public void setCheckBoxMonsterType(List<CheckBox> checkBoxMonsterType){this.checkBoxMonsterType=checkBoxMonsterType;}

    public List<CheckBox> getCheckBoxSpellType(){return this.checkBoxSpellType;}

    public void setCheckBoxSpellType(List<CheckBox> checkBoxSpellType){this.checkBoxSpellType=checkBoxSpellType;}

    public List<CheckBox> getCheckBoxTrapType(){return this.checkBoxTrapType;}

    public void setCheckBoxTrapType(List<CheckBox> checkBoxTrapType){this.checkBoxTrapType=checkBoxTrapType;}


    public List<CheckBox> getCheckBoxLinkClass(){return this.checkBoxLinkClass;}

    public void setCheckBoxLinkClass(List<CheckBox> checkBoxLinkClass){this.checkBoxLinkClass=checkBoxLinkClass;}


    public List<CheckBox> getCheckBoxLinkMark(){return this.checkBoxLinkMark;}

    public void setCheckBoxLinkMark(List<CheckBox> checkBoxLinkMark){this.checkBoxLinkMark=checkBoxLinkMark;}

    public List<CheckBox> getCheckBoxAttribut(){return this.checkBoxAttribut;}

    public void setCheckBoxAttribut(List<CheckBox> checkBoxAttribut){this.checkBoxAttribut=checkBoxAttribut;}

    public List<CheckBox> getCheckBoxRace(){return this.checkBoxRace;}

    public void setCheckBoxRace(List<CheckBox> checkBoxRace){this.checkBoxRace=checkBoxRace;}
    public Button getBtnReset(){return this.btn_reset;}

    public void setBtnReset(Button btn_reset){this.btn_reset=btn_reset;}

    public Button getBtnOk(){return this.btn_ok;}

    public void setBtnOk(Button btn_ok){this.btn_ok=btn_ok;}

    public Button getBtnRetour(){return this.btn_retour;}

    public void setBtnRetour(Button btn_retour){this.btn_retour=btn_retour;}

    public LinearLayout getLayoutCardType(){return this.layoutCardType;}

    public void setLayoutCardType(LinearLayout layoutCardType){this.layoutCardType=layoutCardType;}

    public LinearLayout getLayoutLevel(){return this.layoutLevel;}

    public void setLayoutLevel(LinearLayout layoutLevel){this.layoutLevel=layoutLevel;}

    public LinearLayout getLayoutMonsterType(){return this.layoutMonsterType;}

    public void setLayoutMonsterType(LinearLayout layoutMonsterType){this.layoutMonsterType=layoutMonsterType;}

    public LinearLayout getLayoutSpellType(){return this.layoutSpellType;}

    public void setLayoutSpellType(LinearLayout layoutSpellType){this.layoutSpellType=layoutSpellType;}

    public LinearLayout getLayoutTrapType(){return this.layoutTrapType;}

    public void setLayoutTrapType(LinearLayout layoutTrapType){this.layoutTrapType=layoutTrapType;}

    public LinearLayout getLayoutLinkClass(){return this.layoutLinkClass;}

    public void setLayoutLinkClass(LinearLayout layoutLinkClass){this.layoutLinkClass=layoutLinkClass;}

    public LinearLayout getLayoutLinkMark(){return this.layoutLinkMark;}

    public void setLayoutLinkMark(LinearLayout layoutLinkMark){this.layoutLinkMark=layoutLinkMark;}

    public LinearLayout getLayoutAttribut() {
        return layoutAttribut;
    }

    public void setLayoutAttribut(LinearLayout layoutAttribut) {
        this.layoutAttribut = layoutAttribut;
    }

    public LinearLayout getLayoutRace() {
        return layoutRace;
    }

    public void setLayoutRace(LinearLayout layoutRace) {
        this.layoutRace = layoutRace;
    }
}
