package com.tiha.anphatsu.data.entities.condition;

public class CancelOrderCondition {
    private String SoDonHang;
    private Integer NguoiDungMobileID;
    private String LyDoHuy;

    public String getSoDonHang() {
        return SoDonHang;
    }

    public Integer getNguoiDungMobileID() {
        return NguoiDungMobileID;
    }

    public String getLyDoHuy() {
        return LyDoHuy;
    }

    public void setSoDonHang(String SoDonHang) {
        this.SoDonHang = SoDonHang;
    }

    public void setNguoiDungMobileID(Integer NguoiDungMobileID) {
        this.NguoiDungMobileID = NguoiDungMobileID;
    }

    public void setLyDoHuy(String LyDoHuy) {
        this.LyDoHuy = LyDoHuy;
    }
}
