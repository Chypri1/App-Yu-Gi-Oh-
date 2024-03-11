package com.example.appyugioh.modele;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appyugioh.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComportementEnregistrementCarte {

    protected int REQUEST_CAMERA_PERMISSION = 101;

    protected int RETOUR_PRENDRE_PHOTO = 1;

    public void afficherConfirmationEnregistrementCarte(Activity activity) {
        Snackbar.make(activity.findViewById(android.R.id.content), "Carte enregistrée avec succès", Snackbar.LENGTH_SHORT).show();
    }


    public void enregistrementCarte(EditText nomCarte, EditText nomEdition, ImageView imageCam, Activity activity) throws JSONException {
        String nomCarteText = nomCarte.getText().toString();
        String nomEditionText = nomEdition.getText().toString();

        // Vérifier si le nom de la carte et l'édition sont vides ou égaux aux valeurs par défaut
        if (!TextUtils.isEmpty(nomCarteText) && !nomCarteText.equals(activity.getString(R.string.nom_carte)) &&
                !TextUtils.isEmpty(nomEditionText) && !nomEditionText.equals(activity.getString(R.string.edition))) {

            // Vérifier si l'imageView n'est pas vide
            if (imageCam.getDrawable() != null) {
                // Récupérer le Drawable de l'ImageView
                Drawable drawable = imageCam.getDrawable();
                // Vérifier si le Drawable est une instance de BitmapDrawable
                if (drawable instanceof BitmapDrawable) {
                    // Convertir le Drawable en Bitmap
                    Bitmap image = ((BitmapDrawable) drawable).getBitmap();
                    // Enregistrer l'image dans le stockage de l'appareil
                    String imageSavedPath = saveImageToGallery(image, nomCarteText,activity);
                    // Vérifier si l'enregistrement de l'image a réussi
                    if (imageSavedPath != null) {
                        // Créer un objet JSON avec le nom de la carte, l'édition et l'emplacement de l'image
                        // TODO: enregistrer d'autres infos pour savoir si c'est une carte prise ne photo ou non
                        JSONObject carteJSON = new JSONObject();
                        carteJSON.put("nomCarte", nomCarteText);
                        carteJSON.put("nomEdition", nomEditionText);
                        carteJSON.put("imagePath", imageSavedPath);

                        // Enregistrer l'objet JSON dans un fichier
                        String jsonFilePath = saveJSONObjectToFile(carteJSON,activity);
                        if (jsonFilePath != null) {
                            // L'objet JSON a été enregistré avec succès dans le fichier
                            //TODO : pop up pour dire si oui c'est enregistré
                        } else {
                            // Gérer le cas où l'enregistrement de l'objet JSON a échoué
                        }
                    } else {
                        // Gérer le cas où l'enregistrement de l'image a échoué
                    }
                }
            }
        } else {
            // Gérer le cas où le nom de la carte ou l'édition est vide ou égal à la valeur par défaut
        }
    }

    // Méthode pour enregistrer un objet JSON dans un fichier
    private String saveJSONObjectToFile(JSONObject jsonObject,Activity activity) {
        try {
            // Créer un fichier dans le répertoire des fichiers de l'application
            File jsonFile = new File(activity.getFilesDir(), "carte.json");
            FileWriter fileWriter = new FileWriter(jsonFile);
            // Écrire l'objet JSON dans le fichier
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
            return jsonFile.getAbsolutePath();
        } catch (IOException e) {
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

    private String saveImageToGallery(Bitmap image, String imageName, Activity activity) {
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
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                // Ajouter l'image à la galerie
                MediaStore.Images.Media.insertImage(activity.getContentResolver(), imageFile.getAbsolutePath(), imageName, "Description de l'image");
                // Retourner le chemin de l'image enregistrée
                return imageFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'exception si l'enregistrement de l'image échoue
                return null;
            }
        } else {
            // Gérer le cas où le stockage externe n'est pas disponible
            return null;
        }
    }

}
