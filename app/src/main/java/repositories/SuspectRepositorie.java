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

import models.Preuve;
import models.Suspect;
import models.Utilisateur;
import models.Victime;

public class SuspectRepositorie {
    private DBHelper db;
    private String tablename = DBHelper.TABLENAME_SUSPECT;

    public SuspectRepositorie(Context context) {
        db = DBHelper.getInstance(context);
    }

    public long ajouterSuspect(Suspect nouveauSuspect) throws SQLException {
        if(nouveauSuspect == null){
            Log.d("DJIB-ENQUETE","L'objet nouveauSuspect est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_SUSPECT_NOM,nouveauSuspect.getNom());
        values.put(DBHelper.COLUMN_SUSPECT_PRENOM,nouveauSuspect.getPrenom());
        values.put(DBHelper.COLUMN_SUSPECT_ADRESSE,nouveauSuspect.getAdresse());
        values.put(DBHelper.COLUMN_SUSPECT_DATENAISSANCE,nouveauSuspect.getDate_naissance());
        values.put(DBHelper.COLUMN_SUSPECT_CNI,nouveauSuspect.getCni());
        values.put(DBHelper.COLUMN_ENQUETE_ID,nouveauSuspect.getId_enquete());
        Bitmap bitmap = nouveauSuspect.getPhoto();

        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();
            values.put(DBHelper.COLUMN_SUSPECT_PHOTO,byteArray);
        }

        long g=writableDatabase.insertOrThrow(tablename,null,values);


        return g;
    }

    public long supprimerSuspect(int idSuspect) throws SQLException {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();


        String whereClause = String.format("%s = ?",DBHelper.COLUMN_SUSPECT_ID);
        String[] whereArgs = { Integer.toString(idSuspect) };

        long numRowsUpdated = writableDatabase.delete(tablename, whereClause, whereArgs);

        writableDatabase.close();
        return numRowsUpdated;
    }

    public long mettreAjourSuspect(Suspect misAjourSuspect) throws SQLException {
        if(misAjourSuspect == null){
            Log.d("DJIB-ENQUETE","L'objet misAjourPreuve est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_SUSPECT_NOM,misAjourSuspect.getNom());
        values.put(DBHelper.COLUMN_SUSPECT_PRENOM,misAjourSuspect.getPrenom());
        values.put(DBHelper.COLUMN_SUSPECT_ADRESSE,misAjourSuspect.getAdresse());
        values.put(DBHelper.COLUMN_SUSPECT_DATENAISSANCE,misAjourSuspect.getDate_naissance());
        values.put(DBHelper.COLUMN_SUSPECT_CNI,misAjourSuspect.getCni());
        values.put(DBHelper.COLUMN_ENQUETE_ID,misAjourSuspect.getId_enquete());

        Bitmap bitmap = misAjourSuspect.getPhoto();

        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();
            values.put(DBHelper.COLUMN_SUSPECT_PHOTO,byteArray);
        }

        String whereClause = String.format("%s = ?",DBHelper.COLUMN_SUSPECT_ID);
        String[] whereArgs = { Integer.toString(misAjourSuspect.getId()) };

        long numRowsUpdated = writableDatabase.update(tablename, values, whereClause, whereArgs);

        writableDatabase.close();
        return numRowsUpdated;
    }
    public Suspect recupererSuspectID(int id){
        Suspect suspect = null;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s=%s",tablename,DBHelper.COLUMN_SUSPECT_ID,id);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                do{
                    byte[] blob = cursor.getBlob(6);

                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);

                    suspect = new Suspect(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),bitmap);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }




        return suspect;
    }

    public ArrayList<Suspect> recupererSuspects(int idEnquete){
        ArrayList<Suspect> suspects = new ArrayList<Suspect>();
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s = %s",tablename,DBHelper.COLUMN_ENQUETE_ID,idEnquete);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                Suspect un_suspect;
                do{
                    byte[] blob = cursor.getBlob(6);

                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);

                    un_suspect = new Suspect(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),bitmap);
                    suspects.add(un_suspect);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return suspects;
    }

    public int getCount(int idEnquete){
        int count = 0;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT Count(*) FROM %s WHERE %s=%s",tablename,DBHelper.COLUMN_ENQUETE_ID,idEnquete);
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
}
