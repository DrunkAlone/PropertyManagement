package com.pro.propertymanagepro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Order;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public OrderService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addOrder(Order order){
        String sql = "insert into Orders(orderID, username, amount, type, payDate, status) values(?,?,?,?,?,?)";
        Object []ob = {
                order.getOrderID(),
                order.getUsername(),
                order.getAmount(),
                order.getType(),
                order.getPayDate(),
                order.getStatus()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Order getOrderByUsername(String username){
        String sql = "select * from Orders where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String orderID = cursor.getString(cursor.getColumnIndex("orderID"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String payDate = cursor.getString(cursor.getColumnIndex("payDate"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            Order order = new Order(id, orderID, username, amount, type, payDate, status);
            cursor.close();
            return order;
        }
        else{
            Log.e("tag", "没有符合条件的结果！");
            return null;
        }
    }

    public List<Order> getAllOrders(){
        List<Order> list= new ArrayList<Order>();
        Cursor cursor = sdb.query("Orders", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String orderID = cursor.getString(cursor.getColumnIndex("orderID"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String payDate = cursor.getString(cursor.getColumnIndex("payDate"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            Order order = new Order(id, orderID, username, amount, type, payDate, status);
            list.add(order);
        }
        return list;
    }

    public Boolean updateOrder(Order order){
        try{
            ContentValues values = new ContentValues();
            values.put("status", order.getStatus());
            sdb.update("Orders", values, "id = ?", new String[]{String.valueOf(order.getId())});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
