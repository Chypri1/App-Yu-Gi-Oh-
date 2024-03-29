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
        if(carteRest.getString("type").contains("Monster"))
        {

            if(carteRest.getString("frameType").contentEquals("Link"))
            {
                carteYuGiOh = new CarteLink();
                ((CarteLink) carteYuGiOh).setAttaque(carteRest.optInt("atk", 0));
                ((CarteLink) carteYuGiOh).setAttribut(carteRest.getString("attribute"));
                ((CarteLink) carteYuGiOh).setNombreLien(carteRest.optInt("level", 0));
                // ((CarteLink) carteYuGiOh).setListeEmplacementMarqueurs((List<String>) carteRest.getJSONArray("linkmarkers"));

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
        if(carteRest.has("name"))
            carteYuGiOh.setNom(carteRest.getString("name"));
        if(carteRest.has("type"))
            carteYuGiOh.setType(carteRest.getString("type"));
        if(carteRest.has("frameType"))
            carteYuGiOh.setTypeFrame(carteRest.getString("frameType"));
        if (carteRest.has("archetype")) {
            carteYuGiOh.setArchetype(carteRest.getString("archetype"));
        }
        if(carteRest.has("desc"))
            carteYuGiOh.setDesc(carteRest.getString("desc"));
        if(carteRest.has("race"))
            carteYuGiOh.setRace(carteRest.getString("race"));
        if (carteRest.has("card_sets")) {
            JSONArray cardSetArray = carteRest.getJSONArray("card_sets");
            List<Edition> listeEdition = new ArrayList<>();
            for (int i = 0; i < cardSetArray.length(); i++) {
                JSONObject editionObj = cardSetArray.getJSONObject(i);
                Edition edition = new Edition(editionObj.getString("set_name"), editionObj.getString("set_code"), editionObj.getString("set_rarity"), Float.parseFloat(editionObj.getString("set_price")));
                listeEdition.add(edition);
            }
            carteYuGiOh.setListeEdition(listeEdition);
        }
        // Gérer les images
        JSONArray cardImages = carteRest.getJSONArray("card_images");
        if (cardImages.length() > 0) {
            carteYuGiOh.setLienImage(cardImages.getJSONObject(0).getString("image_url"));
        }

        return carteYuGiOh;
    }

}
