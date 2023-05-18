package com.example.enquete;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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
import repositories.PreuveRepositorie;

public class FormPreuveActivity extends AppCompatActivity {

    ImageButton imageViewPhoto;
    EditText editTextLieu;
    Button btnEnregistrer,btnSupprimer;
    Bitmap imgBitmap;
    PreuveRepositorie reposPreuves;
    private static final int PICK_IMAGE_REQUEST = 1;
    TextView form_title;
    Preuve preuveAmettreAjour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_nouveau_preuve);
        imageViewPhoto = (ImageButton) findViewById(R.id.imageViewPhoto);
        editTextLieu = (EditText) findViewById(R.id.editTextLieu);
        btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
        btnSupprimer = (Button) findViewById(R.id.btnSupprimer);
        form_title = (TextView) findViewById(R.id.form_title);
        reposPreuves = new PreuveRepositorie(this);
        int preuveAmettreAjourID = getIntent().getIntExtra("id_preuve",-1);
        preuveAmettreAjour = null;
        if(preuveAmettreAjourID != -1){
            preuveAmettreAjour = reposPreuves.recupererPreuveID(preuveAmettreAjourID);
            preuveAmettreAjour.setId(preuveAmettreAjourID);
            btnEnregistrer.setText("Mettre à jour");
            editTextLieu.setText(preuveAmettreAjour.getLieu());
            imageViewPhoto.setImageBitmap(preuveAmettreAjour.getImage());
            imgBitmap = preuveAmettreAjour.getImage();
            form_title.setText("Mise à jour d'une preuve");
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
                String lieu = editTextLieu.getText().toString();
                if(imgBitmap == null || lieu.isEmpty()){
                    Toast.makeText(getApplication().getApplicationContext(),"Veuillez remplir tous les champs et choisir une image!",Toast.LENGTH_LONG).show();
                    return;
                }
                Preuve nouvellePreuve = null;

                    nouvellePreuve = new Preuve();
                    nouvellePreuve.setImage(imgBitmap);
                    nouvellePreuve.setLieu(lieu);
                    nouvellePreuve.setId_enquete(getIntent().getIntExtra("id_enquete",0));
                    long rows = -1;
                if(preuveAmettreAjour == null){
                    rows = reposPreuves.ajouterPreuve(nouvellePreuve);
                    if (rows == -1) {
                        Toast.makeText(getApplication().getApplicationContext(),"Une erreur est survenue lors de l'insertion de la preuve!",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplication().getApplicationContext(),"La preuve a bien été insérée!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    nouvellePreuve.setId(preuveAmettreAjour.getId());
                    rows = reposPreuves.mettreAjourPreuve(nouvellePreuve);
                    if (rows == -1) {
                        Toast.makeText(getApplication().getApplicationContext(),"Une erreur est survenue lors de la mis à jour de la preuve!",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplication().getApplicationContext(),"La preuve a bien été mis à jour!",Toast.LENGTH_LONG).show();
                    }
                }



            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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