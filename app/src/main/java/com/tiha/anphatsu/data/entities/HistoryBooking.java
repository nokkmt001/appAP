package com.tiha.anphatsu.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class HistoryBooking {
    public String SoCt;
    public Date Ngay;
    public String Supplier_ID;
    public float ThanhTien;
    public String SoPhieuVietTay;

    public HistoryBooking getHistoryBooking(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<HistoryBooking>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<HistoryBooking> getListHistoryBooking(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<HistoryBooking>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public String getSoCt() {
        return SoCt;
    }

    public Date getNgay() {
        return Ngay;
    }

    public String getSupplier_ID() {
        return Supplier_ID;
    }

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setSoCt(String SoCt) {
        this.SoCt = SoCt;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public void setSupplier_ID(String Supplier_ID) {
        this.Supplier_ID = Supplier_ID;
    }

    public void setThanhTien(float ThanhTien) {
        this.ThanhTien = ThanhTien;
    }
}
