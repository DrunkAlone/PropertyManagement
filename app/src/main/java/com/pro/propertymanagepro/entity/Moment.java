package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Moment implements Serializable {
    private int id;
    private String username;
    private int category;
    private String content;
    private String picture;
    private int picNum;
    private String pubTime;

    public Moment(int id, String username, int category, String content, String picture, int picNum, String pubTime) {
        this.id = id;
        this.username = username;
        this.category = category;
        this.content = content;
        this.picture = picture;
        this.picNum = picNum;
        this.pubTime = pubTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCategory() { return category; }

    public void setCategory(int category) {this.category = category;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", picture='" + picture + '\'' +
                ", picNum=" + picNum +
                ", pubTime='" + pubTime + '\'' +
                '}';
    }
}
