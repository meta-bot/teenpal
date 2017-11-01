package com.example.anando.hciapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.anando.hciapp.adapters.emergencyContainer;
import com.example.anando.hciapp.adapters.planContainer;

import java.util.ArrayList;

/**
 * Created by anando on 30-Oct-17.
 */
public class planDB extends SQLiteOpenHelper {
    public static final String name = "planDB";
    public String table1 = "plan";
    public String table2 = "contact";
    private static final int version = 1;
    public planDB(Context context){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlString = "CREATE TABLE "+table1+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TIMER INTEGER);";
        sqLiteDatabase.execSQL(sqlString);

        sqlString = "CREATE TABLE "+table2+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CONTAC TEXT);";
        sqLiteDatabase.execSQL(sqlString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void pushPlan(String name , int time){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("TIMER", time);
        db.insert(table1,null,cv);

    }
    public void pushContact(String name , String number){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME",name);
        cv.put("CONTAC",number);
        db.insert(table2,null,cv);
    }
    public ArrayList<planContainer> getAllPlans(){
        ArrayList<planContainer> ret = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(table1,new String[]{"_id","NAME","TIMER"},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                planContainer pc = new planContainer(cursor.getString(1),cursor.getInt(0),cursor.getInt(2));
                ret.add(pc);
            }while (cursor.moveToNext());
        }
        return ret;
    }
    public ArrayList<emergencyContainer> getAllContact(){
        ArrayList<emergencyContainer> ret = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(table2,new String[]{"_id","NAME","CONTAC"},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                emergencyContainer ec = new emergencyContainer(cursor.getString(1),cursor.getString(2),cursor.getInt(0));
                ret.add(ec);
            }while (cursor.moveToNext());
        }
        return ret;
    }
}
