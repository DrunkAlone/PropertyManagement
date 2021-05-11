package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Repairs implements Serializable {
    private int id;
    private String username;
    private String project_name;
    private String date;
    private String room;
    private String name;
    private String phone;
    private String description;

    public Repairs(int id, String username, String project_name, String date, String room, String name, String phone, String description) {
        this.id = id;
        this.username = username;
        this.project_name = project_name;
        this.date = date;
        this.room = room;
        this.name = name;
        this.phone = phone;
        this.description = description;
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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Repairs{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", project_name='" + project_name + '\'' +
                ", date='" + date + '\'' +
                ", room='" + room + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
