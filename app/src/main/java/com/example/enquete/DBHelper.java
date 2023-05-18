package com.example.enquete;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    final private static String dbname="enquete.db";
    private static DBHelper instance = null;

    // Nom des tables
    public static final String TABLENAME_UTILISATEUR = "Utilisateur";
    public static final String TABLENAME_VICTIME = "Victime";
    public static final String TABLENAME_PREUVE = "Preuve";
    public static final String TABLENAME_SUSPECT = "Suspect";
    public static final String TABLENAME_ENQUETE = "Enquete";

    // Champs de la table UTILISATEUR
    public static final String COLUMN_UTILISATEUR_ID = "id_user";
    public static final String COLUMN_UTILISATEUR_IDENTIFIANT = "identifiant";
    public static final String COLUMN_UTILISATEUR_MOTDEPASSE = "mot_de_passe";
    public static final String COLUMN_UTILISATEUR_NOM = "nom";
    public static final String COLUMN_UTILISATEUR_PRENOM = "prenom";
    public static final String COLUMN_UTILISATEUR_CNI = "cni";
    public static final String COLUMN_UTILISATEUR_ADRESSE = "adresse";
    public static final String COLUMN_UTILISATEUR_PHOTO = "photo";

    // Champs de la table VICTIME
    public static final String COLUMN_VICTIME_ID = "id_victime";
    public static final String COLUMN_VICTIME_NOM = "nom";
    public static final String COLUMN_VICTIME_PRENOM = "prenom";
    public static final String COLUMN_VICTIME_ADRESSE = "adresse";
    public static final String COLUMN_VICTIME_DATE_NAISSANCE = "date_naissance";
    public static final String COLUMN_VICTIME_TELEPHONE = "telephone";
    public static final String COLUMN_VICTIME_CNI = "cni";
    public static final String COLUMN_VICTIME_PHOTO = "photo";

    // Champs de la table SUSPECT
    public static final String COLUMN_SUSPECT_ID = "id_suspect";
    public static final String COLUMN_SUSPECT_NOM = "nom";
    public static final String COLUMN_SUSPECT_PRENOM = "prenom";
    public static final String COLUMN_SUSPECT_ADRESSE = "adresse";
    public static final String COLUMN_SUSPECT_DATENAISSANCE = "date_naissance";
    public static final String COLUMN_SUSPECT_CNI = "cni";
    public static final String COLUMN_SUSPECT_PHOTO = "photo";

    // Champs de la table PREUVE
    public static final String COLUMN_PREUVE_ID = "id_preuve";
    public static final String COLUMN_PREUVE_lieu = "lieu";
    public static final String COLUMN_PREUVE_image = "image";


    // Champs de la table ENQUETE
    public static final String COLUMN_ENQUETE_ID = "id_enquete";
    public static final String COLUMN_ENQUETE_TITRE = "titre";
    public static final String COLUMN_ENQUETE_DESCRIPTION = "description";
    public static final String COLUMN_ENQUETE_DATE = "date";
    public static final String COLUMN_ENQUETE_LIEU = "lieu";
    public static final String COLUMN_ENQUETE_ETAT = "etat";




    private DBHelper(Context context) {
        super(context, dbname, null, 1);
    }

    public static DBHelper getInstance(Context context){
        if(instance != null)
            return instance;

        return new DBHelper(context);
    }


    @Override
    public void onCreate(@NonNull SQLiteDatabase db)
    {
        String table_utilisateur_query = String.format("create table %s (%s integer primary key autoincrement,%s text unique,%s text,%s text,%s text,%s text unique,%s text,%s blob)",
                TABLENAME_UTILISATEUR,
                COLUMN_UTILISATEUR_ID,
                COLUMN_UTILISATEUR_IDENTIFIANT,
                COLUMN_UTILISATEUR_MOTDEPASSE,
                COLUMN_UTILISATEUR_NOM,
                COLUMN_UTILISATEUR_PRENOM,
                COLUMN_UTILISATEUR_CNI,
                COLUMN_UTILISATEUR_ADRESSE,
                COLUMN_UTILISATEUR_PHOTO);
        db.execSQL(table_utilisateur_query);

        String table_victime_query = String.format("create table %s (%s integer primary key autoincrement,%s text,%s text,%s text,%s text,%s text,%s text,%s blob,%s integer references %s(%s))",
                TABLENAME_VICTIME,
                COLUMN_VICTIME_ID,
                COLUMN_VICTIME_NOM,
                COLUMN_VICTIME_PRENOM,
                COLUMN_VICTIME_ADRESSE,
                COLUMN_VICTIME_DATE_NAISSANCE,
                COLUMN_VICTIME_TELEPHONE,
                COLUMN_VICTIME_CNI,
                COLUMN_VICTIME_PHOTO,
                COLUMN_ENQUETE_ID,
                TABLENAME_ENQUETE,
                COLUMN_ENQUETE_ID);
        db.execSQL(table_victime_query);

        String table_suspect_query = String.format("create table %s (%s integer primary key autoincrement,%s text,%s text,%s text,%s text,%s text,%s blob,%s integer references %s(%s))",
                TABLENAME_SUSPECT,
                COLUMN_SUSPECT_ID,
                COLUMN_SUSPECT_NOM,
                COLUMN_SUSPECT_PRENOM,
                COLUMN_SUSPECT_ADRESSE,
                COLUMN_SUSPECT_DATENAISSANCE,
                COLUMN_SUSPECT_CNI,
                COLUMN_SUSPECT_PHOTO,
                COLUMN_ENQUETE_ID,
                TABLENAME_ENQUETE,
                COLUMN_ENQUETE_ID
                );
        db.execSQL(table_suspect_query);

        String table_preuve_query = String.format("create table %s (%s integer primary key autoincrement,%s text,%s text,%s integer references %s(%s))",
                TABLENAME_PREUVE,
                COLUMN_PREUVE_ID,
                COLUMN_PREUVE_lieu,
                COLUMN_PREUVE_image,
                COLUMN_ENQUETE_ID,
                TABLENAME_ENQUETE,
                COLUMN_ENQUETE_ID
                );
        db.execSQL(table_preuve_query);

        String table_enquete_query = String.format("create table %s (%s integer primary key autoincrement,%s text,%s text,%s text,%s text,%s text,%s integer references %s(%s))",
                TABLENAME_ENQUETE,
                COLUMN_ENQUETE_ID,
                COLUMN_ENQUETE_TITRE,
                COLUMN_ENQUETE_DESCRIPTION,
                COLUMN_ENQUETE_DATE,
                COLUMN_ENQUETE_LIEU,
                COLUMN_ENQUETE_ETAT,
                COLUMN_UTILISATEUR_ID,
                TABLENAME_UTILISATEUR,
                COLUMN_UTILISATEUR_ID
                );

        db.execSQL(table_enquete_query);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS Utilisateur");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Victime ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Suspect");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Preuve");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Enquete");
        onCreate(db);

    }

}
