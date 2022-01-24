package com.tiha.admin.anphat.data.entities.condition;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class FilterReportCondition {
    public String UserName;
    public String TuNgay;
    public String DenNgay;
    public String ListKho;
    public String ListLoaiHang;
    public String ListNhanVien;
    public String ListChiNhanh;
    public String ListNhomKH;
    public Integer TrangThai;
    public Integer LoaiNhanVien;
    public String TimKiem;
    public String MaMauBaoCao;

    public Map<String, String> GetParameter() {
        Map<String, String> params = new HashMap<>();
        params.put("TuNgay", TuNgay);
        params.put("DenNgay", DenNgay);
        params.put("ListKho", TextUtils.isEmpty(ListKho) ? "" : ListKho );
        params.put("ListLoaiHang", TextUtils.isEmpty(ListLoaiHang) ? "" : ListLoaiHang);
        params.put("ListNhanVien", TextUtils.isEmpty(ListNhanVien) ? "" : ListNhanVien);
        params.put("TrangThai", String.valueOf(TrangThai));
        params.put("ListChiNhanh", TextUtils.isEmpty(ListChiNhanh) ? "" : ListChiNhanh);
        params.put("ListNhomKH", TextUtils.isEmpty(ListNhomKH) ? "" : ListNhomKH);
        params.put("UserName", UserName);
        params.put("LoaiNhanVien", String.valueOf(LoaiNhanVien));
        return params;
    }
}
