package com.gurgur.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqllite_database";//database adı

    private static final String TABLE_NAME = "notlar_listesi";
    private static String NOT_BASLIK = "not_basligi";
    private static String NOT_ID = "not_id";
    private static String NOT_ICERIK = "not_icerik";
    private static String NOT_DATE ="not_tarih";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + NOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOT_BASLIK + " TEXT,"
                + NOT_ICERIK + " TEXT,"
                + NOT_DATE + " TEXT"
                 + ")";
        db.execSQL(CREATE_TABLE);
    }



    public void notEkle(String note_baslik,String note_icerik,String date) {
        //kitapEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOT_BASLIK, note_baslik);
        values.put(NOT_ICERIK, note_icerik);
        values.put(NOT_DATE, date);


        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    //tüm verileri siler
    public void tabloyu_bosalt(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    public ArrayList<HashMap<String, String>> kitaplar(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                kitaplist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return kitaplist;
    }


    public void notSil(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NOT_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }



    public void notDuzenle(String not_baslik, String not_icerik,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOT_BASLIK, not_baslik);
        values.put(NOT_ICERIK, not_icerik);

        db.update(TABLE_NAME, values, NOT_ID + " = ?",
                new String[] { String.valueOf(id) });
    }





    public HashMap<String, String> kitapDetay(int id){
        HashMap<String,String> kitap = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE not_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            kitap.put(NOT_BASLIK, cursor.getString(1));
            kitap.put(NOT_ICERIK, cursor.getString(2));
            kitap.put(NOT_DATE, cursor.getString(3));
        }
        cursor.close();
        db.close();
        return kitap;
    }




    public void resetTables(){
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

}
