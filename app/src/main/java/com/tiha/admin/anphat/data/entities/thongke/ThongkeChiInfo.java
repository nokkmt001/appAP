package com.tiha.admin.anphat.data.entities.thongke;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class ThongkeChiInfo {
    public String SoCt ;
    public Date ngay ;
    public String Supplier_ID ;
    public String Company_Name ;
    public String NguoiChiTien ;
    public String NguoiNhanTien ;
    public Double Ngoaite;
    public Double SoTien ;
    public String Vekhoan ;
    public String Kemtheo ;
    public String Ghichu ;
    public String Diachi ;
    public String Quy ;
    public String Nguoigo ;
    public String Phieu ;
    public String MaLySo ;
    public String LyDo ;
    public String NHOMLYDO ;
    public String RutNH ;
    public String NopNH ;
    public String Kho ;
    public String TenTrangThai ;
    public String LoaiTien ;
    public Integer Solanin ;
    public String SoPhieuVietTay ;
    public String NhomKH ;
    public String SoHD ;
    public Double TruKhac ;
    public Double US ;
    public Double Tygia ;
    public String MaChuoi ;
    public String TenChuoi ;

    public ArrayList<ThongkeChiInfo> getListThongkeChiInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<ThongkeChiInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
