package com.tiha.anphat.data.entities.location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tiha.anphat.data.entities.ResponseInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InsertLocationInfo {
    private Integer ID;
    private String UserName;
    private String Phone;
    private String Latitude;
    private String Longitude;
    private String OS;
    private String CreateDate;
    private String EventName;
    private String SoCT;
    private String LoaiPhieu;

    public InsertLocationInfo getInsertLocationInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<InsertLocationInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<InsertLocationInfo> getListInsertLocationInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<InsertLocationInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getID() {
        return ID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getOS() {
        return OS;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getEventName() {
        return EventName;
    }

    public String getSoCT() {
        return SoCT;
    }

    public String getLoaiPhieu() {
        return LoaiPhieu;
    }

    // Setter Methods

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public void setSoCT(String SoCT) {
        this.SoCT = SoCT;
    }

    public void setLoaiPhieu(String LoaiPhieu) {
        this.LoaiPhieu = LoaiPhieu;
    }
}
