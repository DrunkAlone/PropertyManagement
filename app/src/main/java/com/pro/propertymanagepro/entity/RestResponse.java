package com.pro.propertymanagepro.entity;

public class RestResponse {
    private int code;
    private Object data;

    public RestResponse(){}

    public RestResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

}
