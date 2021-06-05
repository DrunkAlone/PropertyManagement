package com.pro.propertymanagepro.entity;

public class Test {
    public Test(){}

    public Test(String name, String password) {
    }

    public Test(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    private Integer id;
    private String name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
