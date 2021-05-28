package com.pro.propertymanagepro.entity;

import java.io.Serializable;

public class Vote implements Serializable {

    private int id;
    private String title;
    private String content;
    private String pubTime;
    private String endTime;
    private String nameList;
    private String status;
    private int approveNum;
    private int totalNum;

    public Vote(int id, String title, String content, String pubTime, String endTime, String nameList, String status, int approveNum, int totalNum) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pubTime = pubTime;
        this.endTime = endTime;
        this.nameList = nameList;
        this.status = status;
        this.approveNum = approveNum;
        this.totalNum = totalNum;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getApproveNum() {
        return approveNum;
    }

    public void setApproveNum(int approveNum) {
        this.approveNum = approveNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pubTime='" + pubTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", nameList='" + nameList + '\'' +
                ", status='" + status + '\'' +
                ", approveNum=" + approveNum +
                ", totalNum=" + totalNum +
                '}';
    }



}
