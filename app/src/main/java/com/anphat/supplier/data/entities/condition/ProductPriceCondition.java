package com.anphat.supplier.data.entities.condition;

public class ProductPriceCondition {
    private String NguoiDungMobileID;
    private String ProductID;
    private String Ngay;
    private Integer SoLuong ;

    public String getNguoiDungMobileID() {
        return NguoiDungMobileID;
    }

    public void setNguoiDungMobileID(String nguoiDungMobileID) {
        NguoiDungMobileID = nguoiDungMobileID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }
}
