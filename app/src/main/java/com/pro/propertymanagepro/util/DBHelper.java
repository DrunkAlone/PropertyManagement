package com.pro.propertymanagepro.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String db_name = "User.db";
    private static final int version = 1;

    private final static String createTable1 = "create table " + "User" +
            "(id integer primary key AUTOINCREMENT," +
            "auth integer," +
            "username varchar(20)," +
            "pwd varchar(30)," +
            "name varchar(20)," +
            "age integer," +
            "gender integer," +
            "roomNo integer," +
            "phone varchar(11))";
    private final static String createTable2 = "create table " + "Repairs" +
            "(id integer primary key AUTOINCREMENT," +
            "username varchar(20)," +
            "project_name varchar(30)," +
            "date varchar(40)," +
            "room varchar(20)," +
            "name varchar(10)," +
            "phone varchar(11)," +
            "description varchar(255)," +
            "distribute_status int," +
            "handle_status int," +
            "distributer varchar(40)," +
            "handler varchar(40))";
    private final static String createTable3 = "create table " + "Moment" +
            "(id integer primary key AUTOINCREMENT," +
            "username varchar(20)," +
            "category int," +
            "content varchar(255)," +
            "picture varchar(255)," +
            "picNum int," +
            "pubTime varchar(100))";
    private final static String createTable4 = "create table " + "Advice" +
            "(id integer primary key AUTOINCREMENT," +
            "type int," +
            "username varchar(20)," +
            "name varchar(20)," +
            "phone varchar(11)," +
            "content varchar(255))";
    private final static String createTable5 = "create table " + "Announce" +
            "(id integer primary key AUTOINCREMENT," +
            "title varchar(100)," +
            "content varchar(255)," +
            "pubTime varchar(100))";
    private final static String createTable6 = "create table " + "Vote" +
            "(id integer primary key AUTOINCREMENT," +
            "title varchar(50)," +
            "content varchar(255)," +
            "pubTime varchar(100)," +
            "endTime varchar(100)," +
            "nameList varchar(255)," +
            "status varchar(30)," +
            "approveNum int," +
            "totalNum int)";
    private final static String createTable7 = "create table " + "Orders" +
            "(id integer primary key AUTOINCREMENT," +
            "orderID varchar(50)," +
            "username varchar(50)," +
            "amount int," +
            "type varchar(20)," +
            "payDate varchar(50)," +
            "status varchar(20))";
    private final static String createTable8 = "create table " + "Deposit" +
            "(id integer primary key AUTOINCREMENT," +
            "username varchar(50)," +
            "type varchar(20)," +
            "amount int," +
            "lastDate varchar(50))";
    private final static String createTable9 = "create table " + "Administrator" +
            "(id integer primary key AUTOINCREMENT," +
            "username varchar(40)," +
            "name varchar(30)," +
            "location varchar(255))";
    private final static String createTable10 = "create table " + "Staff" +
            "(id integer primary key AUTOINCREMENT," +
            "username varchar(40)," +
            "name varchar(30)," +
            "taskList varchar(255)," +
            "phone varchar(11))";

    public DBHelper(Context context){
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        db.execSQL(createTable5);
        db.execSQL(createTable6);
        db.execSQL(createTable7);
        db.execSQL(createTable8);
        db.execSQL(createTable9);
        db.execSQL(createTable10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Repairs");
        db.execSQL("DROP TABLE IF EXISTS Moment");
        db.execSQL("DROP TABLE IF EXISTS Advice");
        db.execSQL("DROP TABLE IF EXISTS Announce");
        db.execSQL("DROP TABLE IF EXISTS Vote");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS Deposit");
        db.execSQL("DROP TABLE IF EXISTS Administrator");
        db.execSQL("DROP TABLE IF EXISTS Staff");
        onCreate(db);
    }
}

