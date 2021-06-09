package com.pro.propertymanagepro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.User;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public UserService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addUser(User user){
        String sql = "insert into User(auth, username, pwd, name, age, gender, roomNo, phone) values(?,?,?,?,?,?,?,?)";
        Object []ob = {
                user.getAuth(),
                user.getUsername(),
                user.getPwd(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getRoomNo(),
                user.getPhone()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Boolean updateUser(User user){
        try{
            ContentValues values = new ContentValues();
            values.put("pwd", user.getPwd());
            values.put("age", user.getAge());
            values.put("gender", user.getGender());
            values.put("roomNo", user.getRoomNo());
            sdb.update("User", values, "username = ?", new String[]{user.getUsername()});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Boolean deleteUser(String username){
        sdb.delete("User", "username = ?", new String[]{username});
        return true;
    }

    public User getUser(String username){
        String sql = "select * from User where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int auth = cursor.getInt(cursor.getColumnIndex("auth"));
            String password = cursor.getString(cursor.getColumnIndex("pwd"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            int gender = cursor.getInt(cursor.getColumnIndex("gender"));
            int roomNo = cursor.getInt(cursor.getColumnIndex("roomNo"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            User user = new User(id, auth, username, password, name, age, gender, roomNo, phone);
            cursor.close();
            return user;
        }
        else{
            Log.e("tag", "没有符合条件的用户！");
            return null;
        }
    }

    public User getUserByID(int id){
        String sql = "select * from User where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            int auth = cursor.getInt(cursor.getColumnIndex("auth"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("pwd"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            int gender = cursor.getInt(cursor.getColumnIndex("gender"));
            int roomNo = cursor.getInt(cursor.getColumnIndex("roomNo"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            User user = new User(id, auth, username, password, name, age, gender, roomNo, phone);
            cursor.close();
            return user;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<User> getUsers(){
        List<User> list= new ArrayList<User>();
        Cursor cursor = sdb.query("User", null, null, null, null, null, "username DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int auth = cursor.getInt(cursor.getColumnIndex("auth"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("pwd"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            int gender = cursor.getInt(cursor.getColumnIndex("gender"));
            int roomNo = cursor.getInt(cursor.getColumnIndex("roomNo"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            User user = new User(id, auth, username, password, name, age, gender, roomNo, phone);
            list.add(user);
        }
        return list;
    }
}
