package com.pro.propertymanagepro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pro.propertymanagepro.entity.Vote;
import com.pro.propertymanagepro.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class VoteService {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;
    public VoteService(Context context){
        dbHelper = new DBHelper(context);
        sdb = dbHelper.getReadableDatabase();
    }

    public Boolean addVote(Vote vote){
        String sql = "insert into Vote(title, content, pubTime, endTime, nameList, status, approveNum, totalNum) values(?,?,?,?,?,?,?,?)";
        Object []ob = {
                vote.getTitle(),
                vote.getContent(),
                vote.getPubTime(),
                vote.getEndTime(),
                vote.getNameList(),
                vote.getStatus(),
                vote.getApproveNum(),
                vote.getTotalNum()
        };
        try{
            sdb.execSQL(sql, ob);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Boolean updateVote(Vote vote){
        try{
            ContentValues values = new ContentValues();
            values.put("status", vote.getStatus());
            values.put("approveNum", vote.getApproveNum());
            values.put("totalNum", vote.getTotalNum());
            values.put("nameList", vote.getNameList());
            sdb.update("Vote", values, "id = ?", new String[]{String.valueOf(vote.getId())});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Vote getVote(int id){
        String sql = "select * from Vote where id = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
            String nameList = cursor.getString(cursor.getColumnIndex("nameList"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            int approveNum = cursor.getInt(cursor.getColumnIndex("approveNum"));
            int totalNum = cursor.getInt(cursor.getColumnIndex("totalNum"));
            Vote vote = new Vote(id, title, content, pubTime, endTime, nameList, status, approveNum, totalNum);
            cursor.close();
            return vote;
        }
        else{
            Log.e("tag", "没有符合条件的投票！");
            return null;
        }
    }

    public List<Vote> getVotes(){
        List<Vote> list= new ArrayList<Vote>();
        Cursor cursor = sdb.query("Vote", null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String pubTime = cursor.getString(cursor.getColumnIndex("pubTime"));
            String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
            String nameList = cursor.getString(cursor.getColumnIndex("nameList"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            int approveNum = cursor.getInt(cursor.getColumnIndex("approveNum"));
            int totalNum = cursor.getInt(cursor.getColumnIndex("totalNum"));
            Vote vote = new Vote(id, title, content, pubTime, endTime, nameList, status, approveNum, totalNum);
            list.add(vote);
        }
        return list;
    }

    public Boolean deleteVote(int id){
        sdb.delete("Vote", "id = ?", new String[]{String.valueOf(id)});
        return true;
    }
}
