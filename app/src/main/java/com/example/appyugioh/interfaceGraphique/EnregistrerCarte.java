package com.example.appyugioh.interfacegraphique;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.camera2.*;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.media.CameraPrewarmService;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.appyugioh.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnregistrerCarte extends Activity {

    private static final int RETOUR_PRENDRE_PHOTO = 1;
    protected EditText nomCarte;

    protected EditText nomEdition;

    protected LinearLayout layoutResultatCam;

    protected ImageView imageCam;

    protected Button boutonAccesCamera;

    protected Button boutonEnregistrementCarte;

    private String photoPath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrercarte);

        nomCarte = findViewById(R.id.nomCarte);

        nomEdition = findViewById(R.id.nomEdition);

        layoutResultatCam = findViewById(R.id.layoutResultatCam);

        imageCam = findViewById(R.id.imageCam);

        boutonAccesCamera = findViewById(R.id.boutonAccesCamera);

        boutonEnregistrementCarte = findViewById(R.id.boutonEnregistrementCarte);

        boutonAccesCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prendreUnePhoto();
                //imageCam.setVisibility(View.VISIBLE);
            }
        });

        boutonEnregistrementCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nomCarte.getText()!= null || nomCarte.getText().equals(R.string.nom_carte)){

                }
                if(nomEdition.getText()!= null || nomEdition.getText().equals(R.string.edition)) {

                }

                // Vérifier si l'imageView n'est pas vide
                if (imageCam.getDrawable() != null) {
                    // Récupérer le Drawable de l'ImageView
                    Drawable drawable = imageCam.getDrawable();
                    // Vérifier si le Drawable est une instance de BitmapDrawable
                    if (drawable instanceof BitmapDrawable) {
                        // Convertir le Drawable en Bitmap
                        Bitmap image = ((BitmapDrawable) drawable).getBitmap();
                        // Enregistrer l'image dans le stockage de l'appareil
                        String imageSavedPath = saveImageToGallery(image, nomCarte.getText().toString());
                        // Vérifier si l'enregistrement de l'image a réussi
                        if (imageSavedPath != null) {
                            // L'image a été enregistrée avec succès
                            // Vous pouvez effectuer d'autres opérations ici si nécessaire
                        } else {
                            // Gérer le cas où l'enregistrement de l'image a échoué
                        }
                    }
                }
            }
        });
    }


    private void prendreUnePhoto()  {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Récupérer l'ID de la caméra frontale
                String frontCameraId = null ;
                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    for (String cameraId : manager.getCameraIdList()) {
                        CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                        if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                            // Caméra frontale trouvée
                            frontCameraId = cameraId;
                            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            File photoFile = File.createTempFile("photo" +time, ".jpg",photoDir);
                            //enregistrer le chemin
                            this.photoPath = photoFile.getAbsolutePath();
                            break;
                        }
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        if (frontCameraId != null) {
                    startActivityForResult(intent, RETOUR_PRENDRE_PHOTO);
                } else {
                    // aller dans les parametres de l'appareil pour l'activer
                }
            }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETOUR_PRENDRE_PHOTO) {
            if (resultCode == RESULT_OK ) {
                // La photo a été prise et le résultat est valide
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    // Utiliser l'image capturée
                    imageCam.setImageBitmap(imageBitmap);

                }
            }
        }
    }

    private String saveImageToGallery(Bitmap image, String imageName) {
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
                MediaStore.Images.Media.insertImage(getContentResolver(), imageFile.getAbsolutePath(), imageName, "Description de l'image");
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


