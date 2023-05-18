package com.example.enquete;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import models.Preuve;
import models.Suspect;
import repositories.SuspectRepositorie;

public class FormSuspectActivity extends AppCompatActivity {
    ImageButton imageViewPhoto;
    EditText editTextNom, editTextPrenom,editTextAdresse, editTextDateNaissance,editTextCni;
    Button btnEnregistrer,btnSupprimer;
    Bitmap imgBitmap;
    SuspectRepositorie reposSuspect;
    Suspect suspectAmettreAjour;
    TextView form_title;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_nouveau_suspect);
        imageViewPhoto = (ImageButton) findViewById(R.id.imageViewPhoto);
        btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
        btnSupprimer = (Button) findViewById(R.id.btnSupprimer);
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextAdresse = (EditText) findViewById(R.id.editTextAdresse);
        editTextDateNaissance = (EditText) findViewById(R.id.editTextDateNaissance);
        editTextCni = (EditText) findViewById(R.id.editTextCni);
        form_title = (TextView) findViewById(R.id.form_title);
        reposSuspect = new SuspectRepositorie(this);
        int suspectAmettreAjourID = getIntent().getIntExtra("id_suspect",-1);
        suspectAmettreAjour = null;
        if(suspectAmettreAjourID != -1){
            suspectAmettreAjour = reposSuspect.recupererSuspectID(suspectAmettreAjourID);
            suspectAmettreAjour.setId(suspectAmettreAjourID);
            btnEnregistrer.setText("Mettre à jour");
            editTextNom.setText(suspectAmettreAjour.getNom());
            editTextPrenom.setText(suspectAmettreAjour.getPrenom());
            editTextAdresse.setText(suspectAmettreAjour.getAdresse());
            editTextDateNaissance.setText(suspectAmettreAjour.getDate_naissance());
            editTextCni.setText(suspectAmettreAjour.getCni());
            imageViewPhoto.setImageBitmap(suspectAmettreAjour.getPhoto());
            imgBitmap = suspectAmettreAjour.getPhoto();
            form_title.setText("Mise à jour des preuves");
            btnSupprimer.setVisibility(Button.VISIBLE);
        }


        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Choisir image"),PICK_IMAGE_REQUEST);
            }
        });

        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = editTextNom.getText().toString();
                String prenom = editTextPrenom.getText().toString();
                String adresse = editTextAdresse.getText().toString();
                String dateNaissance = editTextDateNaissance.getText().toString();
                String cni = editTextCni.getText().toString();
                if (imgBitmap == null || nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || dateNaissance.isEmpty() || cni.isEmpty()) {
                    Toast.makeText(getApplication().getApplicationContext(), "Veuillez remplir tous les champs et choisir une image!", Toast.LENGTH_LONG).show();
                    return;
                }
                Suspect nouveauSuspect = new Suspect();
                nouveauSuspect.setNom(nom);
                nouveauSuspect.setPrenom(prenom);
                nouveauSuspect.setAdresse(adresse);
                nouveauSuspect.setDate_naissance(dateNaissance);
                nouveauSuspect.setCni(cni);
                nouveauSuspect.setId_enquete(getIntent().getIntExtra("id_enquete",0));
                nouveauSuspect.setPhoto(imgBitmap);

                long rows;
                if(suspectAmettreAjour == null){
                    rows = reposSuspect.ajouterSuspect(nouveauSuspect);
                    if (rows == -1) {
                        Toast.makeText(getApplication().getApplicationContext(),"Une erreur est survenue lors de l'insertion du suspect!",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplication().getApplicationContext(),"Le suspect a bien été inséré!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    nouveauSuspect.setId(suspectAmettreAjour.getId());
                    rows = reposSuspect.mettreAjourSuspect(nouveauSuspect);
                    if (rows == -1) {
                        Toast.makeText(getApplication().getApplicationContext(),"Une erreur est survenue lors de la mis à jour du suspect!",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplication().getApplicationContext(),"Le suspect a bien été mis à jour!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

            }
        });

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(suspectAmettreAjour != null){
                    long rows = reposSuspect.supprimerSuspect(suspectAmettreAjour.getId());
                    if (rows > 0) {
                        Toast.makeText(getApplication().getApplicationContext(),"Le suspect a bien été supprimer!",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication().getApplicationContext(),"Erreur lors de la suppression!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            Uri imgUri = data.getData();
            try {
                imgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageViewPhoto.setImageBitmap(imgBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}