package com.anphat.supplier.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

public class NewCustomer implements Serializable {
    private Integer NguoiDungMobileID;
    private String HoTen;
    private String SoDienThoai;
    private String DiaChi;
    private String  NgayGio;
    private Integer MaPIN;
    private String ModifiedDate;
    private String Password;

    public NewCustomer getNewCustomer(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<NewCustomer>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getNguoiDungMobileID() {
        return NguoiDungMobileID;
    }

    public void setNguoiDungMobileID(Integer nguoiDungMobileID) {
        NguoiDungMobileID = nguoiDungMobileID;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgayGio() {
        return NgayGio;
    }

    public void setNgayGio(String ngayGio) {
        NgayGio = ngayGio;
    }

    public Integer getMaPIN() {
        return MaPIN;
    }

    public void setMaPIN(Integer maPIN) {
        MaPIN = maPIN;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

//    public ArrayList<NewCustomer> getListResponse(String jsonString) {
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                .create();
//        Type type = new TypeToken<ArrayList<NewCustomer>>() {
//        }.getType();
//        return gson.fromJson(jsonString, type);
//    }
}


