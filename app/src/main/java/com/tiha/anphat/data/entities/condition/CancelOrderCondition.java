package com.tiha.anphat.data.entities.condition;

public class CancelOrderCondition {
    private String SoDonHang;
    private float NguoiDungMobileID;
    private String LyDoHuy;

    public String getSoDonHang() {
        return SoDonHang;
    }

    public float getNguoiDungMobileID() {
        return NguoiDungMobileID;
    }

    public String getLyDoHuy() {
        return LyDoHuy;
    }

    public void setSoDonHang(String SoDonHang) {
        this.SoDonHang = SoDonHang;
    }

    public void setNguoiDungMobileID(float NguoiDungMobileID) {
        this.NguoiDungMobileID = NguoiDungMobileID;
    }

    public void setLyDoHuy(String LyDoHuy) {
        this.LyDoHuy = LyDoHuy;
    }
}
