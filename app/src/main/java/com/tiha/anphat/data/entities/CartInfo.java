package com.tiha.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tiha.anphat.data.entities.condition.CartCondition;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartInfo {
    private Integer ID;
    private Integer NguoiDungMobileID;
    private Integer SoLuong;
    private String ProductID;
    private String ProductName;
    private String GhiChu;
    private String CreateDate;
    private String ModifiedDate;
    private Integer DonGia;

    public Integer getNguoiDungMobileID() {
        return NguoiDungMobileID;
    }

    public void setNguoiDungMobileID(Integer nguoiDungMobileID) {
        NguoiDungMobileID = nguoiDungMobileID;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public CartInfo getCart(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<CartCondition>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<CartInfo> getListCart(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<CartInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Integer getDonGia() {
        return DonGia;
    }

    public void setDonGia(Integer donGia) {
        DonGia = donGia;
    }
}
