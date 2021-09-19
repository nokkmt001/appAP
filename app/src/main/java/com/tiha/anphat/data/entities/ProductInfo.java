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

}
