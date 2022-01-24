package com.tiha.admin.anphat.data.entities.condition;

public class InventoryCondition {
    private String listNhomHang;
    private String listKho;
    private String denNgay;
    private String loaiXem;
    private String userName;
    private String tenHangSearch;

    public String getListNhomHang() {
        return listNhomHang;
    }

    public String getListKho() {
        return listKho;
    }

    public String getDenNgay() {
        return denNgay;
    }

    public String getLoaiXem() {
        return loaiXem;
    }

    public String getUserName() {
        return userName;
    }

    public String getTenHangSearch() {
        return tenHangSearch;
    }

    // Setter Methods

    public void setListNhomHang(String listNhomHang) {
        this.listNhomHang = listNhomHang;
    }

    public void setListKho(String listKho) {
        this.listKho = listKho;
    }

    public void setDenNgay(String denNgay) {
        this.denNgay = denNgay;
    }

    public void setLoaiXem(String loaiXem) {
        this.loaiXem = loaiXem;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTenHangSearch(String tenHangSearch) {
        this.tenHangSearch = tenHangSearch;
    }
}
