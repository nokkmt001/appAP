package com.tiha.anphatsu.data.entities.condition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartCondition implements Serializable {
    private Integer ID;
    private Integer NguoiDungMobileID;
    private Integer SoLuong;
    private String ProductID;
    private String GhiChu;
    private String CreateDate;
    private String ModifiedDate;

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

    public CartCondition getCart(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<CartCondition>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<CartCondition> getListCart(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<CartCondition>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
