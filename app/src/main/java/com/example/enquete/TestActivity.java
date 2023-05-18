package com.example.enquete;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import models.Utilisateur;
import repositories.UtilisateurRepositorie;


public class TestActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final String PREFERENCE = "MyPreference";
    Uri uri;
    Bitmap imgBitmap;
    ImageView imgView;
    Button recupGalerie;
    Button recupDatabase;
    Button inserer;
    EditText editTextId;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);


        recupDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.deleteLoginUser(preferences);
                Intent intent = new Intent(TestActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }
        });


        recupGalerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galerieIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galerieIntent.setType("image/*");

                startActivityForResult(Intent.createChooser(galerieIntent,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });

        inserer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgBitmap == null){
                    Toast.makeText(getApplication().getApplicationContext(),"Image bitmap is null.",Toast.LENGTH_LONG);
                    return;
                }

                UtilisateurRepositorie repos = new UtilisateurRepositorie(getApplication().getApplicationContext());
                Utilisateur newUser = new Utilisateur();
                newUser.setIdentifiant("hamza");
                newUser.setMot_de_passe("4567");
                newUser.setNom("Ahmed Chirwah");
                newUser.setPrenom("Hamza");
                newUser.setCni("232344>>Hamz<SDZE32");
                newUser.setAdresse("PK12");
                newUser.setImage(imgBitmap);

                long rows = repos.ajouterUtilisateur(newUser);

                if(rows == -1){
                    Toast.makeText(getApplication().getApplicationContext(),"Erreur lors de l'insertion à la base de donnée.",Toast.LENGTH_LONG);
                } else if (rows > 0) {
                    Toast.makeText(getApplication().getApplicationContext(),"Insertion dans la base de donnée réussie.",Toast.LENGTH_LONG);
                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            try {
                imgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imgView.setImageBitmap(imgBitmap);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}