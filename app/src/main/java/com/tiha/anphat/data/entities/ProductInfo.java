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
    private String Product_id; // Inventory
    private String MaThuongHieu;
    private Double SLNhieu ;
    private String ModifiedDate;
    private String ModifiedBy;
    private Double GiaBanLe;
    private Double GiaMua;
    private String Description;
    private Double Ton, Ton2;
    private String ImageBitMap;

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

    public Double getGiaBanLe() {
        return GiaBanLe;
    }

    public void setGiaBanLe(Double giaBanLe) {
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

    public Double getGiaMua() {
        return GiaMua;
    }

    public void setGiaMua(Double giaMua) {
        GiaMua = giaMua;
    }

    public Double getSLNhieu() {
        return SLNhieu;
    }

    public void setSLNhieu(Double SLNhieu) {
        this.SLNhieu = SLNhieu;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDonVitinh() {
        return DonVitinh;
    }

    public void setDonVitinh(String donVitinh) {
        DonVitinh = donVitinh;
    }

    public Double getTon() {
        return Ton;
    }

    public void setTon(Double ton) {
        Ton = ton;
    }

    public Double getTon2() {
        return Ton2;
    }

    public void setTon2(Double ton2) {
        Ton2 = ton2;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getImageBitMap() {
        return ImageBitMap;
    }

    public void setImageBitMap(String imageBitMap) {
        ImageBitMap = imageBitMap;
    }

    public String getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(String category_ID) {
        Category_ID = category_ID;
    }
}
