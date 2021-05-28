package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Deposit implements Serializable {
    private int id;
    private String username;
    private String type;
    private int amount;
    private String lastDate;

    public Deposit(int id, String username, String type, int amount, String lastDate) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.lastDate = lastDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", lastDate='" + lastDate + '\'' +
                '}';
    }
}
