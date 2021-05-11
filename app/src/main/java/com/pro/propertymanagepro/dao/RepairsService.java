package com.pro.propertymanagepro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Repairs;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class RepairsService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public RepairsService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addRepairs(Repairs repairs){
        String sql = "insert into Repairs(username, project_name, date, room, name, phone, description) values(?,?,?,?,?,?,?)";
        Object []ob = {
                repairs.getUsername(),
                repairs.getProject_name(),
                repairs.getDate(),
                repairs.getRoom(),
                repairs.getName(),
                repairs.getPhone(),
                repairs.getDescription()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Repairs getRepairbyUsername(String username){
        String sql = "select * from Repairs where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            Repairs repair = new Repairs(id, username, project_name, date, room, name, phone, description);
            cursor.close();
            return repair;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Repairs> getAllRepairs(){
        List<Repairs> list= new ArrayList<Repairs>();
        Cursor cursor = sdb.query("Repairs", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            Repairs repairs = new Repairs(id, username, project_name, date, room, name, phone, description);
            list.add(repairs);
        }
        return list;
    }
}
