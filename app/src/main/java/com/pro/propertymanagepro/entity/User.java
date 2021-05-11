package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String pwd;
    private int age;
    private int gender;
    private int roomNo;

    public User(int id, String name, String pwd, int age, int gender, int roomNo) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.age = age;
        this.gender = gender;
        this.roomNo = roomNo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", roomNo=" + roomNo +
                '}';
    }


}
