package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Staff implements Serializable {

    private int id;
    private String username;
    private String name;
    private String taskList;
    private String phone;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskList() {
        return taskList;
    }

    public void setTaskList(String taskList) {
        this.taskList = taskList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Staff(int id, String username, String name, String taskList, String phone) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.taskList = taskList;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", taskList='" + taskList + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
