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

import models.Suspect;
import models.Victime;
import repositories.SuspectRepositorie;
import repositories.VictimeRepositorie;

public class FormVictimeActivity extends AppCompatActivity {
    ImageButton imageViewPhoto;
    EditText editTextNom, editTextPrenom,editTextAdresse, editTextDateNaissance,editTextTelephone,editTextCni;
    Button btnEnregistrer,btnSupprimer;
    Bitmap imgBitmap;
    VictimeRepositorie reposVictimes;
    private static final int PICK_IMAGE_REQUEST = 1;
    Victime victimeAmettreAjour;
    TextView form_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_nouvelle_victime);
        imageViewPhoto = (ImageButton) findViewById(R.id.imageViewPhoto);
        btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextAdresse = (EditText) findViewById(R.id.editTextAdresse);
        editTextDateNaissance = (EditText) findViewById(R.id.editTextDateNaissance);
        editTextTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editTextCni = (EditText) findViewById(R.id.editTextCni);
        btnSupprimer = (Button) findViewById(R.id.btnSupprimer);
        form_title = (TextView) findViewById(R.id.form_title);
        reposVictimes = new VictimeRepositorie(this);
        int victimeAmettreAjourID = getIntent().getIntExtra("id_victime",-1);
        victimeAmettreAjour = null;
        if(victimeAmettreAjourID != -1){
            victimeAmettreAjour = reposVictimes.recupererVictime(victimeAmettreAjourID);
            victimeAmettreAjour.setId(victimeAmettreAjourID);
            btnEnregistrer.setText("Mettre à jour");
            editTextNom.setText(victimeAmettreAjour.getNom());
            editTextPrenom.setText(victimeAmettreAjour.getPrenom());
            editTextAdresse.setText(victimeAmettreAjour.getAdresse());
            editTextDateNaissance.setText(victimeAmettreAjour.getDate_naissance());
            editTextCni.setText(victimeAmettreAjour.getCni());
            imageViewPhoto.setImageBitmap(victimeAmettreAjour.getPhoto());
            imgBitmap = victimeAmettreAjour.getPhoto();
            form_title.setText("Mise à jour d'une victime");
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
                String telephone = editTextTelephone.getText().toString();
                String cni = editTextCni.getText().toString();
                if (imgBitmap == null || nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || dateNaissance.isEmpty() || cni.isEmpty()) {
                    Toast.makeText(getApplication().getApplicationContext(), "Veuillez remplir tous les champs et choisir une image!", Toast.LENGTH_LONG).show();
                    return;
                }
                Victime nouvelleVictime = new Victime();
                nouvelleVictime.setNom(nom);
                nouvelleVictime.setPrenom(prenom);
                nouvelleVictime.setAdresse(adresse);
                nouvelleVictime.setDate_naissance(dateNaissance);
                nouvelleVictime.setCni(cni);
                nouvelleVictime.setTelephone(telephone);
                nouvelleVictime.setId_enquete(getIntent().getIntExtra("id_enquete",0));
                nouvelleVictime.setPhoto(imgBitmap);
                long rows = reposVictimes.ajouterVictime(nouvelleVictime);
                if (rows == -1) {
                    Toast.makeText(getApplication().getApplicationContext(),"Une erreur est survenue lors de l'insertion de la victime!",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplication().getApplicationContext(),"La victime a bien été inséré!",Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(victimeAmettreAjour != null){
                    long rows = reposVictimes.supprimerVictime(victimeAmettreAjour.getId());
                    if (rows > 0) {
                        Toast.makeText(getApplication().getApplicationContext(),"La victime a bien été supprimer!",Toast.LENGTH_LONG).show();
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