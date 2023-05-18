package repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.enquete.DBHelper;
import com.example.enquete.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import models.Utilisateur;

public class UtilisateurRepositorie {
    private DBHelper db;
    private String tablename = DBHelper.TABLENAME_UTILISATEUR;

    public UtilisateurRepositorie(Context context) {
        db = DBHelper.getInstance(context);
    }

    public long ajouterUtilisateur(Utilisateur nouveauUtilisateur) throws SQLException{
        if(nouveauUtilisateur == null){
            Log.d("DJIB-ENQUETE","L'objet utilisateur est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_UTILISATEUR_IDENTIFIANT,nouveauUtilisateur.getIdentifiant());
        values.put(DBHelper.COLUMN_UTILISATEUR_MOTDEPASSE,nouveauUtilisateur.getMot_de_passe());
        values.put(DBHelper.COLUMN_UTILISATEUR_NOM,nouveauUtilisateur.getNom());
        values.put(DBHelper.COLUMN_UTILISATEUR_PRENOM,nouveauUtilisateur.getPrenom());
        values.put(DBHelper.COLUMN_UTILISATEUR_CNI,nouveauUtilisateur.getCni());
        values.put(DBHelper.COLUMN_UTILISATEUR_ADRESSE,nouveauUtilisateur.getAdresse());

        if(nouveauUtilisateur.getImage() != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = nouveauUtilisateur.getImage();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();
            values.put(DBHelper.COLUMN_UTILISATEUR_PHOTO,byteArray);
        }

        long g=writableDatabase.insertOrThrow(tablename,null,values);


        return g;
    }
    public Utilisateur recupererUtilisateur(int id){
        Utilisateur utilisateur = null;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s=%s",tablename,DBHelper.COLUMN_UTILISATEUR_ID,id);
        Cursor cursor = null;
    try{
        cursor = readableDatabase.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                byte[] blob = cursor.getBlob(7);

                Bitmap bitmap = null;
                if(blob != null)
                    bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);

                utilisateur = new Utilisateur(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),bitmap);
            }while(cursor.moveToNext());
        }
    } catch (Exception e){
        e.printStackTrace();
    } finally {
        cursor.close();
    }




        return utilisateur;
    }

    public Utilisateur recupererUtilisateurLogin(String identifiant){
        Utilisateur utilisateur = null;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s='%s'",tablename,DBHelper.COLUMN_UTILISATEUR_IDENTIFIANT,identifiant);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                do{
                    byte[] blob = cursor.getBlob(7);

                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);

                    utilisateur = new Utilisateur(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),bitmap);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }




        return utilisateur;
    }


}
