package com.example.appyugioh.modele.comportementFront;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appyugioh.modele.metier.CarteMonstre;
import com.example.appyugioh.modele.metier.CarteYuGiOh;
import com.example.appyugioh.modele.metier.Edition;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.modele.rest.CarteYuGiOhCallback;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComportementEnregistrementCarte {

    protected int REQUEST_CAMERA_PERMISSION = 101;

    protected int RETOUR_PRENDRE_PHOTO = 1;

    public void afficherConfirmationEnregistrementCarte(Activity activity) {
        Snackbar.make(activity.findViewById(android.R.id.content), "Carte enregistrée avec succès", Snackbar.LENGTH_SHORT).show();
    }

    public void afficherNonEnregistrementCarte(Activity activity) {
        Snackbar.make(activity.findViewById(android.R.id.content), "Carte non enregistrée", Snackbar.LENGTH_SHORT).show();
    }


    public void enregistrementCarte(EditText nomCarte, EditText nomEdition, ImageView imageCam, Activity activity) throws JSONException {
        String nomCarteText = nomCarte.getText().toString();
        String nomEditionText = nomEdition.getText().toString();

        AccesExterneRest accesExterneRest = new AccesExterneRest();
        // Création de la tâche Callable pour effectuer la requête
        accesExterneRest.appRestExact(nomCarteText, new CarteYuGiOhCallback() {
            @Override
            public void onCarteYuGiOhReceived(List<CarteYuGiOh> cartes) {
                // Le traitement des résultats de la requête peut être effectué ici
                if (!nomCarteText.isEmpty() && !nomEditionText.isEmpty() && cartes.size() == 1) {
                    // Continuer le traitement et l'enregistrement de la carte
                    // Charger l'image à partir de l'URL avec Picasso
                    Picasso.get().load(cartes.get(0).getLienImage()).into(imageCam, new Callback() {
                        @Override
                        public void onSuccess()  {
                            // L'image a été chargée avec succès, vous pouvez maintenant enregistrer la carte
                            try {
                                enregistrerCarte(nomCarteText, nomEditionText, cartes.get(0), imageCam, activity);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            // Gérer l'erreur de chargement de l'image avec Picasso
                            afficherNonEnregistrementCarte(activity);
                        }
                    });
                } else {
                    afficherNonEnregistrementCarte(activity);
                }
            }
        });
    }

    private void enregistrerCarte(String nomCarteText, String nomEditionText, CarteYuGiOh carte, ImageView imageCam, Activity activity) throws JSONException {
        // Enregistrer l'image dans le stockage de l'appareil
        String imageSavedPath = saveImageToGallery(carte.getLienImage(), nomCarteText, activity);
        // Vérifier si l'enregistrement de l'image a réussi
        if (imageSavedPath != null) {
            // Créer un objet JSON avec le nom de la carte, l'édition et l'emplacement de l'image
            JSONObject carteJson = new JSONObject();
            // Ajouter les informations de la carte à l'objet JSON représentant la carte
            // Vous devez remplacer ces valeurs par les propriétés réelles de votre objet CarteYuGiOh
            carteJson.put("name", carte.getNom());
            carteJson.put("lienImage", imageSavedPath);
            carteJson.put("desc", carte.getDesc());
            carteJson.put("type", carte.getType());
            carteJson.put("frameType", carte.getTypeFrame());
            carteJson.put("race", carte.getRace());
            if (carte instanceof CarteMonstre) {
                carteJson.put("atk", ((CarteMonstre) carte).getAttaque());
                carteJson.put("def", ((CarteMonstre) carte).getDefense());
                carteJson.put("attribute", ((CarteMonstre) carte).getAttribut());
                carteJson.put("level", ((CarteMonstre) carte).getNiveau());
            }
            JSONArray listeEdition = new JSONArray();
            for (Edition edition : carte.getListeEdition()) {
                JSONObject editionJson = new JSONObject();
                editionJson.put("nom", edition.getNom());
                editionJson.put("code", edition.getCode());
                editionJson.put("rarete", edition.getRarete());
                editionJson.put("prix", edition.getPrix());
                listeEdition.put(editionJson);
            }
            // Ajouter la liste des éditions à l'objet JSON représentant la carte
            carteJson.put("editionCarte", listeEdition);
            // Enregistrer l'objet JSON dans un fichier
            String jsonFilePath = saveJSONObjectToFile(carteJson, activity);
            if (jsonFilePath != null) {
                afficherConfirmationEnregistrementCarte(activity);
            } else {
                afficherNonEnregistrementCarte(activity);
            }
        } else {
            afficherNonEnregistrementCarte(activity);
        }
    }

    // Méthode pour enregistrer un objet JSON dans un fichier
    public String saveJSONObjectToFile(JSONObject jsonObject, Activity activity) {
        try {
            // Créer un fichier dans le répertoire des fichiers de l'application
            File jsonFile = new File(activity.getFilesDir(), "carte.json");
            JSONArray jsonArray;
            String jsonString;

            // Vérifier si le fichier JSON existe déjà
            if (jsonFile.exists()) {
                // Lire le contenu du fichier JSON existant
                FileInputStream fis = new FileInputStream(jsonFile);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                // Convertir la chaîne JSON en un tableau JSON
                jsonString = stringBuilder.toString();
                jsonArray = new JSONArray(jsonString);
            } else {
                // Créer un nouveau tableau JSON
                jsonArray = new JSONArray();
            }

            // Ajouter le nouvel objet JSON au tableau JSON
            jsonArray.put(jsonObject);

            // Écrire le tableau JSON dans le fichier
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString(4)); // Indentation de 4 espaces pour une meilleure lisibilité
            fileWriter.close();

            return jsonFile.getAbsolutePath();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Gérer l'exception si l'enregistrement de l'objet JSON dans le fichier échoue
            return null;
        }
    }




    private void prendreUnePhoto(Activity activity) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Récupérer l'ID de la caméra frontale
        String frontCameraId = null;
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                    // Caméra frontale trouvée
                    frontCameraId = cameraId;
                    String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File photoDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File photoFile = File.createTempFile("photo" + time, ".jpg", photoDir);
                    //enregistrer le chemin
                    break;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (frontCameraId != null) {
            activity.startActivityForResult(intent, RETOUR_PRENDRE_PHOTO);
        } else {
            Toast.makeText(activity, "Aucune application de caméra disponible", Toast.LENGTH_SHORT).show();
            redirigerVersParametresCamera(activity);
        }
    }


    private void redirigerVersParametresCamera(Activity activity) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }


    public void ouvrirCamera(Activity activity) {
        // Intent pour accéder à la caméra
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        prendreUnePhoto(activity);
    }


    public void demanderPermissionCamera(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            ouvrirCamera(activity);
        }
    }

    private String saveImageToGallery(String imageUrl, String imageName, Activity activity) {
        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // Vérifier si le stockage externe est disponible en écriture
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            // Définir le répertoire de destination pour enregistrer l'image
                            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "VotreDossier");
                            // Créer le répertoire s'il n'existe pas
                            if (!directory.exists()) {
                                directory.mkdirs();
                            }
                            // Enregistrer l'image dans le répertoire
                            File imageFile = new File(directory, imageName + ".jpg");
                            try {
                                // Ajouter l'image à la galerie
                                MediaStore.Images.Media.insertImage(activity.getContentResolver(), imageFile.getAbsolutePath(), imageName, "Description de l'image");
                                // Afficher un message de confirmation
                                afficherConfirmationEnregistrementCarte(activity);
                            } catch (IOException e) {
                                e.printStackTrace();
                                // Gérer l'exception si l'enregistrement de l'image échoue
                                afficherNonEnregistrementCarte(activity);
                            }
                        } else {
                            // Gérer le cas où le stockage externe n'est pas disponible
                            afficherNonEnregistrementCarte(activity);
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        // Gérer l'échec du chargement de l'image
                        afficherNonEnregistrementCarte(activity);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        return imageUrl;
    }


}
