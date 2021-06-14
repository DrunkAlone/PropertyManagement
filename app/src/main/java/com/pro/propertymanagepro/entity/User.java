package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private int auth;
    private String username;
    private String pwd;
    private String name;
    private int age;
    private int gender;
    private int roomNo;
    private String phone;
    private String license;

    public User(int id, int auth, String username, String pwd, String name, int age, int gender, int roomNo, String phone, String license) {
        this.id = id;
        this.auth = auth;
        this.username = username;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.roomNo = roomNo;
        this.phone = phone;
        this.license = license;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuth() { return auth; }

    public void setAuth(int auth) { this.auth = auth; }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicense() { return license; }

    public void setLicense(String license) { this.license = license;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", auth='" + auth +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name=" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", roomNo=" + roomNo +
                ", phone=" + phone +
                ", license=" + license +
                '}';
    }


}
