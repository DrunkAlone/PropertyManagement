package com.pro.propertymanagepro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Moment;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MomentService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public MomentService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addMoment(Moment moment){
        String sql = "insert into Moment(username, category, content, picture, picNum, pubTime) values(?,?,?,?,?,?)";
        Object []ob = {
                moment.getUsername(),
                moment.getCategory(),
                moment.getContent(),
                moment.getPicture(),
                moment.getPicNum(),
                moment.getPubTime()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public List<Moment> getMomentByCategory(int category){
        List<Moment> list= new ArrayList<Moment>();
        String sql = "select * from Moment where category = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(category)});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));
            int picNum = cursor.getInt(cursor.getColumnIndex("picNum"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            Moment moment = new Moment(id, username, category, content, picture, picNum, pubTime);
            list.add(moment);
        }
        cursor.close();
        return list;
    }

    public List<Moment> getMomentByUser(String username){
        List<Moment> list= new ArrayList<Moment>();
        String sql = "select * from Moment where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int category = cursor.getInt(cursor.getColumnIndex("category"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));
            int picNum = cursor.getInt(cursor.getColumnIndex("picNum"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            Moment moment = new Moment(id, username, category, content, picture, picNum, pubTime);
            list.add(moment);
        }
        cursor.close();
        return list;
    }

    public List<Moment> getMoment(String word){
        List<Moment> list= new ArrayList<Moment>();
        String sql = "select * from Moment where username = ?";
        Cursor cursor = sdb.query("moment",null,  "content" + " like ?", new String[]{"%" + word + "%"}, null, null ,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            int category = cursor.getInt(cursor.getColumnIndex("category"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));
            int picNum = cursor.getInt(cursor.getColumnIndex("picNum"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            Moment moment = new Moment(id, username, category, content, picture, picNum, pubTime);
            list.add(moment);
        }
        cursor.close();
        return list;
    }

    public List<Moment> getAllMoments(){
        List<Moment> list= new ArrayList<Moment>();
        Cursor cursor = sdb.query("Moment", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            int category = cursor.getInt(cursor.getColumnIndex("category"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));
            int picNum = cursor.getInt(cursor.getColumnIndex("picNum"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            Moment moment = new Moment(id, username, category, content, picture, picNum, pubTime);
            list.add(moment);
        }
        return list;
    }
}
