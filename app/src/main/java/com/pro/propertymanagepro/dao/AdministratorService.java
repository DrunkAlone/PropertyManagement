package com.pro.propertymanagepro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Administrator;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AdministratorService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public AdministratorService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addAdministrator(Administrator administrator){
        String sql = "insert into Administrator(username, name, location) values(?,?,?)";
        Object []ob = {
                administrator.getUsername(),
                administrator.getName(),
                administrator.getLocation()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Administrator getAdministrator(String username){
        String sql = "select * from Administrator where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            Administrator administrator = new Administrator(id, username, name, location);
            cursor.close();
            return administrator;
        }
        else{
            Log.e("tag", "没有符合条件的用户！");
            return null;
        }
    }

    public Administrator getAdministratorByID(int id){
        String sql = "select * from Administrator where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            Administrator administrator = new Administrator(id, username, name, location);
            cursor.close();
            return administrator;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Administrator> getAdministrators(){
        List<Administrator> list= new ArrayList<Administrator>();
        Cursor cursor = sdb.query("Administrator", null, null, null, null, null, "username DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            list.add(new Administrator(id, username, name, location));
        }
        return list;
    }

}
