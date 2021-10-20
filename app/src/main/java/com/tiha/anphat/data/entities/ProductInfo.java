package com.tiha.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductInfo {
    private String TeninHD;
    private String Loaitien;
    private String Donvitien;
    private String Category_ID;
    private String DonVitinh;
    private String Product_Name;
    private String Product_ID;
    private String MaThuongHieu;
    private Integer SLNhieu ;
    private String ModifiedDate;
    private String ModifiedBy;
    private Integer GiaBanLe;
    private Integer GiaMua;
    private String Description;

    public String getTeninHD() {
        return TeninHD;
    }

    public void setTeninHD(String teninHD) {
        TeninHD = teninHD;
    }

    public String getLoaitien() {
        return Loaitien;
    }

    public void setLoaitien(String loaitien) {
        Loaitien = loaitien;
    }

    public ProductInfo getProductInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ProductInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<ProductInfo> getListAllProduct(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<ProductInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getGiaBanLe() {
        return GiaBanLe;
    }

    public void setGiaBanLe(Integer giaBanLe) {
        GiaBanLe = giaBanLe;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public Integer getGiaMua() {
        return GiaMua;
    }

    public void setGiaMua(Integer giaMua) {
        GiaMua = giaMua;
    }

    public Integer getSLNhieu() {
        return SLNhieu;
    }

    public void setSLNhieu(Integer SLNhieu) {
        this.SLNhieu = SLNhieu;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
