package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Repairs implements Serializable {
    private int id;
    private String username;
    private String project_name;
    private String date;
    private String handled_date;
    private String finished_date;
    private String room;
    private String name;
    private String phone;
    private String description;
    private int distribute_status;
    private int handle_status;
    private int finished_status;
    private int score;
    private String distributer;
    private String handler;

    public Repairs(int id, String username, String project_name, String date, String handled_date, String finished_date, String room, String name, String phone, String description, int distribute_status, int handle_status, int finished_status, int score, String distributer, String handler) {
        this.id = id;
        this.username = username;
        this.project_name = project_name;
        this.date = date;
        this.handled_date = handled_date;
        this.finished_date = finished_date;
        this.room = room;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.distribute_status = distribute_status;
        this.handle_status = handle_status;
        this.finished_status = finished_status;
        this.score = score;
        this.distributer = distributer;
        this.handler = handler;
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

    public int getDistribute_status() {
        return distribute_status;
    }

    public void setDistribute_status(int distribute_status) {
        this.distribute_status = distribute_status;
    }

    public int getHandle_status() {
        return handle_status;
    }

    public void setHandle_status(int handle_status) {
        this.handle_status = handle_status;
    }

    public String getDistributer() {
        return distributer;
    }

    public void setDistributer(String distributer) {
        this.distributer = distributer;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getHandled_date() {
        return handled_date;
    }

    public void setHandled_date(String handled_date) {
        this.handled_date = handled_date;
    }

    public String getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(String finished_date) {
        this.finished_date = finished_date;
    }

    public int getFinished_status() {
        return finished_status;
    }

    public void setFinished_status(int finished_status) {
        this.finished_status = finished_status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Repairs{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", project_name='" + project_name + '\'' +
                ", date='" + date + '\'' +
                ", handled_date='" + handled_date + '\'' +
                ", finished_date='" + finished_date + '\'' +
                ", room='" + room + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", distribute_status=" + distribute_status +
                ", handle_status=" + handle_status +
                ", finished_status=" + finished_status +
                ", score=" + score +
                ", distributer='" + distributer + '\'' +
                ", handler='" + handler + '\'' +
                '}';
    }

}
