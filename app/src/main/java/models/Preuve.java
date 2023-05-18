package models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Preuve implements Serializable {
 private int id;
 private String lieu;
 private Bitmap image;

    public int getId_enquete() {
        return id_enquete;
    }

    public void setId_enquete(int id_enquete) {
        this.id_enquete = id_enquete;
    }

    private int id_enquete;

    public int getId() {
        return id;
    }
    public void setId(int id){ this.id = id;}

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Preuve() {
    }

    public Preuve(int id, String lieu, Bitmap image) {
        this.id = id;
        this.lieu = lieu;
        this.image = image;
    }
}
