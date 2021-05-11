package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Advice implements Serializable {
    private int id;
    private int type;
    private String username;
    private String name;
    private String phone;
    private String content;

    @Override
    public String toString() {
        return "Advice{" +
                "id=" + id +
                ", type=" + type +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Advice(int id, int type, String username, String name, String phone, String content) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
