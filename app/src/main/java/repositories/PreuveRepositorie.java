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
import java.util.List;

import models.Preuve;
import models.Suspect;

public class PreuveRepositorie {
    private DBHelper db;
    private String tablename = DBHelper.TABLENAME_PREUVE;

    public PreuveRepositorie(Context context) {
        db = DBHelper.getInstance(context);
    }

    public long ajouterPreuve(Preuve nouveauPreuve) throws SQLException {
        if(nouveauPreuve == null){
            Log.d("DJIB-ENQUETE","L'objet nouveauPreuve est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_PREUVE_lieu,nouveauPreuve.getLieu());
        values.put(DBHelper.COLUMN_ENQUETE_ID,nouveauPreuve.getId_enquete());
        Bitmap bitmap = nouveauPreuve.getImage();

        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();
            values.put(DBHelper.COLUMN_PREUVE_image,byteArray);
        }

        long g=writableDatabase.insertOrThrow(tablename,null,values);


        writableDatabase.close();
        return g;
    }
    public long mettreAjourPreuve(Preuve misAjourPreuve) throws SQLException {
        if(misAjourPreuve == null){
            Log.d("DJIB-ENQUETE","L'objet misAjourPreuve est null");
            return -1;
        }

        SQLiteDatabase writableDatabase = db.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_PREUVE_lieu,misAjourPreuve.getLieu());

        Bitmap bitmap = misAjourPreuve.getImage();

        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();
            values.put(DBHelper.COLUMN_PREUVE_image,byteArray);
        }

        String whereClause = String.format("%s = ?",DBHelper.COLUMN_PREUVE_ID);
        String[] whereArgs = { Integer.toString(misAjourPreuve.getId()) };

        long numRowsUpdated = writableDatabase.update(DBHelper.TABLENAME_PREUVE, values, whereClause, whereArgs);

        writableDatabase.close();
        return numRowsUpdated;
    }

    public long supprimerPreuve(int idPreuve) throws SQLException {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();


        String whereClause = String.format("%s = ?",DBHelper.COLUMN_PREUVE_ID);
        String[] whereArgs = { Integer.toString(idPreuve) };

        long numRowsUpdated = writableDatabase.delete(DBHelper.TABLENAME_PREUVE, whereClause, whereArgs);

        writableDatabase.close();
        return numRowsUpdated;
    }

    public Preuve recupererPreuveID(int id){
        Preuve preuve = null;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s Where %s=%s",tablename,DBHelper.COLUMN_PREUVE_ID,id);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                do{
                    byte[] blob = cursor.getBlob(2);

                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);

                    preuve = new Preuve(cursor.getInt(0),cursor.getString(1),bitmap);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }



        readableDatabase.close();
        return preuve;
    }



    public ArrayList<Preuve> recupererPreuves(int idEnquete){
        ArrayList<Preuve> preuves = new ArrayList<Preuve>() ;
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=%s",tablename,DBHelper.COLUMN_ENQUETE_ID,idEnquete);
        Cursor cursor = null;
        try{
            cursor = readableDatabase.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                Preuve une_preuve;
                do{
                    byte[] blob = cursor.getBlob(2);

                    Bitmap bitmap = null;
                    if(blob != null)
                        bitmap =  BitmapFactory.decodeByteArray(blob, 0, blob.length);

                    une_preuve = new Preuve(cursor.getInt(0),cursor.getString(1),bitmap);
                    preuves.add(une_preuve);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        readableDatabase.close();
        return preuves;
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
