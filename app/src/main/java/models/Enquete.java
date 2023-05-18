package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Enquete implements Serializable {

    public enum Etat
    {
        RESOLU("Résolue"),
        NON_RESOLU("Non Résolue");

        private String etat;

        Etat(String etat) {
            this.etat = etat;
        }

        public String getEtat() {
            return etat;
        }
    }
    private int id;
    private String titre;
    private String description;
    private String date;
    private String lieu;
    private String etat;

    private int id_user;
    private ArrayList<Suspect> suspects;
    private ArrayList<Victime> victimes;
    private ArrayList<Preuve> preuves;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void addSuspect(Suspect suspect){
        suspects.add(suspect);
    }

    public void addVictime(Victime victime){
        victimes.add(victime);
    }

    public void addPreuve(Preuve preuve){
        preuves.add(preuve);
    }

    public void addAllSuspect(ArrayList<Suspect> suspect){
        suspects.addAll(suspect);
    }

    public void addAllVictime(ArrayList<Victime> victime){
        victimes.addAll(victime);
    }

    public void addAllPreuve(ArrayList<Preuve> preuve){
        preuves.addAll(preuve);
    }

    public ArrayList<Suspect> getSuspects() {
        return suspects;
    }

    public ArrayList<Victime> getVictimes() {
        return victimes;
    }

    public ArrayList<Preuve> getPreuves() {
        return preuves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Enquete(int id, String titre, String description, String date, String lieu, String etat) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.etat = etat;
    }

    public Enquete() {
    }
}
