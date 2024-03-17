package com.example.appyugioh.modele.mappeur;

import com.example.appyugioh.modele.metier.CarteYuGiOh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MappeurCarteRest2CarteYuGiOh {

    public List<CarteYuGiOh> mapperListeCarteRest2ListeCarteYuGiOh(JSONArray response) throws JSONException {
        List<CarteYuGiOh> listeCarteYuGiOh = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject carteRest = response.getJSONObject(i);
            CarteYuGiOh carteYuGiOh = mapperCarteRest2CarteYuGiOh(carteRest);
            listeCarteYuGiOh.add(carteYuGiOh);
        }

        return listeCarteYuGiOh;
    }

    public CarteYuGiOh mapperCarteRest2CarteYuGiOh(JSONObject carteRest) throws JSONException {
        CarteYuGiOh carteYuGiOh = new CarteYuGiOh();
        carteYuGiOh.setType(carteRest.getString("type"));
        /*if(carteYuGiOh.getType().equals("Spell Card"))
        carteYuGiOh.setArchetype(carteRest.getString("archetype"));
        carteYuGiOh.setAttaque(carteRest.optInt("atk", 0));
        carteYuGiOh.setAttribut(carteRest.getString("attribute"));
        carteYuGiOh.setDefense(carteRest.optInt("def", 0));
        carteYuGiOh.setDesc(carteRest.getString("desc"));
        carteYuGiOh.setNiveau(carteRest.optInt("level", 0));
        carteYuGiOh.setNom(carteRest.getString("name"));
        carteYuGiOh.setRace(carteRest.getString("race"));
*/

        // Gérer les images
        JSONArray cardImages = carteRest.getJSONArray("card_images");
        if (cardImages.length() > 0) {
            carteYuGiOh.setLienImage(cardImages.getJSONObject(0).getString("image_url"));
        }

        return carteYuGiOh;
    }

}
