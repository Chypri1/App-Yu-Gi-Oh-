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

public class MappeurCarteJson2CarteYuGiOh {

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
            carteYuGiOh.setNom(carteRest.getString("name"));
            carteYuGiOh.setType(carteRest.getString("type"));
            carteYuGiOh.setTypeFrame(carteRest.getString("frameType"));
            if (carteRest.has("archetype")) {
                carteYuGiOh.setArchetype(carteRest.getString("archetype"));
            }
            carteYuGiOh.setDesc(carteRest.getString("desc"));
            carteYuGiOh.setRace(carteRest.getString("race"));
            if(carteRest.has("editionCarte")) {
                JSONArray cardSetArray = carteRest.getJSONArray("editionCarte");
                List<Edition> listeEdition = new ArrayList<>();
                for (int i = 0; i < cardSetArray.length(); i++) {
                    JSONObject editionObj = cardSetArray.getJSONObject(i);
                    Edition edition = new Edition(editionObj.getString("nom"), editionObj.getString("code"), editionObj.getString("rarete"), Float.parseFloat(editionObj.getString("prix")));
                    listeEdition.add(edition);
                }
                carteYuGiOh.setListeEdition(listeEdition);
            }
            carteYuGiOh.setLienImage(carteRest.getString("lienImage"));
            return carteYuGiOh;
        }

}
