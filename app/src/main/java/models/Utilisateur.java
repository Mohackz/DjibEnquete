package models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Utilisateur implements Serializable {


    public Utilisateur() {
    }

    public Utilisateur(int id, String identifiant, String mot_de_passe,  String nom, String prenom,String cni, String adresse, Bitmap image) {
        this.id = id;
        this.identifiant = identifiant;
        this.mot_de_passe = mot_de_passe;
        this.cni = cni;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int id;
    private String identifiant;
    private String mot_de_passe;
    private String email;
    private String cni;

    private String nom;
    private String prenom;
    private String adresse;
    private Bitmap image;


}
