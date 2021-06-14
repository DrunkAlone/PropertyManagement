package com.pro.propertymanagepro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Staff;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class StaffService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public StaffService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addStaff(Staff staff){
        String sql = "insert into Staff(username, name, taskList, phone) values(?,?,?,?)";
        Object []ob = {
                staff.getUsername(),
                staff.getName(),
                staff.getTaskList(),
                staff.getPhone()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Staff getStaffByID(int id){
        String sql = "select * from Staff where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String taskList = cursor.getString(cursor.getColumnIndex("taskList"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Staff staff = new Staff(id, username, name, taskList, phone);
            cursor.close();
            return staff;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public Staff getStaff(String username){
        String sql = "select * from Staff where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String taskList = cursor.getString(cursor.getColumnIndex("taskList"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Staff staff = new Staff(id, username, name, taskList, phone);
            cursor.close();
            return staff;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Staff> getStaffs(){
        List<Staff> list= new ArrayList<Staff>();
        Cursor cursor = sdb.query("Staff", null, null, null, null, null, "username DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String taskList = cursor.getString(cursor.getColumnIndex("taskList"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Staff staff = new Staff(id, username, name, taskList, phone);
            list.add(staff);
        }
        return list;
    }

    public Boolean updateStaff(Staff staff){
        try{
            ContentValues values = new ContentValues();
            values.put("name", staff.getName());
            values.put("phone", staff.getPhone());
            sdb.update("Staff", values, "username = ?", new String[]{staff.getUsername()});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
