package com.tiha.anphat.data.entities.presenteruser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class InsertPresenterInfo {
    private float ID;
    private float NguoiGioiThieuID;
    private float NguoiDuocGioiThieuID;
    private String CreateDate;

    // Getter Methods

    public InsertPresenterInfo getPresenterInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<InsertPresenterInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public float getID() {
        return ID;
    }

    public float getNguoiGioiThieuID() {
        return NguoiGioiThieuID;
    }

    public float getNguoiDuocGioiThieuID() {
        return NguoiDuocGioiThieuID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    // Setter Methods

    public void setID(float ID) {
        this.ID = ID;
    }

    public void setNguoiGioiThieuID(float NguoiGioiThieuID) {
        this.NguoiGioiThieuID = NguoiGioiThieuID;
    }

    public void setNguoiDuocGioiThieuID(float NguoiDuocGioiThieuID) {
        this.NguoiDuocGioiThieuID = NguoiDuocGioiThieuID;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }
}
