package com.anphat.supplier.data.entities.condition;

public class ProductCondition {
    private Integer Begin;
    private Integer End;
    private String NhomLoaiHang;
    private String UserName;
    private String TextSearch;

    public Integer getBegin() {
        return Begin;
    }

    public void setBegin(Integer begin) {
        Begin = begin;
    }

    public Integer getEnd() {
        return End;
    }

    public void setEnd(Integer end) {
        End = end;
    }

    public String getNhomLoaiHang() {
        return NhomLoaiHang;
    }

    public void setNhomLoaiHang(String nhomLoaiHang) {
        NhomLoaiHang = nhomLoaiHang;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTextSearch() {
        return TextSearch;
    }

    public void setTextSearch(String textSearch) {
        TextSearch = textSearch;
    }
}
