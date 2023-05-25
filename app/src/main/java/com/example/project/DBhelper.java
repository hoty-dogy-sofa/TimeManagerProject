package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ParentsDataBase.db";

    public DBhelper(@Nullable Context context) {
        super(context, "ParentsDataBase.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Parents(login TEXT, password TEXT, keyword TEXT, phrase TEXT, money INTEGER, task TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("DROP TABLE IF EXISTS Parents");
    }

    public void add(String name, String keyword, String password, String phrase, Integer money, String task){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("login", name);
        content.put("password", password);
        content.put("keyword", keyword);
        content.put("phrase", phrase);
        content.put("money", money);
        content.put("task", task);
        myDB.insert("Parents", null, content);
    }
    @SuppressLint("Range")
    public Object[] get(String name, String keyword, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.query("Parents", new String[] {"phrase, money, task"},
                "login = ? AND keyword = ? AND password = ?", new String[] {name, keyword, password},
                null, null, null);
        cursor.moveToFirst();
        return new Object[]{cursor.getInt(cursor.getColumnIndex("money")),
                cursor.getString(cursor.getColumnIndex("phrase")), cursor.getString(cursor.getColumnIndex("task"))};
    }
    public void change_pass(String name, String keyword, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("password", password);
        myDB.update("Parents", content, "login = ? AND keyword = ?", new String[] {name, keyword});
    }
    public void change_money(String name, Integer money){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("money", money);
        myDB.update("Parents", content, "login = ?", new String[] {name});
    }
    public void change_citata(String name, String phrase){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("phrase", phrase);
        myDB.update("Parents", content, "login = ?", new String[] {name});
    }
    public void change_task(String name, String task){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("task", task);
        myDB.update("Parents", content, "login = ?", new String[] {name});
    }
    public Boolean check_presence(String name){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM Parents WHERE login = ?", new String[] {name});
        if (cursor.getCount() == 0) return false;
        else return true;
    }
    public Boolean check_with_kw(String name, String kw){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM Parents WHERE login = ? AND keyword = ?", new String[] {name, kw});
        if (cursor.getCount() == 0) return false;
        else return true;
    }
    public Boolean check_with_pw(String name, String pw){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM Parents WHERE login = ? AND password = ?", new String[] {name, pw});
        if (cursor.getCount() == 0) return false;
        else return true;
    }


}
