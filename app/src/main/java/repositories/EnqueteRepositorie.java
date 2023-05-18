package repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.enquete.DBHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import models.Enquete;
import models.Preuve;
import models.Suspect;
import models.Utilisateur;
import models.Victime;

public class EnqueteRepositorie {
    private DBHelper db;
    private String tablename = DBHelper.TABLENAME_ENQUETE;
    PreuveRepositorie preuveRepos;
    SuspectRepositorie suspectRepos;
    VictimeRepositorie victimeRepos;

    public EnqueteRepositorie(Context context) {
        db = DBHelper.getInstance(context);
        preuveRepos = new PreuveRepositorie(context);
        suspectRepos = new SuspectRepositorie(context);
        victimeRepos = new VictimeRepositorie(context);
    }

    public long ajouterEnquete(Enquete nouveauEnquete) throws SQLException {
        if(nouveauEnquete == null){
            Log.d("DJIB-ENQUETE","L'objet nouveauEnquete est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_ENQUETE_TITRE,nouveauEnquete.getTitre());
        values.put(DBHelper.COLUMN_ENQUETE_DESCRIPTION,nouveauEnquete.getDescription());
        values.put(DBHelper.COLUMN_ENQUETE_DATE,nouveauEnquete.getDate());
        values.put(DBHelper.COLUMN_ENQUETE_LIEU,nouveauEnquete.getLieu());
        values.put(DBHelper.COLUMN_ENQUETE_ETAT,nouveauEnquete.getEtat());
        values.put(DBHelper.COLUMN_UTILISATEUR_ID,nouveauEnquete.getId_user());

        long g=writableDatabase.insertOrThrow(tablename,null,values);


        return g;
    }
    public Enquete recupererEnquete(int id){
        Enquete enquete = null;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s=%s",tablename,DBHelper.COLUMN_ENQUETE_ID,id);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                do{
                    enquete = new Enquete(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5));
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return enquete;
    }

    public int getCount(){
        int count = 0;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT Count(*) FROM %s",tablename);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return count;
    }

    public int getCount(int idUser){
        int count = 0;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT Count(*) FROM %s WHERE %s=%s",tablename,DBHelper.COLUMN_UTILISATEUR_ID,idUser);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return count;
    }

    public ArrayList<Enquete> recupererEnquetes(int idUser){
        ArrayList<Enquete> enquetes = new ArrayList<Enquete>();
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=%s",tablename,DBHelper.COLUMN_UTILISATEUR_ID,idUser);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                Enquete une_enquete;
                do{
                    une_enquete = new Enquete(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5));
                    enquetes.add(une_enquete);
                }while(cursor.moveToNext());


            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return enquetes;
    }

}
