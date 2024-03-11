package com.example.appyugioh.vue;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.*;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appyugioh.modele.ComportementMenu;
import com.example.appyugioh.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnregistrerCarte extends Activity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;

    private static final int RETOUR_PRENDRE_PHOTO = 1;
    protected EditText nomCarte;

    protected EditText nomEdition;

    protected LinearLayout layoutResultatCam;

    protected ImageView imageCam;

    protected Button boutonAccesCamera;

    protected Button boutonEnregistrementCarte;

    protected NavigationView navigationView;

    protected ComportementMenu comportementMenu;


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

        this.navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        this.comportementMenu = new ComportementMenu();

        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);
        MenuItem menuItem6 = menu.findItem(R.id.menu_bouton_enregistrer_carte);

        final Activity elem1 = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, elem1);
            }
        });

        boutonAccesCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demanderPermissionCamera();
            }
        });

        boutonEnregistrementCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    enregistrementCarte(nomCarte, nomEdition, imageCam);
                    afficherConfirmationEnregistrementCarte();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void afficherConfirmationEnregistrementCarte() {
        Snackbar.make(findViewById(android.R.id.content), "Carte enregistrée avec succès", Snackbar.LENGTH_SHORT).show();
    }


    private void enregistrementCarte(EditText nomCarte, EditText nomEdition, ImageView imageCam) throws JSONException {
        String nomCarteText = nomCarte.getText().toString();
        String nomEditionText = nomEdition.getText().toString();

        // Vérifier si le nom de la carte et l'édition sont vides ou égaux aux valeurs par défaut
        if (!TextUtils.isEmpty(nomCarteText) && !nomCarteText.equals(getString(R.string.nom_carte)) &&
                !TextUtils.isEmpty(nomEditionText) && !nomEditionText.equals(getString(R.string.edition))) {

            // Vérifier si l'imageView n'est pas vide
            if (imageCam.getDrawable() != null) {
                // Récupérer le Drawable de l'ImageView
                Drawable drawable = imageCam.getDrawable();
                // Vérifier si le Drawable est une instance de BitmapDrawable
                if (drawable instanceof BitmapDrawable) {
                    // Convertir le Drawable en Bitmap
                    Bitmap image = ((BitmapDrawable) drawable).getBitmap();
                    // Enregistrer l'image dans le stockage de l'appareil
                    String imageSavedPath = saveImageToGallery(image, nomCarteText);
                    // Vérifier si l'enregistrement de l'image a réussi
                    if (imageSavedPath != null) {
                        // Créer un objet JSON avec le nom de la carte, l'édition et l'emplacement de l'image
                        // TODO: enregistrer d'autres infos pour savoir si c'est une carte prise ne photo ou non
                        JSONObject carteJSON = new JSONObject();
                        carteJSON.put("nomCarte", nomCarteText);
                        carteJSON.put("nomEdition", nomEditionText);
                        carteJSON.put("imagePath", imageSavedPath);

                        // Enregistrer l'objet JSON dans un fichier
                        String jsonFilePath = saveJSONObjectToFile(carteJSON);
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
    private String saveJSONObjectToFile(JSONObject jsonObject) {
        try {
            // Créer un fichier dans le répertoire des fichiers de l'application
            File jsonFile = new File(getFilesDir(), "carte.json");
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
                Toast.makeText(this, "Aucune application de caméra disponible", Toast.LENGTH_SHORT).show();
                redirigerVersParametresCamera();
        }
    }


    private void redirigerVersParametresCamera() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
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

    private void demanderPermissionCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            ouvrirCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ouvrirCamera();
            } else {
                // La permission a été refusée. Vous pouvez afficher un message à l'utilisateur.
                Toast.makeText(this, "Permission de la caméra refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ouvrirCamera() {
        // Intent pour accéder à la caméra
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        prendreUnePhoto();
    }
}


