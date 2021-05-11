package com.pro.propertymanagepro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Announce;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AnnounceService{
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public AnnounceService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addAnnounce(Announce announce){
        String sql = "insert into Announce(title, content, pubTime) values(?,?,?)";
        Object []ob = {
                announce.getTitle(),
                announce.getContent(),
                announce.getPubTime()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Announce getAnnounceByID(int id){
        String sql = "select * from Announce where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            Announce announce = new Announce(id, title, content, pubTime);
            cursor.close();
            return announce;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Announce> getAllAnnoucements(){
        List<Announce> list= new ArrayList<Announce>();
        Cursor cursor = sdb.query("Announce", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            Announce announce = new Announce(id, title, content, pubTime);
            list.add(announce);
        }
        return list;
    }
}
