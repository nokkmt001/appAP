package com.tiha.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryInfo {
    private String Category_ID;
    private String Loaihang;
    private String Mota = null;
    private String Picture = null;
    private String Ngaygio;
    private String ModifiedDate = null;
    private String ModifiedBy = null;
    private String ParentID;
    private String NhanVienPhuTrach = null;
    private String KhoangNgayHen = null;
    private String NgayTonKhoToiDa = null;
    private String BienDoGiamGia = null;
    private String TimKiem;

    public CategoryInfo getCart(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<CategoryInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<CategoryInfo> getListCategory(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<CategoryInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    // Getter Methods

    public String getCategory_ID() {
        return Category_ID;
    }

    public String getLoaihang() {
        return Loaihang;
    }

    public String getMota() {
        return Mota;
    }

    public String getPicture() {
        return Picture;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public String getParentID() {
        return ParentID;
    }

    public String getNhanVienPhuTrach() {
        return NhanVienPhuTrach;
    }

    public String getKhoangNgayHen() {
        return KhoangNgayHen;
    }

    public String getNgayTonKhoToiDa() {
        return NgayTonKhoToiDa;
    }

    public String getBienDoGiamGia() {
        return BienDoGiamGia;
    }

    public String getTimKiem() {
        return TimKiem;
    }

    // Setter Methods

    public void setCategory_ID(String Category_ID) {
        this.Category_ID = Category_ID;
    }

    public void setLoaihang(String Loaihang) {
        this.Loaihang = Loaihang;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
    }

    public void setNhanVienPhuTrach(String NhanVienPhuTrach) {
        this.NhanVienPhuTrach = NhanVienPhuTrach;
    }

    public void setKhoangNgayHen(String KhoangNgayHen) {
        this.KhoangNgayHen = KhoangNgayHen;
    }

    public void setNgayTonKhoToiDa(String NgayTonKhoToiDa) {
        this.NgayTonKhoToiDa = NgayTonKhoToiDa;
    }

    public void setBienDoGiamGia(String BienDoGiamGia) {
        this.BienDoGiamGia = BienDoGiamGia;
    }

    public void setTimKiem(String TimKiem) {
        this.TimKiem = TimKiem;
    }
}
