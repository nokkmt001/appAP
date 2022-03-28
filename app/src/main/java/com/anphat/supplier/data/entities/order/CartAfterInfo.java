package com.anphat.supplier.data.entities.order;

public class CartAfterInfo { // chi tiet don hang
    private String Product_ID;
    private String Product_Name;
    private String Dvt;
    private String Ghichu;
    private float SL;
    private float dongia;
    private float LoaiDiscount;
    private float GiatriDiscount;
    private float Thue;
    private float Thanh_Tien;

    public String getProduct_ID() {
        return Product_ID;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public String getDvt() {
        return Dvt;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public float getSL() {
        return SL;
    }

    public float getDongia() {
        return dongia;
    }

    public float getLoaiDiscount() {
        return LoaiDiscount;
    }

    public float getGiatriDiscount() {
        return GiatriDiscount;
    }

    public float getThue() {
        return Thue;
    }

    public float getThanh_Tien() {
        return Thanh_Tien;
    }

    // Setter Methods

    public void setProduct_ID(String Product_ID) {
        this.Product_ID = Product_ID;
    }

    public void setProduct_Name(String Product_Name) {
        this.Product_Name = Product_Name;
    }

    public void setDvt(String Dvt) {
        this.Dvt = Dvt;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }

    public void setSL(float SL) {
        this.SL = SL;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public void setLoaiDiscount(float LoaiDiscount) {
        this.LoaiDiscount = LoaiDiscount;
    }

    public void setGiatriDiscount(float GiatriDiscount) {
        this.GiatriDiscount = GiatriDiscount;
    }

    public void setThue(float Thue) {
        this.Thue = Thue;
    }

    public void setThanh_Tien(float Thanh_Tien) {
        this.Thanh_Tien = Thanh_Tien;
    }
}
