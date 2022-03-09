package com.tiha.anphatsu.data.entities.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class BookingInfo implements Serializable { // get don hang
    private String SoCt;
    private String Ngay;
    private String MSK;
    private String Supplier_ID;
    private String PTTT;
    private String DDBH = null;
    private String TenDDBH = null;
    private String Ghichu = null;
    private String Nguoigo;
    private String Tennguoimua;
    private String Diachi = null;
    private String TenDDnguoimua = null;
    private String Diengiai = null;
    private String Ngaygio;
    private float Solanin;
    private String MSDKTT;
    private String MSNguoigiao = null;
    private String TenNguoiGiao = null;
    private String Diachigiaohang;
    private String MaTrangThai;
    private String TenTrangThai;
    private String DTDD = null;
    private String SoPhieuVietTay;
    private String EMailPx = null;
    private List<ChiTietDonInfo> ListChiTietDonHang;

    public BookingInfo getBookingInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<BookingInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

//    private List<CartAfterInfo> ListChiTietDonHang;

    // Getter Methods

    public String getSoCt() {
        return SoCt;
    }

    public String getNgay() {
        return Ngay;
    }

    public String getMSK() {
        return MSK;
    }

    public String getSupplier_ID() {
        return Supplier_ID;
    }

    public String getPTTT() {
        return PTTT;
    }

    public String getDDBH() {
        return DDBH;
    }

    public String getTenDDBH() {
        return TenDDBH;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public String getNguoigo() {
        return Nguoigo;
    }

    public String getTennguoimua() {
        return Tennguoimua;
    }

    public String getDiachi() {
        return Diachi;
    }

    public String getTenDDnguoimua() {
        return TenDDnguoimua;
    }

    public String getDiengiai() {
        return Diengiai;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public float getSolanin() {
        return Solanin;
    }

    public String getMSDKTT() {
        return MSDKTT;
    }

    public String getMSNguoigiao() {
        return MSNguoigiao;
    }

    public String getTenNguoiGiao() {
        return TenNguoiGiao;
    }

    public String getDiachigiaohang() {
        return Diachigiaohang;
    }

    public String getMaTrangThai() {
        return MaTrangThai;
    }

    public String getTenTrangThai() {
        return TenTrangThai;
    }

    public String getDTDD() {
        return DTDD;
    }

    public String getSoPhieuVietTay() {
        return SoPhieuVietTay;
    }

    public String getEMailPx() {
        return EMailPx;
    }

    // Setter Methods

    public void setSoCt(String SoCt) {
        this.SoCt = SoCt;
    }

    public void setNgay(String Ngay) {
        this.Ngay = Ngay;
    }

    public void setMSK(String MSK) {
        this.MSK = MSK;
    }

    public void setSupplier_ID(String Supplier_ID) {
        this.Supplier_ID = Supplier_ID;
    }

    public void setPTTT(String PTTT) {
        this.PTTT = PTTT;
    }

    public void setDDBH(String DDBH) {
        this.DDBH = DDBH;
    }

    public void setTenDDBH(String TenDDBH) {
        this.TenDDBH = TenDDBH;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }

    public void setNguoigo(String Nguoigo) {
        this.Nguoigo = Nguoigo;
    }

    public void setTennguoimua(String Tennguoimua) {
        this.Tennguoimua = Tennguoimua;
    }

    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }

    public void setTenDDnguoimua(String TenDDnguoimua) {
        this.TenDDnguoimua = TenDDnguoimua;
    }

    public void setDiengiai(String Diengiai) {
        this.Diengiai = Diengiai;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }

    public void setSolanin(float Solanin) {
        this.Solanin = Solanin;
    }

    public void setMSDKTT(String MSDKTT) {
        this.MSDKTT = MSDKTT;
    }

    public void setMSNguoigiao(String MSNguoigiao) {
        this.MSNguoigiao = MSNguoigiao;
    }

    public void setTenNguoiGiao(String TenNguoiGiao) {
        this.TenNguoiGiao = TenNguoiGiao;
    }

    public void setDiachigiaohang(String Diachigiaohang) {
        this.Diachigiaohang = Diachigiaohang;
    }

    public void setMaTrangThai(String MaTrangThai) {
        this.MaTrangThai = MaTrangThai;
    }

    public void setTenTrangThai(String TenTrangThai) {
        this.TenTrangThai = TenTrangThai;
    }

    public void setDTDD(String DTDD) {
        this.DTDD = DTDD;
    }

    public void setSoPhieuVietTay(String SoPhieuVietTay) {
        this.SoPhieuVietTay = SoPhieuVietTay;
    }

    public void setEMailPx(String EMailPx) {
        this.EMailPx = EMailPx;
    }

    public List<ChiTietDonInfo> getListChiTietDonHang() {
        return ListChiTietDonHang;
    }

    public void setListChiTietDonHang(List<ChiTietDonInfo> listChiTietDonHang) {
        ListChiTietDonHang = listChiTietDonHang;
    }
}
