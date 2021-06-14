package com.pro.propertymanagepro.dao;

import android.content.ContentValues;
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
    private Context context;
    public RepairsService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addRepairs(Repairs repairs){
        String sql = "insert into Repairs(username, project_name, date, handled_date, finished_date, room, name, phone, description, distribute_status, handle_status, finished_status, score, distributer, handler) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object []ob = {
                repairs.getUsername(),
                repairs.getProject_name(),
                repairs.getDate(),
                repairs.getHandled_date(),
                repairs.getFinished_date(),
                repairs.getRoom(),
                repairs.getName(),
                repairs.getPhone(),
                repairs.getDescription(),
                repairs.getDistribute_status(),
                repairs.getHandle_status(),
                repairs.getFinished_status(),
                repairs.getScore(),
                repairs.getDistributer(),
                repairs.getHandler()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public List<Repairs> getRepairsbyUsername(String username){
        List<Repairs> list= new ArrayList<Repairs>();
        String sql = "select * from Repairs where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String handled_date = cursor.getString(cursor.getColumnIndex("handled_date"));
            String finished_date = cursor.getString(cursor.getColumnIndex("finished_date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int distribute_status = cursor.getInt(cursor.getColumnIndex("distribute_status"));
            int handle_status = cursor.getInt(cursor.getColumnIndex("handle_status"));
            int finished_status = cursor.getInt(cursor.getColumnIndex("finished_status"));
            int score = cursor.getInt(cursor.getColumnIndex("score"));
            String distributer = cursor.getString(cursor.getColumnIndex("distributer"));
            String handler = cursor.getString(cursor.getColumnIndex("handler"));
            Repairs repair = new Repairs(id, username, project_name, date, handled_date, finished_date, room, name, phone, description, distribute_status, handle_status, finished_status, score, distributer, handler);
            list.add(repair);
        }
        cursor.close();
        return list;
    }

    public Repairs getRepairbyID(int id){
        String sql = "select * from Repairs where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String handled_date = cursor.getString(cursor.getColumnIndex("handled_date"));
            String finished_date = cursor.getString(cursor.getColumnIndex("finished_date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int distribute_status = cursor.getInt(cursor.getColumnIndex("distribute_status"));
            int handle_status = cursor.getInt(cursor.getColumnIndex("handle_status"));
            int finished_status = cursor.getInt(cursor.getColumnIndex("finished_status"));
            int score = cursor.getInt(cursor.getColumnIndex("score"));
            String distributer = cursor.getString(cursor.getColumnIndex("distributer"));
            String handler = cursor.getString(cursor.getColumnIndex("handler"));
            Repairs repair = new Repairs(id, username, project_name, date, handled_date, finished_date, room, name, phone, description, distribute_status, handle_status, finished_status, score, distributer, handler);
            cursor.close();
            return repair;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Repairs> getUnhandledTasks(){
        List<Repairs> list= new ArrayList<Repairs>();
        String sql = "select * from Repairs where distribute_status = ? and handle_status = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{"1", "0"});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String handled_date = cursor.getString(cursor.getColumnIndex("handled_date"));
            String finished_date = cursor.getString(cursor.getColumnIndex("finished_date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int distribute_status = cursor.getInt(cursor.getColumnIndex("distribute_status"));
            int handle_status = cursor.getInt(cursor.getColumnIndex("handle_status"));
            int finished_status = cursor.getInt(cursor.getColumnIndex("finished_status"));
            int score = cursor.getInt(cursor.getColumnIndex("score"));
            String distributer = cursor.getString(cursor.getColumnIndex("distributer"));
            String handler = cursor.getString(cursor.getColumnIndex("handler"));
            Repairs repair = new Repairs(id, username, project_name, date, handled_date, finished_date, room, name, phone, description, distribute_status, handle_status, finished_status, score, distributer, handler);
            list.add(repair);
        }
        cursor.close();
        return list;
    }

    public List<Repairs> getTasksByHandler(String staffName){
        List<Repairs> list= new ArrayList<Repairs>();
        String sql = "select * from Repairs where handler = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(staffName)});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String handled_date = cursor.getString(cursor.getColumnIndex("handled_date"));
            String finished_date = cursor.getString(cursor.getColumnIndex("finished_date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int distribute_status = cursor.getInt(cursor.getColumnIndex("distribute_status"));
            int handle_status = cursor.getInt(cursor.getColumnIndex("handle_status"));
            int finished_status = cursor.getInt(cursor.getColumnIndex("finished_status"));
            int score = cursor.getInt(cursor.getColumnIndex("score"));
            String distributer = cursor.getString(cursor.getColumnIndex("distributer"));
            String handler = cursor.getString(cursor.getColumnIndex("handler"));
            Repairs repair = new Repairs(id, username, project_name, date, handled_date, finished_date, room, name, phone, description, distribute_status, handle_status, finished_status, score, distributer, handler);
            list.add(repair);
        }
        cursor.close();
        return list;
    }

    public List<Repairs> getAllRepairs(){
        List<Repairs> list= new ArrayList<Repairs>();
        Cursor cursor = sdb.query("Repairs", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String project_name = cursor.getString(cursor.getColumnIndex("project_name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String handled_date = cursor.getString(cursor.getColumnIndex("handled_date"));
            String finished_date = cursor.getString(cursor.getColumnIndex("finished_date"));
            String room = cursor.getString(cursor.getColumnIndex("room"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int distribute_status = cursor.getInt(cursor.getColumnIndex("distribute_status"));
            int handle_status = cursor.getInt(cursor.getColumnIndex("handle_status"));
            int finished_status = cursor.getInt(cursor.getColumnIndex("finished_status"));
            int score = cursor.getInt(cursor.getColumnIndex("score"));
            String distributer = cursor.getString(cursor.getColumnIndex("distributer"));
            String handler = cursor.getString(cursor.getColumnIndex("handler"));
            Repairs repair = new Repairs(id, username, project_name, date, handled_date, finished_date, room, name, phone, description, distribute_status, handle_status, finished_status, score, distributer, handler);
            list.add(repair);
        }
        return list;
    }

    public Boolean updateRepairs(Repairs repairs){
        try{
            ContentValues values = new ContentValues();
            values.put("distribute_status", repairs.getDistribute_status());
            values.put("distributer", repairs.getDistributer());
            values.put("handle_status", repairs.getHandle_status());
            values.put("handler", repairs.getHandler());
            values.put("finished_status", repairs.getFinished_status());
            values.put("handled_date", repairs.getHandled_date());
            values.put("finished_date", repairs.getFinished_date());
            values.put("score", repairs.getScore());
            sdb.update("Repairs", values, "id = ?", new String[]{String.valueOf(repairs.getId())});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
