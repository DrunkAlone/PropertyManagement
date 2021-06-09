package com.pro.propertymanagepro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Advice;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AdviceService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public AdviceService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addAdvice(Advice advice){
        String sql = "insert into Advice(type, username, name, phone, content) values(?,?,?,?,?)";
        Object []ob = {
                advice.getType(),
                advice.getUsername(),
                advice.getName(),
                advice.getPhone(),
                advice.getContent()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public List<Advice> getAdviceByUser(String username){
        List<Advice> list= new ArrayList<Advice>();
        String sql = "select * from Advice where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            Advice advice = new Advice(id, type, username, name, phone, content);
            list.add(advice);
        }
        cursor.close();
        return list;
    }

    public Advice getAdviceByID(int id){
        String sql = "select * from Advice where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            Advice advice = new Advice(id, type, username, name, phone, content);
            cursor.close();
            return advice;
        }else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Advice> getAllAdvices(){
        List<Advice> list= new ArrayList<Advice>();
        Cursor cursor = sdb.query("Advice", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            Advice advice = new Advice(id, type, username, name, phone, content);
            list.add(advice);
        }
        return list;
    }
}
