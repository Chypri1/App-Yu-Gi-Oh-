package com.example.appyugioh.modele.mappeur;

import com.example.appyugioh.modele.metier.CarteLink;
import com.example.appyugioh.modele.metier.CarteMonstre;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.metier.Edition;

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
        CarteYuGiOh carteYuGiOh;
        if(carteRest.getString("type").contains("monster"))
        {

            if(carteRest.getString("frametype").contentEquals("link"))
            {
                carteYuGiOh = new CarteLink();
                ((CarteLink) carteYuGiOh).setAttaque(carteRest.optInt("atk", 0));
                ((CarteLink) carteYuGiOh).setAttribut(carteRest.getString("attribute"));
                ((CarteLink) carteYuGiOh).setNombreLien(carteRest.optInt("level", 0));
                ((CarteLink) carteYuGiOh).setListeEmplacementMarqueurs((List<String>) carteRest.getJSONArray("linkmarkers"));

            }
            else
            {
                carteYuGiOh = new CarteMonstre();
                ((CarteMonstre) carteYuGiOh).setAttaque(carteRest.optInt("atk", 0));
                ((CarteMonstre) carteYuGiOh).setAttribut(carteRest.getString("attribute"));
                ((CarteMonstre) carteYuGiOh).setDefense(carteRest.optInt("def", 0));
                ((CarteMonstre) carteYuGiOh).setNiveau(carteRest.optInt("level", 0));
            }

        }
        else
        {
            carteYuGiOh = new CarteYuGiOh();
        }
        carteYuGiOh.setNom(carteRest.getString("name"));
        carteYuGiOh.setType(carteRest.getString("type"));
        carteYuGiOh.setTypeFrame(carteRest.getString("typeframe"));
        carteYuGiOh.setArchetype(carteRest.getString("archetype"));
        carteYuGiOh.setDesc(carteRest.getString("desc"));
        carteYuGiOh.setRace(carteRest.getString("race"));
        carteYuGiOh.setListeEdition((List<Edition>) carteRest.getJSONArray("card_set"));
        // Gérer les images
        JSONArray cardImages = carteRest.getJSONArray("card_images");
        if (cardImages.length() > 0) {
            carteYuGiOh.setLienImage(cardImages.getJSONObject(0).getString("image_url"));
        }

        return carteYuGiOh;
    }

}
