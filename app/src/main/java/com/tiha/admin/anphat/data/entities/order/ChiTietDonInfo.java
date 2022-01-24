package com.tiha.admin.anphat.data.entities.order;

public class ChiTietDonInfo {
    private String Product_ID;
    private String Product_Name;
    private String Dvt;
    private String Ghichu;
    private Integer SL;
    private Integer dongia;
    private Integer LoaiDiscount;
    private Integer GiatriDiscount;
    private Integer Thue;
    private Integer Thanh_Tien;
    // Getter Methods 

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

    public Integer getSL() {
        return SL;
    }

    public Integer getDongia() {
        return dongia;
    }

    public Integer getLoaiDiscount() {
        return LoaiDiscount;
    }

    public Integer getGiatriDiscount() {
        return GiatriDiscount;
    }

    public Integer getThue() {
        return Thue;
    }

    public Integer getThanh_Tien() {
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

    public void setSL(Integer SL) {
        this.SL = SL;
    }

    public void setDongia(Integer dongia) {
        this.dongia = dongia;
    }

    public void setLoaiDiscount(Integer LoaiDiscount) {
        this.LoaiDiscount = LoaiDiscount;
    }

    public void setGiatriDiscount(Integer GiatriDiscount) {
        this.GiatriDiscount = GiatriDiscount;
    }

    public void setThue(Integer Thue) {
        this.Thue = Thue;
    }

    public void setThanh_Tien(Integer Thanh_Tien) {
        this.Thanh_Tien = Thanh_Tien;
    }
}
