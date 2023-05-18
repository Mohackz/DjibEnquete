package com.example.enquete;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import models.Utilisateur;
import repositories.UtilisateurRepositorie;

public class inscriptionActivity extends AppCompatActivity {
    EditText editTextLogin, editTextEmail, editTextAdresse, editTextMotDePasse, editTextNom, editTextPrenom;
    Button btnConnecter;
    UtilisateurRepositorie utilisateurRepos;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscritption);
        preferences = getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAdresse = (EditText) findViewById(R.id.editTextAdresse);
        editTextMotDePasse = (EditText) findViewById(R.id.editTextMotDePasse);
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        btnConnecter = (Button) findViewById(R.id.btnConnecter);

        utilisateurRepos = new UtilisateurRepositorie(this);

        btnConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = editTextLogin.getText().toString();
                String email = editTextEmail.getText().toString();
                String adresse = editTextAdresse.getText().toString();
                String motDePasse = editTextMotDePasse.getText().toString();
                String nom = editTextNom.getText().toString();
                String prenom = editTextPrenom.getText().toString();

                if(login.isEmpty() || email.isEmpty() || adresse.isEmpty() || motDePasse.isEmpty() || nom.isEmpty() || prenom.isEmpty()){
                    Toast.makeText(getApplication().getApplicationContext(),"Veuillez remplir tous les champs !",Toast.LENGTH_LONG).show();
                    return;
                }

                Utilisateur nouveauUtilisateur = new Utilisateur();
                nouveauUtilisateur.setIdentifiant(login);
                nouveauUtilisateur.setEmail(email);
                nouveauUtilisateur.setAdresse(adresse);
                nouveauUtilisateur.setMot_de_passe(motDePasse);
                nouveauUtilisateur.setNom(nom);
                nouveauUtilisateur.setPrenom(prenom);


                try {
                    long rows = utilisateurRepos.ajouterUtilisateur(nouveauUtilisateur);
                    if(rows == -1){
                        Toast.makeText(getApplication().getApplicationContext(),"Une erreur est survenue lors de votre inscription. Vueillez réessayez plus tard!",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplication().getApplicationContext(),"Vous vous êtes bien inscript! Désormais vous pouvez vous connecter!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(inscriptionActivity.this,LoginActivity.class);
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
        if(LoginActivity.isUserLogin(preferences)){
            Intent intent = new Intent(inscriptionActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    }


