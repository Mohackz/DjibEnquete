package com.example.enquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import models.Utilisateur;
import repositories.UtilisateurRepositorie;

public class LoginActivity extends AppCompatActivity {
    EditText editTextLogin,  editTextMotDePasse;
    UtilisateurRepositorie utilisateurRepos;
    private static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextMotDePasse = (EditText) findViewById(R.id.editTextMotDePasse);
        utilisateurRepos = new UtilisateurRepositorie(this);
        preferences = getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(LoginActivity.isUserLogin(preferences)){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void connexion(View view){
        String identifiant = editTextLogin.getText().toString();
        String motDePasse = editTextMotDePasse.getText().toString();

        //recuperer l'uilisateur de la base de donnée à partir de son identifiant
        Utilisateur utilisateur = utilisateurRepos.recupererUtilisateurLogin(identifiant);

        if(utilisateur != null && utilisateur.getMot_de_passe().equals(motDePasse)){
            savedLoginUser(utilisateur);
            Toast.makeText(this,"Vous vous êtes bien connecté!",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Vos identifians sont incorrectes. Veuillez réessayer!",Toast.LENGTH_LONG).show();
        }
    }

    private void savedLoginUser(Utilisateur utilisateur){
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("id",utilisateur.getId());
        editor.putString("identifiant",utilisateur.getIdentifiant());
        editor.putString("email",utilisateur.getEmail());
        editor.putString("nom",utilisateur.getNom());
        editor.putString("prenom",utilisateur.getPrenom());
        editor.putString("adresse",utilisateur.getAdresse());
        editor.putString("cni",utilisateur.getCni());
        editor.commit();
    }

    public static void deleteLoginUser(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove("id");
        editor.remove("identifiant");
        editor.remove("email");
        editor.remove("nom");
        editor.remove("prenom");
        editor.remove("adresse");
        editor.remove("cni");
        editor.commit();
    }

    public static boolean isUserLogin(SharedPreferences preferences){
        String login = preferences.getString("identifiant","");
        return !login.isEmpty();
    }
}