package com.magazine.magazine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseSystem extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "ViewersDB";

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "CREATE TABLE Viewers(id integer primary key,seen text NULL)";


    public DatabaseSystem(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public boolean insertContact (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("seen", name);
        db.insert("Viewers", null, contentValues);
        return true;
    }


    public Cursor getData(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Viewers where id="+id+"", null );
        return res;
    }


    public ArrayList<String> getAllViews() {

        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Viewers", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("seen")));
            res.moveToNext();
        }
        return array_list;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Viewers");
        onCreate(db);
    }

}
