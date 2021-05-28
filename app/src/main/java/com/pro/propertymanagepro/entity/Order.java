package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Order implements Serializable {

    private int id;
    private String orderID;
    private String username;
    private int amount;
    private String type;
    private String payDate;
    private String status;

    public Order(int id, String username, String orderID, int amount, String type, String payDate, String status) {
        this.id = id;
        this.username = username;
        this.orderID = orderID;
        this.amount = amount;
        this.type = type;
        this.payDate = payDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderID='" + orderID + '\'' +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", payDate='" + payDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
