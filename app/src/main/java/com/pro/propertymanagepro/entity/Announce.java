package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Announce implements Serializable {

    private int id;
    private String title;
    private String content;
    private String pubTime;

    public Announce(int id, String title, String content, String pubTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pubTime = pubTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    @Override
    public String toString() {
        return "Announce{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pubTime='" + pubTime + '\'' +
                '}';
    }

}
