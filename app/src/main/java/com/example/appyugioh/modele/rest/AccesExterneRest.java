package com.example.appyugioh.modele.rest;

import com.example.appyugioh.modele.mappeur.MappeurCarteRest2CarteYuGiOh;
import com.example.appyugioh.modele.metier.CarteYuGiOh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccesExterneRest {

    private static final String URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php";


    protected MappeurCarteRest2CarteYuGiOh mappeurCarteRest2CarteYuGiOh = new MappeurCarteRest2CarteYuGiOh();
    public List<CarteYuGiOh> appeRest(String nomCarte) {

        List<CarteYuGiOh> listeCarteYuGiOh = new ArrayList();
        StringBuffer response = new StringBuffer();
        String urlNomCarte = URL + "?name=" + nomCarte;

        // TODO: faire appel rest

        // resultat appel Rest
        String resultatRest = """
                {
                  "data": [
                    {
                      "id": 63977008,
                      "name": "Robot Synchronique",
                      "type": "Tuner Monster",
                      "frameType": "effect",
                      "desc": "Lorsque cette carte est Invoqu\\u00e9e Normalement : vous pouvez cibler 1 monstre de max. Niveau 2 dans votre Cimeti\\u00e8re ; Invoquez Sp\\u00e9cialement la cible en Position de D\\u00e9fense, mais annulez ses effets.",
                      "atk": 1300,
                      "def": 500,
                      "level": 3,
                      "race": "Warrior",
                      "attribute": "DARK",
                      "name_en": "Junk Synchron",
                      "archetype": "Junk",
                      "ygoprodeck_url": "https://ygoprodeck.com/card/junk-synchron-5399",
                      "card_sets": [
                        {
                          "set_name": "Duel Terminal 3",
                          "set_code": "DT03-EN003",
                          "set_rarity": "Duel Terminal Rare Parallel Rare",
                          "set_rarity_code": "(DRPR)",
                          "set_price": "10.8"
                        },
                        {
                          "set_name": "Duelist League 3 participation cards",
                          "set_code": "DL12-EN010",
                          "set_rarity": "Rare",
                          "set_rarity_code": "(R)",
                          "set_price": "9.39"
                        },
                        {
                          "set_name": "Duelist Pack Collection Tin 2010",
                          "set_code": "DPCT-ENY01",
                          "set_rarity": "Ultra Rare",
                          "set_rarity_code": "(UR)",
                          "set_price": "6.98"
                        },
                        {
                          "set_name": "Duelist Pack: Yusei",
                          "set_code": "DP08-EN001",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "2.08"
                        },
                        {
                          "set_name": "Duelist Saga",
                          "set_code": "DUSA-EN074",
                          "set_rarity": "Ultra Rare",
                          "set_rarity_code": "(UR)",
                          "set_price": "4.89"
                        },
                        {
                          "set_name": "Legendary Collection 5D's Mega Pack",
                          "set_code": "LC5D-EN002",
                          "set_rarity": "Secret Rare",
                          "set_rarity_code": "(ScR)",
                          "set_price": "14.46"
                        },
                        {
                          "set_name": "OTS Tournament Pack 17",
                          "set_code": "OP17-EN015",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "1.79"
                        },
                        {
                          "set_name": "OTS Tournament Pack 17 (POR)",
                          "set_code": "OP17-PT015",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "0"
                        },
                        {
                          "set_name": "Starter Deck: Duelist Toolbox",
                          "set_code": "5DS3-EN010",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "3"
                        },
                        {
                          "set_name": "Starter Deck: Yu-Gi-Oh! 5D's",
                          "set_code": "5DS1-EN011",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "2.56"
                        },
                        {
                          "set_name": "Starter Deck: Yu-Gi-Oh! 5D's 2009",
                          "set_code": "5DS2-EN014",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "3.32"
                        },
                        {
                          "set_name": "Synchron Extreme Structure Deck",
                          "set_code": "SDSE-EN004",
                          "set_rarity": "Common",
                          "set_rarity_code": "(C)",
                          "set_price": "2.02"
                        }
                      ],
                      "card_images": [
                        {
                          "id": 63977008,
                          "image_url": "https://images.ygoprodeck.com/images/cards/63977008.jpg",
                          "image_url_small": "https://images.ygoprodeck.com/images/cards_small/63977008.jpg",
                          "image_url_cropped": "https://images.ygoprodeck.com/images/cards_cropped/63977008.jpg"
                        }
                      ],
                      "card_prices": [
                        {
                          "cardmarket_price": "0.12",
                          "tcgplayer_price": "0.66",
                          "ebay_price": "9.00",
                          "amazon_price": "7.99",
                          "coolstuffinc_price": "0.49"
                        }
                      ]
                    }
                  ]
                }""";


        try {
            // Convertir la réponse en objet JSON
            JSONObject jsonResponse = new JSONObject(resultatRest);

            // Récupérer le tableau de cartes
            List<JSONArray> cardsArray = List.of(jsonResponse.getJSONArray("data"));

            listeCarteYuGiOh = mappeurCarteRest2CarteYuGiOh.mapperListeCarteRest2ListeCarteYuGiOh(cardsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listeCarteYuGiOh;
    }
}
