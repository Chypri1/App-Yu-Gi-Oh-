package com.example.appyugioh.modele.rest;

import com.example.appyugioh.modele.metier.CarteYuGiOh;

import java.util.List;

public interface CarteYuGiOhCallback {
    void onCarteYuGiOhReceived(List<CarteYuGiOh> cartes);
}
