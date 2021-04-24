package com.example.mynotes;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
public class Databasehelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "notes.db";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUDIOS = "audio";
    public static final String CATEGORY = "category";
    public static final String CATEGORYPOS = "category_pos";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";

    public static final String TABLE_CREATE = "create table " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT, " +
            AUDIOS + " TEXT, " +
            CATEGORY + " TEXT, " +
            CATEGORYPOS + " INTEGER, " +
            DATE + " VARCHAR, " +
            DESCRIPTION + " VARCHAR )";



    public Databasehelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.e(TAG, "Create application database");
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e(TAG, "Create table");

        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        Log.e(TAG, "Create application Upgrading");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addNotes(String title, String description, String cName, int cpos, String uri, String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(AUDIOS, uri);
        contentValues.put(DESCRIPTION,description);
        contentValues.put(CATEGORY,cName);
        contentValues.put(CATEGORYPOS,cpos);
        contentValues.put(DATE, String.valueOf(date));


        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }

    }

    public boolean update(String titl,String description ,int receivedId,String category,String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE,titl);
        values.put(DESCRIPTION,description);
        values.put(CATEGORY,category);
        values.put(DATE,date);



        // updating row
        db.update(TABLE_NAME, values, "id = ? ", new String[] { Integer.toString(receivedId) } );

        return true;
    }

    public List<model> getNotes(String categoryName) {

        List<model> noteDetails = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from notes where category = ? ",new String[] {categoryName});


        while (cursor.moveToNext()){

            byte[] audios = null;


            int index = cursor.getColumnIndex(ID);
            int index1 = cursor.getColumnIndex(TITLE);
            int index2 = cursor.getColumnIndex(DESCRIPTION);
            int index3 = cursor.getColumnIndex(AUDIOS);
            int index4 = cursor.getColumnIndex(CATEGORY);
            int index5 = cursor.getColumnIndex(CATEGORYPOS);
            int index6 = cursor.getColumnIndex(DATE);


            //audios = cursor.getBlob(cursor.getColumnIndex(AUDIOS));

         //   Log.i("eeeeeeeeeeeee", String.valueOf(BitmapFactory.decodeByteArray(audios,0,audios.length)));
           // Log.i("aaaaaaaaaaaaaaaaaa", String.valueOf(audios));






            int id = cursor.getInt(index);
            String title = cursor.getString(index1);
            String desc = cursor.getString(index2);
            String audio = cursor.getString(index3);
            String category = cursor.getString(index4);
            int cpos = cursor.getInt(index5);
            String date = cursor.getString(index6);




            Log.i("descdesc", String.valueOf(date));



            model model = new model(title,desc,id,category,cpos,audio,date);
            noteDetails.add(0,model);
        }

        return noteDetails;

    }

    public int deleteData (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?",new String[] {String.valueOf(id)});
    }

    public List<model> alphaTitle(String categoryName){

        List<model> noteDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from notes where category = ? order by title desc",new String[] {categoryName});

        while (cursor.moveToNext()){

            int index = cursor.getColumnIndex(ID);
            int index1 = cursor.getColumnIndex(TITLE);
            int index2 = cursor.getColumnIndex(DESCRIPTION);
            int index3 = cursor.getColumnIndex(AUDIOS);
            int index4 = cursor.getColumnIndex(CATEGORY);
            int index5 = cursor.getColumnIndex(CATEGORYPOS);
            int index6 = cursor.getColumnIndex(DATE);

            int id = cursor.getInt(index);
            String title = cursor.getString(index1);
            String desc = cursor.getString(index2);
            String audio = cursor.getString(index3);
            String category = cursor.getString(index4);
            int cpos = cursor.getInt(index5);
            String date = cursor.getString(index6);

            model model = new model(title,desc,id,category,cpos,audio,date);
            noteDetails.add(0,model);
        }

        return noteDetails;

    }

    public List<model> alphaDate(){

        List<model> noteDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * \n" +
                "  from notes \n" +
                "  where date >= Datetime('2000-01-01 00:00:00') \n" +
                "  and date <= Datetime('2050-01-01 23:00:59')",null);

        while (cursor.moveToNext()){

            int index = cursor.getColumnIndex(ID);
            int index1 = cursor.getColumnIndex(TITLE);
            int index2 = cursor.getColumnIndex(DESCRIPTION);
            int index3 = cursor.getColumnIndex(AUDIOS);
            int index4 = cursor.getColumnIndex(CATEGORY);
            int index5 = cursor.getColumnIndex(CATEGORYPOS);
            int index6 = cursor.getColumnIndex(DATE);

            int id = cursor.getInt(index);
            String title = cursor.getString(index1);
            String desc = cursor.getString(index2);
            String audio = cursor.getString(index3);
            String category = cursor.getString(index4);
            int cpos = cursor.getInt(index5);
            String date = cursor.getString(index6);

            model model = new model(title,desc,id,category,cpos,audio,date);
            noteDetails.add(0,model);
        }

        return noteDetails;

    }
}
