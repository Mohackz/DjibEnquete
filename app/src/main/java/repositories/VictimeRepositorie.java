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

import models.Utilisateur;
import models.Victime;

public class VictimeRepositorie {
    private DBHelper db;
    private String tablename = DBHelper.TABLENAME_VICTIME;

    public VictimeRepositorie(Context context) {
        db = DBHelper.getInstance(context);
    }

    public long ajouterVictime(Victime nouveauVictime){
        if(nouveauVictime == null){
            Log.d("DJIB-ENQUETE","L'objet victime est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();


        try{

            ContentValues values=new ContentValues();
            values.put(DBHelper.COLUMN_VICTIME_NOM,nouveauVictime.getNom());
            values.put(DBHelper.COLUMN_VICTIME_PRENOM,nouveauVictime.getPrenom());
            values.put(DBHelper.COLUMN_VICTIME_ADRESSE,nouveauVictime.getAdresse());
            values.put(DBHelper.COLUMN_VICTIME_DATE_NAISSANCE,nouveauVictime.getDate_naissance());
            values.put(DBHelper.COLUMN_VICTIME_TELEPHONE,nouveauVictime.getTelephone());
            values.put(DBHelper.COLUMN_VICTIME_CNI,nouveauVictime.getCni());
            values.put(DBHelper.COLUMN_ENQUETE_ID,nouveauVictime.getId_enquete());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = nouveauVictime.getPhoto();
            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray();
                values.put(DBHelper.COLUMN_VICTIME_PHOTO,byteArray);
            }


            long g=writableDatabase.insert(tablename,null,values);
            if(g == -1){
                Log.d("DJIB-ENQUETE","Erreur message: Impossible d'inserer des données.");
                return -1;
            } else if(g > 0){
                Log.d("DJIB-ENQUETE","Insertion des données réussies.");
                return g;
            }

        } catch (SQLException e){
            Log.d("DJIB-ENQUETE",e.getMessage()+e.getStackTrace().toString());
        }
        return -1;
    }

    public Victime recupererVictime(int id){
        Victime victime = null;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s=%s",tablename,DBHelper.COLUMN_VICTIME_ID,id);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                do{
                    byte[] blob = cursor.getBlob(7);
                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    victime = new Victime(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),bitmap);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return victime;
    }

    public ArrayList<Victime> recupererVictimes(int idEnquete){
        ArrayList<Victime> victimes = new ArrayList<Victime>();
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s=%s",tablename,DBHelper.COLUMN_VICTIME_ID,idEnquete);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                Victime une_victime;
                do{
                    byte[] blob = cursor.getBlob(7);
                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    une_victime = new Victime(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),bitmap);
                    victimes.add(une_victime);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return victimes;
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

    public long supprimerVictime(int idVictime) throws SQLException {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();


        String whereClause = String.format("%s = ?",DBHelper.COLUMN_SUSPECT_ID);
        String[] whereArgs = { Integer.toString(idVictime) };

        long numRowsUpdated = writableDatabase.delete(tablename, whereClause, whereArgs);

        writableDatabase.close();
        return numRowsUpdated;
    }


}
