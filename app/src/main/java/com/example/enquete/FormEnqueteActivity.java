package com.example.enquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import models.Enquete;
import repositories.EnqueteRepositorie;

public class FormEnqueteActivity extends AppCompatActivity {

    EditText editTextTitre, editTextDescription, editTextDate, editTextLieu;

    Button btnEnregistrer;
    SharedPreferences preferences;
    EnqueteRepositorie enqueteRepos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_nouvelle_enquete);
        preferences = getSharedPreferences(MainActivity.PREFERENCE,MODE_PRIVATE);
        enqueteRepos = new EnqueteRepositorie(this);

        editTextTitre = (EditText) findViewById(R.id.editTextTitre);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextLieu = (EditText) findViewById(R.id.editTextLieu);


        btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);

        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titre = editTextTitre.getText().toString();
                String description = editTextDescription.getText().toString();
                String date = editTextDate.getText().toString();
                String lieu = editTextLieu.getText().toString();
                int idUser = preferences.getInt("id",0);
                String etat = Enquete.Etat.NON_RESOLU.getEtat();

                Enquete nouvelleEnquete = new Enquete();
                nouvelleEnquete.setTitre(titre);
                nouvelleEnquete.setDescription(description);
                nouvelleEnquete.setDate(date);
                nouvelleEnquete.setLieu(lieu);
                nouvelleEnquete.setId_user(idUser);
                nouvelleEnquete.setEtat(etat);

                try {
                    long rows = enqueteRepos.ajouterEnquete(nouvelleEnquete);
                    if(rows == -1){
                        Toast.makeText(getApplication().getApplicationContext(),"Erreur lors de l'insertion de l'enquete",Toast.LENGTH_LONG).show();

                    } else{
                        Toast.makeText(getApplication().getApplicationContext(),"Nouvelle enquete insérée !",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(FormEnqueteActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }


            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(!LoginActivity.isUserLogin(preferences)){
            Intent intent = new Intent(FormEnqueteActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}