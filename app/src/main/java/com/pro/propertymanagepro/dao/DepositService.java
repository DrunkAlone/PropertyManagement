package com.pro.propertymanagepro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Deposit;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DepositService{
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public DepositService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addDeposit(Deposit deposit){
        String sql = "insert into Deposit(username, type, amount, lastDate) values(?,?,?,?)";
        Object []ob = {
                deposit.getUsername(),
                deposit.getType(),
                deposit.getAmount(),
                deposit.getLastDate()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Deposit getDepositByUsername(String username){
        String sql = "select * from Deposit where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String lastDate = cursor.getString(cursor.getColumnIndex("lastDate"));
            Deposit deposit = new Deposit(id, username, type, amount, lastDate);
            cursor.close();
            return deposit;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public Deposit getDepositByUsernameAndType(String username, String type){
        String sql = "select * from Deposit where username = ? and type = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username, type});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String lastDate = cursor.getString(cursor.getColumnIndex("lastDate"));
            Deposit deposit = new Deposit(id, username, type, amount, lastDate);
            cursor.close();
            return deposit;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Deposit> getAllDeposits(){
        List<Deposit> list= new ArrayList<Deposit>();
        Cursor cursor = sdb.query("Deposit", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String lastDate = cursor.getString(cursor.getColumnIndex("lastDate"));
            Deposit deposit = new Deposit(id, username, type, amount, lastDate);
            list.add(deposit);
        }
        return list;
    }

    public Boolean updateDeposit(Deposit deposit){
        try{
            ContentValues values = new ContentValues();
            values.put("amount", deposit.getAmount());
            values.put("lastDate", deposit.getLastDate());
            sdb.update("Deposit", values, "username = ? and type = ?", new String[]{deposit.getUsername(), deposit.getType()});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
