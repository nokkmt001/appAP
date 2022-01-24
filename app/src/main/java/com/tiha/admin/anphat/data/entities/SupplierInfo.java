package com.tiha.admin.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SupplierInfo {

    private String Supplier_ID;
    private String Company_Name = null;
    private String Company_NameE = null;
    private String Contact_Name;
    private String Phone = null;
    private String Fax = null;
    private String So;
    private String Duong;
    private String Phuong;
    private String Quan;
    private String Tinh;
    private float Gioihanno;
    private String PTTT;
    private float Cotgia;
    private String DunoMo = null;
    private String PHAITHU = null;
    private String PHAITRA = null;
    private String NgayMo = null;
    private String Duno = null;
    private String Ngay = null;
    private String DDBH = null;
    private String CS = null;
    private String EMail;
    private String MSTHUE;
    private String GID;
    private float Loai;
    private String Ngaygio;
    private String Songayno = null;
    private String DiachighiHD;
    private float Ngunghoatdong;
    private String Dacdiem;
    private String MSDKTT = null;
    private String NgayHoi = null;
    private String GC = null;
    private String HuongGiavon = null;
    private float Khongtinhno;
    private String Nguoithu = null;
    private String Lichchino = null;
    private String CotrongBangkethutien = null;
    private String Nopphieuthusau = null;
    private float Songayduocpheptrehen;
    private String DiaChiGH = null;
    private String Diachigiaohang2 = null;
    private String Diachigiaohang3 = null;
    private String Diachigiaohang4 = null;
    private String Nguoigo;
    private String VIP = null;
    private String PhanTramLaiNo = null;
    private String TaikhoanNH = null;
    private String TenNganHang = null;
    private String DiaChiNH = null;
    private String SoCMND = null;
    private String NgayCapCMND = null;
    private String NoiCap = null;
    private String NgaySinh = null;
    private String GioiTinh = null;
    private String NgheNghiep = null;
    private String ModifiedBy = null;
    private String ModifiedDate;
    private String FileAnh = null;
    private float ChuKyLayHang;
    private String Gia = null;
    private String GiaLon = null;
    private String ChiNhanh;
    private String PhanTramGiamGia = null;
    private String DanhXung = null;
    private String DTDD = null;
    private String MaSoBangGia;
    private String LoaiHinhThanhToan = null;
    private String NhomKhachHang = null;
    private String MaGiamSat = null;
    private String TKKH1 = null;
    private String TKKH2 = null;
    private String TKKH3 = null;
    private String TKKH4 = null;
    private float TiGia;
    private String TKNO = null;
    private String TKCO = null;
    private String SoLeDonGia = null;
    private String MaSP;
    private String SPSuDung = null;
    private String X;
    private String Y;
    private String LoaiHinhDN = null;
    private String SoDT1 = null;
    private String SoDT2 = null;
    private String SoKm;
    private String MaTK = null;
    private String GiamDoc = null;
    private String DTDDGD = null;
    private String EmailGD = null;
    private String ChucVuNLH = null;
    private String EmailNLH = null;
    private String WebSite = null;
    private String KhuVuc = null;
    private String FaceBook;
    private String TheVip = null;
    private String KhuPho = null;
    private float HuongKMBT;
    private String Picture = null;
    private float TatCaChiNhanh;
    private String MaKHKT = null;
    private String TimKiem = null;
    private String NguoiDuyet = null;
    private String ThuocCacNhom = null;
    private String MaNguon = null;
    private String GhiChu = null;
    private String MaNVQuanLy = null;
    private String NgaySinhCuaBe = null;
    private String DaGoiThuc = null;
    private String DonViID = null;
    private String NhanVienChamSocID = null;
    private String SoLanGoi = null;
    private String NhanVienGoi = null;
    private String NgayGioGoi = null;
    private String BenhVien = null;
    private String Line = null;
    private String ActionId = null;
    private String UniqueId = null;
    private String LineBam = null;
    private String TrangThaiCuocGoi = null;
    private String NguoiGioiThieu = null;
    private String CVID = null;
    private String NgayDangKy = null;
    private String NguoiBaoTro = null;
    private String ChiSoNhanhCon = null;
    private String MaQuocGia = null;
    private String MucShop = null;
    private String ContactNameE = null;
    private String ChucVuNLHE = null;
    private String MauSac = null;
    private String NgayHetHan = null;
    private String TrangThai = null;
    private String DoiTuongID = null;
    private String NguonID = null;
    private String KieuKhachHang = null;
    private String GoiThanhCong = null;
    private String NhanVienHoTro = null;
    private String MSPhong = null;
    private String TinhTrangHonNhan = null;
    private String QuanLyTrucTiepID = null;
    private String TenQuanLyTrucTiep = null;
    private String TrangThaiID = null;
    private String DanhGia = null;
    private String AnhCaNhan = null;
    private String AnhCMND = null;
    private String AnhMatTruocThe = null;
    private String NhanVienKinhDoanhID = null;
    private String TenNhanVienKinhDoanh = null;
    private String ChucVu = null;
    private String ListTenSanPham = null;
    private String ListMaSanPham = null;
    private String QuanHe = null;
    private String LoaiTheID = null;
    private String TongGhiChu = null;
    private String Loaitien = null;
    private String Ngoaite = null;
    private String TenNhanVienChamSoc = null;
    private String CICID = null;
    private String QuanHeNganHangID = null;
    private String CMND = null;
    private String CMNDNguoiGioiThieu = null;
    private String SDTNguoiGioiThieu = null;
    private String CongTyCongViec = null;
    private String LinhVucID = null;
    private String TenSanPham = null;
    private String NgayVay = null;
    private String DuNoVay = null;
    private String HanMucThe = null;
    private String Password = null;
    private String Data = null;
    private String FileName = null;
    private String ActiveWeb = null;
    private String MaXacThucWeb = null;


    // Getter Methods

    public String getSupplier_ID() {
        return Supplier_ID;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public String getCompany_NameE() {
        return Company_NameE;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getFax() {
        return Fax;
    }

    public String getSo() {
        return So;
    }

    public String getDuong() {
        return Duong;
    }

    public String getPhuong() {
        return Phuong;
    }

    public String getQuan() {
        return Quan;
    }

    public String getTinh() {
        return Tinh;
    }

    public float getGioihanno() {
        return Gioihanno;
    }

    public String getPTTT() {
        return PTTT;
    }

    public float getCotgia() {
        return Cotgia;
    }

    public String getDunoMo() {
        return DunoMo;
    }

    public String getPHAITHU() {
        return PHAITHU;
    }

    public String getPHAITRA() {
        return PHAITRA;
    }

    public String getNgayMo() {
        return NgayMo;
    }

    public String getDuno() {
        return Duno;
    }

    public String getNgay() {
        return Ngay;
    }

    public String getDDBH() {
        return DDBH;
    }

    public String getCS() {
        return CS;
    }

    public String getEMail() {
        return EMail;
    }

    public String getMSTHUE() {
        return MSTHUE;
    }

    public String getGID() {
        return GID;
    }

    public float getLoai() {
        return Loai;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public String getSongayno() {
        return Songayno;
    }

    public String getDiachighiHD() {
        return DiachighiHD;
    }

    public float getNgunghoatdong() {
        return Ngunghoatdong;
    }

    public String getDacdiem() {
        return Dacdiem;
    }

    public String getMSDKTT() {
        return MSDKTT;
    }

    public String getNgayHoi() {
        return NgayHoi;
    }

    public String getGC() {
        return GC;
    }

    public String getHuongGiavon() {
        return HuongGiavon;
    }

    public float getKhongtinhno() {
        return Khongtinhno;
    }

    public String getNguoithu() {
        return Nguoithu;
    }

    public String getLichchino() {
        return Lichchino;
    }

    public String getCotrongBangkethutien() {
        return CotrongBangkethutien;
    }

    public String getNopphieuthusau() {
        return Nopphieuthusau;
    }

    public float getSongayduocpheptrehen() {
        return Songayduocpheptrehen;
    }

    public String getDiaChiGH() {
        return DiaChiGH;
    }

    public String getDiachigiaohang2() {
        return Diachigiaohang2;
    }

    public String getDiachigiaohang3() {
        return Diachigiaohang3;
    }

    public String getDiachigiaohang4() {
        return Diachigiaohang4;
    }

    public String getNguoigo() {
        return Nguoigo;
    }

    public String getVIP() {
        return VIP;
    }

    public String getPhanTramLaiNo() {
        return PhanTramLaiNo;
    }

    public String getTaikhoanNH() {
        return TaikhoanNH;
    }

    public String getTenNganHang() {
        return TenNganHang;
    }

    public String getDiaChiNH() {
        return DiaChiNH;
    }

    public String getSoCMND() {
        return SoCMND;
    }

    public String getNgayCapCMND() {
        return NgayCapCMND;
    }

    public String getNoiCap() {
        return NoiCap;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getNgheNghiep() {
        return NgheNghiep;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public String getFileAnh() {
        return FileAnh;
    }

    public float getChuKyLayHang() {
        return ChuKyLayHang;
    }

    public String getGia() {
        return Gia;
    }

    public String getGiaLon() {
        return GiaLon;
    }

    public String getChiNhanh() {
        return ChiNhanh;
    }

    public String getPhanTramGiamGia() {
        return PhanTramGiamGia;
    }

    public String getDanhXung() {
        return DanhXung;
    }

    public String getDTDD() {
        return DTDD;
    }

    public String getMaSoBangGia() {
        return MaSoBangGia;
    }

    public String getLoaiHinhThanhToan() {
        return LoaiHinhThanhToan;
    }

    public String getNhomKhachHang() {
        return NhomKhachHang;
    }

    public String getMaGiamSat() {
        return MaGiamSat;
    }

    public String getTKKH1() {
        return TKKH1;
    }

    public String getTKKH2() {
        return TKKH2;
    }

    public String getTKKH3() {
        return TKKH3;
    }

    public String getTKKH4() {
        return TKKH4;
    }

    public float getTiGia() {
        return TiGia;
    }

    public String getTKNO() {
        return TKNO;
    }

    public String getTKCO() {
        return TKCO;
    }

    public String getSoLeDonGia() {
        return SoLeDonGia;
    }

    public String getMaSP() {
        return MaSP;
    }

    public String getSPSuDung() {
        return SPSuDung;
    }

    public String getX() {
        return X;
    }

    public String getY() {
        return Y;
    }

    public String getLoaiHinhDN() {
        return LoaiHinhDN;
    }

    public String getSoDT1() {
        return SoDT1;
    }

    public String getSoDT2() {
        return SoDT2;
    }

    public String getSoKm() {
        return SoKm;
    }

    public String getMaTK() {
        return MaTK;
    }

    public String getGiamDoc() {
        return GiamDoc;
    }

    public String getDTDDGD() {
        return DTDDGD;
    }

    public String getEmailGD() {
        return EmailGD;
    }

    public String getChucVuNLH() {
        return ChucVuNLH;
    }

    public String getEmailNLH() {
        return EmailNLH;
    }

    public String getWebSite() {
        return WebSite;
    }

    public String getKhuVuc() {
        return KhuVuc;
    }

    public String getFaceBook() {
        return FaceBook;
    }

    public String getTheVip() {
        return TheVip;
    }

    public String getKhuPho() {
        return KhuPho;
    }

    public float getHuongKMBT() {
        return HuongKMBT;
    }

    public String getPicture() {
        return Picture;
    }

    public float getTatCaChiNhanh() {
        return TatCaChiNhanh;
    }

    public String getMaKHKT() {
        return MaKHKT;
    }

    public String getTimKiem() {
        return TimKiem;
    }

    public String getNguoiDuyet() {
        return NguoiDuyet;
    }

    public String getThuocCacNhom() {
        return ThuocCacNhom;
    }

    public String getMaNguon() {
        return MaNguon;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public String getMaNVQuanLy() {
        return MaNVQuanLy;
    }

    public String getNgaySinhCuaBe() {
        return NgaySinhCuaBe;
    }

    public String getDaGoiThuc() {
        return DaGoiThuc;
    }

    public String getDonViID() {
        return DonViID;
    }

    public String getNhanVienChamSocID() {
        return NhanVienChamSocID;
    }

    public String getSoLanGoi() {
        return SoLanGoi;
    }

    public String getNhanVienGoi() {
        return NhanVienGoi;
    }

    public String getNgayGioGoi() {
        return NgayGioGoi;
    }

    public String getBenhVien() {
        return BenhVien;
    }

    public String getLine() {
        return Line;
    }

    public String getActionId() {
        return ActionId;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public String getLineBam() {
        return LineBam;
    }

    public String getTrangThaiCuocGoi() {
        return TrangThaiCuocGoi;
    }

    public String getNguoiGioiThieu() {
        return NguoiGioiThieu;
    }

    public String getCVID() {
        return CVID;
    }

    public String getNgayDangKy() {
        return NgayDangKy;
    }

    public String getNguoiBaoTro() {
        return NguoiBaoTro;
    }

    public String getChiSoNhanhCon() {
        return ChiSoNhanhCon;
    }

    public String getMaQuocGia() {
        return MaQuocGia;
    }

    public String getMucShop() {
        return MucShop;
    }

    public String getContactNameE() {
        return ContactNameE;
    }

    public String getChucVuNLHE() {
        return ChucVuNLHE;
    }

    public String getMauSac() {
        return MauSac;
    }

    public String getNgayHetHan() {
        return NgayHetHan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getDoiTuongID() {
        return DoiTuongID;
    }

    public String getNguonID() {
        return NguonID;
    }

    public String getKieuKhachHang() {
        return KieuKhachHang;
    }

    public String getGoiThanhCong() {
        return GoiThanhCong;
    }

    public String getNhanVienHoTro() {
        return NhanVienHoTro;
    }

    public String getMSPhong() {
        return MSPhong;
    }

    public String getTinhTrangHonNhan() {
        return TinhTrangHonNhan;
    }

    public String getQuanLyTrucTiepID() {
        return QuanLyTrucTiepID;
    }

    public String getTenQuanLyTrucTiep() {
        return TenQuanLyTrucTiep;
    }

    public String getTrangThaiID() {
        return TrangThaiID;
    }

    public String getDanhGia() {
        return DanhGia;
    }

    public String getAnhCaNhan() {
        return AnhCaNhan;
    }

    public String getAnhCMND() {
        return AnhCMND;
    }

    public String getAnhMatTruocThe() {
        return AnhMatTruocThe;
    }

    public String getNhanVienKinhDoanhID() {
        return NhanVienKinhDoanhID;
    }

    public String getTenNhanVienKinhDoanh() {
        return TenNhanVienKinhDoanh;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public String getListTenSanPham() {
        return ListTenSanPham;
    }

    public String getListMaSanPham() {
        return ListMaSanPham;
    }

    public String getQuanHe() {
        return QuanHe;
    }

    public String getLoaiTheID() {
        return LoaiTheID;
    }

    public String getTongGhiChu() {
        return TongGhiChu;
    }

    public String getLoaitien() {
        return Loaitien;
    }

    public String getNgoaite() {
        return Ngoaite;
    }

    public String getTenNhanVienChamSoc() {
        return TenNhanVienChamSoc;
    }

    public String getCICID() {
        return CICID;
    }

    public String getQuanHeNganHangID() {
        return QuanHeNganHangID;
    }

    public String getCMND() {
        return CMND;
    }

    public String getCMNDNguoiGioiThieu() {
        return CMNDNguoiGioiThieu;
    }

    public String getSDTNguoiGioiThieu() {
        return SDTNguoiGioiThieu;
    }

    public String getCongTyCongViec() {
        return CongTyCongViec;
    }

    public String getLinhVucID() {
        return LinhVucID;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public String getNgayVay() {
        return NgayVay;
    }

    public String getDuNoVay() {
        return DuNoVay;
    }

    public String getHanMucThe() {
        return HanMucThe;
    }

    public String getPassword() {
        return Password;
    }

    public String getData() {
        return Data;
    }

    public String getFileName() {
        return FileName;
    }

    public String getActiveWeb() {
        return ActiveWeb;
    }

    public String getMaXacThucWeb() {
        return MaXacThucWeb;
    }

    // Setter Methods

    public void setSupplier_ID(String Supplier_ID) {
        this.Supplier_ID = Supplier_ID;
    }

    public void setCompany_Name(String Company_Name) {
        this.Company_Name = Company_Name;
    }

    public void setCompany_NameE(String Company_NameE) {
        this.Company_NameE = Company_NameE;
    }

    public void setContact_Name(String Contact_Name) {
        this.Contact_Name = Contact_Name;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public void setSo(String So) {
        this.So = So;
    }

    public void setDuong(String Duong) {
        this.Duong = Duong;
    }

    public void setPhuong(String Phuong) {
        this.Phuong = Phuong;
    }

    public void setQuan(String Quan) {
        this.Quan = Quan;
    }

    public void setTinh(String Tinh) {
        this.Tinh = Tinh;
    }

    public void setGioihanno(float Gioihanno) {
        this.Gioihanno = Gioihanno;
    }

    public void setPTTT(String PTTT) {
        this.PTTT = PTTT;
    }

    public void setCotgia(float Cotgia) {
        this.Cotgia = Cotgia;
    }

    public void setDunoMo(String DunoMo) {
        this.DunoMo = DunoMo;
    }

    public void setPHAITHU(String PHAITHU) {
        this.PHAITHU = PHAITHU;
    }

    public void setPHAITRA(String PHAITRA) {
        this.PHAITRA = PHAITRA;
    }

    public void setNgayMo(String NgayMo) {
        this.NgayMo = NgayMo;
    }

    public void setDuno(String Duno) {
        this.Duno = Duno;
    }

    public void setNgay(String Ngay) {
        this.Ngay = Ngay;
    }

    public void setDDBH(String DDBH) {
        this.DDBH = DDBH;
    }

    public void setCS(String CS) {
        this.CS = CS;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public void setMSTHUE(String MSTHUE) {
        this.MSTHUE = MSTHUE;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public void setLoai(float Loai) {
        this.Loai = Loai;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }

    public void setSongayno(String Songayno) {
        this.Songayno = Songayno;
    }

    public void setDiachighiHD(String DiachighiHD) {
        this.DiachighiHD = DiachighiHD;
    }

    public void setNgunghoatdong(float Ngunghoatdong) {
        this.Ngunghoatdong = Ngunghoatdong;
    }

    public void setDacdiem(String Dacdiem) {
        this.Dacdiem = Dacdiem;
    }

    public void setMSDKTT(String MSDKTT) {
        this.MSDKTT = MSDKTT;
    }

    public void setNgayHoi(String NgayHoi) {
        this.NgayHoi = NgayHoi;
    }

    public void setGC(String GC) {
        this.GC = GC;
    }

    public void setHuongGiavon(String HuongGiavon) {
        this.HuongGiavon = HuongGiavon;
    }

    public void setKhongtinhno(float Khongtinhno) {
        this.Khongtinhno = Khongtinhno;
    }

    public void setNguoithu(String Nguoithu) {
        this.Nguoithu = Nguoithu;
    }

    public void setLichchino(String Lichchino) {
        this.Lichchino = Lichchino;
    }

    public void setCotrongBangkethutien(String CotrongBangkethutien) {
        this.CotrongBangkethutien = CotrongBangkethutien;
    }

    public void setNopphieuthusau(String Nopphieuthusau) {
        this.Nopphieuthusau = Nopphieuthusau;
    }

    public void setSongayduocpheptrehen(float Songayduocpheptrehen) {
        this.Songayduocpheptrehen = Songayduocpheptrehen;
    }

    public void setDiaChiGH(String DiaChiGH) {
        this.DiaChiGH = DiaChiGH;
    }

    public void setDiachigiaohang2(String Diachigiaohang2) {
        this.Diachigiaohang2 = Diachigiaohang2;
    }

    public void setDiachigiaohang3(String Diachigiaohang3) {
        this.Diachigiaohang3 = Diachigiaohang3;
    }

    public void setDiachigiaohang4(String Diachigiaohang4) {
        this.Diachigiaohang4 = Diachigiaohang4;
    }

    public void setNguoigo(String Nguoigo) {
        this.Nguoigo = Nguoigo;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;
    }

    public void setPhanTramLaiNo(String PhanTramLaiNo) {
        this.PhanTramLaiNo = PhanTramLaiNo;
    }

    public void setTaikhoanNH(String TaikhoanNH) {
        this.TaikhoanNH = TaikhoanNH;
    }

    public void setTenNganHang(String TenNganHang) {
        this.TenNganHang = TenNganHang;
    }

    public void setDiaChiNH(String DiaChiNH) {
        this.DiaChiNH = DiaChiNH;
    }

    public void setSoCMND(String SoCMND) {
        this.SoCMND = SoCMND;
    }

    public void setNgayCapCMND(String NgayCapCMND) {
        this.NgayCapCMND = NgayCapCMND;
    }

    public void setNoiCap(String NoiCap) {
        this.NoiCap = NoiCap;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public void setNgheNghiep(String NgheNghiep) {
        this.NgheNghiep = NgheNghiep;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public void setFileAnh(String FileAnh) {
        this.FileAnh = FileAnh;
    }

    public void setChuKyLayHang(float ChuKyLayHang) {
        this.ChuKyLayHang = ChuKyLayHang;
    }

    public void setGia(String Gia) {
        this.Gia = Gia;
    }

    public void setGiaLon(String GiaLon) {
        this.GiaLon = GiaLon;
    }

    public void setChiNhanh(String ChiNhanh) {
        this.ChiNhanh = ChiNhanh;
    }

    public void setPhanTramGiamGia(String PhanTramGiamGia) {
        this.PhanTramGiamGia = PhanTramGiamGia;
    }

    public void setDanhXung(String DanhXung) {
        this.DanhXung = DanhXung;
    }

    public void setDTDD(String DTDD) {
        this.DTDD = DTDD;
    }

    public void setMaSoBangGia(String MaSoBangGia) {
        this.MaSoBangGia = MaSoBangGia;
    }

    public void setLoaiHinhThanhToan(String LoaiHinhThanhToan) {
        this.LoaiHinhThanhToan = LoaiHinhThanhToan;
    }

    public void setNhomKhachHang(String NhomKhachHang) {
        this.NhomKhachHang = NhomKhachHang;
    }

    public void setMaGiamSat(String MaGiamSat) {
        this.MaGiamSat = MaGiamSat;
    }

    public void setTKKH1(String TKKH1) {
        this.TKKH1 = TKKH1;
    }

    public void setTKKH2(String TKKH2) {
        this.TKKH2 = TKKH2;
    }

    public void setTKKH3(String TKKH3) {
        this.TKKH3 = TKKH3;
    }

    public void setTKKH4(String TKKH4) {
        this.TKKH4 = TKKH4;
    }

    public void setTiGia(float TiGia) {
        this.TiGia = TiGia;
    }

    public void setTKNO(String TKNO) {
        this.TKNO = TKNO;
    }

    public void setTKCO(String TKCO) {
        this.TKCO = TKCO;
    }

    public void setSoLeDonGia(String SoLeDonGia) {
        this.SoLeDonGia = SoLeDonGia;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public void setSPSuDung(String SPSuDung) {
        this.SPSuDung = SPSuDung;
    }

    public void setX(String X) {
        this.X = X;
    }

    public void setY(String Y) {
        this.Y = Y;
    }

    public void setLoaiHinhDN(String LoaiHinhDN) {
        this.LoaiHinhDN = LoaiHinhDN;
    }

    public void setSoDT1(String SoDT1) {
        this.SoDT1 = SoDT1;
    }

    public void setSoDT2(String SoDT2) {
        this.SoDT2 = SoDT2;
    }

    public void setSoKm(String SoKm) {
        this.SoKm = SoKm;
    }

    public void setMaTK(String MaTK) {
        this.MaTK = MaTK;
    }

    public void setGiamDoc(String GiamDoc) {
        this.GiamDoc = GiamDoc;
    }

    public void setDTDDGD(String DTDDGD) {
        this.DTDDGD = DTDDGD;
    }

    public void setEmailGD(String EmailGD) {
        this.EmailGD = EmailGD;
    }

    public void setChucVuNLH(String ChucVuNLH) {
        this.ChucVuNLH = ChucVuNLH;
    }

    public void setEmailNLH(String EmailNLH) {
        this.EmailNLH = EmailNLH;
    }

    public void setWebSite(String WebSite) {
        this.WebSite = WebSite;
    }

    public void setKhuVuc(String KhuVuc) {
        this.KhuVuc = KhuVuc;
    }

    public void setFaceBook(String FaceBook) {
        this.FaceBook = FaceBook;
    }

    public void setTheVip(String TheVip) {
        this.TheVip = TheVip;
    }

    public void setKhuPho(String KhuPho) {
        this.KhuPho = KhuPho;
    }

    public void setHuongKMBT(float HuongKMBT) {
        this.HuongKMBT = HuongKMBT;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }

    public void setTatCaChiNhanh(float TatCaChiNhanh) {
        this.TatCaChiNhanh = TatCaChiNhanh;
    }

    public void setMaKHKT(String MaKHKT) {
        this.MaKHKT = MaKHKT;
    }

    public void setTimKiem(String TimKiem) {
        this.TimKiem = TimKiem;
    }

    public void setNguoiDuyet(String NguoiDuyet) {
        this.NguoiDuyet = NguoiDuyet;
    }

    public void setThuocCacNhom(String ThuocCacNhom) {
        this.ThuocCacNhom = ThuocCacNhom;
    }

    public void setMaNguon(String MaNguon) {
        this.MaNguon = MaNguon;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public void setMaNVQuanLy(String MaNVQuanLy) {
        this.MaNVQuanLy = MaNVQuanLy;
    }

    public void setNgaySinhCuaBe(String NgaySinhCuaBe) {
        this.NgaySinhCuaBe = NgaySinhCuaBe;
    }

    public void setDaGoiThuc(String DaGoiThuc) {
        this.DaGoiThuc = DaGoiThuc;
    }

    public void setDonViID(String DonViID) {
        this.DonViID = DonViID;
    }

    public void setNhanVienChamSocID(String NhanVienChamSocID) {
        this.NhanVienChamSocID = NhanVienChamSocID;
    }

    public void setSoLanGoi(String SoLanGoi) {
        this.SoLanGoi = SoLanGoi;
    }

    public void setNhanVienGoi(String NhanVienGoi) {
        this.NhanVienGoi = NhanVienGoi;
    }

    public void setNgayGioGoi(String NgayGioGoi) {
        this.NgayGioGoi = NgayGioGoi;
    }

    public void setBenhVien(String BenhVien) {
        this.BenhVien = BenhVien;
    }

    public void setLine(String Line) {
        this.Line = Line;
    }

    public void setActionId(String ActionId) {
        this.ActionId = ActionId;
    }

    public void setUniqueId(String UniqueId) {
        this.UniqueId = UniqueId;
    }

    public void setLineBam(String LineBam) {
        this.LineBam = LineBam;
    }

    public void setTrangThaiCuocGoi(String TrangThaiCuocGoi) {
        this.TrangThaiCuocGoi = TrangThaiCuocGoi;
    }

    public void setNguoiGioiThieu(String NguoiGioiThieu) {
        this.NguoiGioiThieu = NguoiGioiThieu;
    }

    public void setCVID(String CVID) {
        this.CVID = CVID;
    }

    public void setNgayDangKy(String NgayDangKy) {
        this.NgayDangKy = NgayDangKy;
    }

    public void setNguoiBaoTro(String NguoiBaoTro) {
        this.NguoiBaoTro = NguoiBaoTro;
    }

    public void setChiSoNhanhCon(String ChiSoNhanhCon) {
        this.ChiSoNhanhCon = ChiSoNhanhCon;
    }

    public void setMaQuocGia(String MaQuocGia) {
        this.MaQuocGia = MaQuocGia;
    }

    public void setMucShop(String MucShop) {
        this.MucShop = MucShop;
    }

    public void setContactNameE(String ContactNameE) {
        this.ContactNameE = ContactNameE;
    }

    public void setChucVuNLHE(String ChucVuNLHE) {
        this.ChucVuNLHE = ChucVuNLHE;
    }

    public void setMauSac(String MauSac) {
        this.MauSac = MauSac;
    }

    public void setNgayHetHan(String NgayHetHan) {
        this.NgayHetHan = NgayHetHan;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public void setDoiTuongID(String DoiTuongID) {
        this.DoiTuongID = DoiTuongID;
    }

    public void setNguonID(String NguonID) {
        this.NguonID = NguonID;
    }

    public void setKieuKhachHang(String KieuKhachHang) {
        this.KieuKhachHang = KieuKhachHang;
    }

    public void setGoiThanhCong(String GoiThanhCong) {
        this.GoiThanhCong = GoiThanhCong;
    }

    public void setNhanVienHoTro(String NhanVienHoTro) {
        this.NhanVienHoTro = NhanVienHoTro;
    }

    public void setMSPhong(String MSPhong) {
        this.MSPhong = MSPhong;
    }

    public void setTinhTrangHonNhan(String TinhTrangHonNhan) {
        this.TinhTrangHonNhan = TinhTrangHonNhan;
    }

    public void setQuanLyTrucTiepID(String QuanLyTrucTiepID) {
        this.QuanLyTrucTiepID = QuanLyTrucTiepID;
    }

    public void setTenQuanLyTrucTiep(String TenQuanLyTrucTiep) {
        this.TenQuanLyTrucTiep = TenQuanLyTrucTiep;
    }

    public void setTrangThaiID(String TrangThaiID) {
        this.TrangThaiID = TrangThaiID;
    }

    public void setDanhGia(String DanhGia) {
        this.DanhGia = DanhGia;
    }

    public void setAnhCaNhan(String AnhCaNhan) {
        this.AnhCaNhan = AnhCaNhan;
    }

    public void setAnhCMND(String AnhCMND) {
        this.AnhCMND = AnhCMND;
    }

    public void setAnhMatTruocThe(String AnhMatTruocThe) {
        this.AnhMatTruocThe = AnhMatTruocThe;
    }

    public void setNhanVienKinhDoanhID(String NhanVienKinhDoanhID) {
        this.NhanVienKinhDoanhID = NhanVienKinhDoanhID;
    }

    public void setTenNhanVienKinhDoanh(String TenNhanVienKinhDoanh) {
        this.TenNhanVienKinhDoanh = TenNhanVienKinhDoanh;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public void setListTenSanPham(String ListTenSanPham) {
        this.ListTenSanPham = ListTenSanPham;
    }

    public void setListMaSanPham(String ListMaSanPham) {
        this.ListMaSanPham = ListMaSanPham;
    }

    public void setQuanHe(String QuanHe) {
        this.QuanHe = QuanHe;
    }

    public void setLoaiTheID(String LoaiTheID) {
        this.LoaiTheID = LoaiTheID;
    }

    public void setTongGhiChu(String TongGhiChu) {
        this.TongGhiChu = TongGhiChu;
    }

    public void setLoaitien(String Loaitien) {
        this.Loaitien = Loaitien;
    }

    public void setNgoaite(String Ngoaite) {
        this.Ngoaite = Ngoaite;
    }

    public void setTenNhanVienChamSoc(String TenNhanVienChamSoc) {
        this.TenNhanVienChamSoc = TenNhanVienChamSoc;
    }

    public void setCICID(String CICID) {
        this.CICID = CICID;
    }

    public void setQuanHeNganHangID(String QuanHeNganHangID) {
        this.QuanHeNganHangID = QuanHeNganHangID;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public void setCMNDNguoiGioiThieu(String CMNDNguoiGioiThieu) {
        this.CMNDNguoiGioiThieu = CMNDNguoiGioiThieu;
    }

    public void setSDTNguoiGioiThieu(String SDTNguoiGioiThieu) {
        this.SDTNguoiGioiThieu = SDTNguoiGioiThieu;
    }

    public void setCongTyCongViec(String CongTyCongViec) {
        this.CongTyCongViec = CongTyCongViec;
    }

    public void setLinhVucID(String LinhVucID) {
        this.LinhVucID = LinhVucID;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public void setNgayVay(String NgayVay) {
        this.NgayVay = NgayVay;
    }

    public void setDuNoVay(String DuNoVay) {
        this.DuNoVay = DuNoVay;
    }

    public void setHanMucThe(String HanMucThe) {
        this.HanMucThe = HanMucThe;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public void setActiveWeb(String ActiveWeb) {
        this.ActiveWeb = ActiveWeb;
    }

    public void setMaXacThucWeb(String MaXacThucWeb) {
        this.MaXacThucWeb = MaXacThucWeb;
    }

    public SupplierInfo getSupplierInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<SupplierInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<SupplierInfo> getListSupplierInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<SupplierInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
