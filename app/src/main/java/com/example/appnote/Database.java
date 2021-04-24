package com.example.appnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "note.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Users(id INTEGER primary key autoincrement, email TEXT, password TEXT, firstname TEXT, lastname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Users");
    }
    public Boolean insertData(Integer id, String email, String password, String firstName, String lastName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("password",password);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        long result = db.insert("Users", null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public Boolean UpdateData(Integer id, String email, String firstName, String lastName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String s = Integer.toString(id);
        contentValues.put("email", email);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        Cursor cursor = db.rawQuery("Select * from Users where id= ?", new String[] {s});
        if(cursor.getCount() > 0)
        {
            long result = db.update("Users", contentValues, "id=?", new String[] {s});
            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Boolean UpdatePass(Integer id, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String s = Integer.toString(id);
        contentValues.put("password", password );
        Cursor cursor = db.rawQuery("Select * from Users where id= ?", new String[] {s});
        if(cursor.getCount() > 0)
        {
            long result = db.update("Users", contentValues, "id=?", new String[] {s});
            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Boolean login(String email, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where email=? and password=?", new String[] {email, password});
        if(cursor.getCount() == 1) return true;
        else return false;
    }
    public Boolean checkMail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where email=?", new String[] {email});
        if(cursor.getCount()>0) return false;
        return true;
    }
    public Cursor getUser(Integer id)
    {
        String s = Integer.toString(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where id=?", new String[] {s});
        return cursor;
    }

    public Cursor getdata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users", null);
        return cursor;
    }
    public Boolean UpdateProfile(String email,String password, String firstName, String lastName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password",password);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        long result = db.insert("Users", null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
