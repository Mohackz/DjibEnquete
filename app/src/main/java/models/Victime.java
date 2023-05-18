package models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Victime implements Serializable {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String date_naissance;
    private String telephone;

    private String cni;
    private Bitmap photo;

    private int id_enquete;

    public int getId_enquete() {
        return id_enquete;
    }

    public void setId_enquete(int id_enquete) {
        this.id_enquete = id_enquete;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Victime(int id, String nom, String prenom, String adresse, String date_naissance, String telephone ,String cni, Bitmap photo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.date_naissance = date_naissance;
        this.telephone = telephone;
        this.cni = cni;
        this.photo = photo;
    }

    public Victime() {
    }
}
