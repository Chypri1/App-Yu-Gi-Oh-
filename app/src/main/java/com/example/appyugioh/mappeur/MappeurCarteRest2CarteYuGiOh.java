package com.example.appyugioh.mappeur;

import com.example.appyugioh.metier.CarteYuGiOh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MappeurCarteRest2CarteYuGiOh {

    public List<CarteYuGiOh> mapperListeCarteRest2ListeCarteYuGiOh(JSONArray listeCarteRest) throws JSONException {
        List<CarteYuGiOh> listeCarteYuGiOh = new ArrayList();

        for (int i=0;i<listeCarteRest.length();i++) {
            CarteYuGiOh carteYuGiOh = new CarteYuGiOh();
            carteYuGiOh = this.mapperCarteRest2CarteYuGiOh(listeCarteRest.getJSONObject(i));
            listeCarteYuGiOh.add(carteYuGiOh);
        }

        return listeCarteYuGiOh;
    }

    public CarteYuGiOh mapperCarteRest2CarteYuGiOh(JSONObject carteRest) throws JSONException {
        CarteYuGiOh carteYuGiOh = new CarteYuGiOh();

        carteYuGiOh.setArchetype( carteRest.getString("archetype"));
        carteYuGiOh.setAttaque( carteRest.getInt("atk"));
        carteYuGiOh.setAttribut( carteRest.getString("attribute"));
        carteYuGiOh.setDefense( carteRest.getInt("def"));
        carteYuGiOh.setDesc(carteRest.getString("desc"));
        carteYuGiOh.setNiveau(carteRest.getInt("level"));
        carteYuGiOh.setNom(carteRest.getString("name"));
        carteYuGiOh.setRace(carteRest.getString("race"));
        carteYuGiOh.setType(carteRest.getString("type"));
        // TODO: mapper les editions
        carteYuGiOh.setLienImage(carteRest.getJSONArray("card_images").getJSONObject(0).getString("image_url"));

        return carteYuGiOh;
    }
}
