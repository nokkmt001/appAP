package com.tiha.anphat.data.entities.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CallInfo {
    private Integer ID;
    private String Supplier_ID;
    private String Company_Name = null;
    private String Phone;
    private String Ngay;
    private String GhiChu = null;
    private String CALLden = null;
    private String SoLanGoi = null;
    private String Line;
    private String DiaChi;
    private String TenKhachHang;
    private String MaSP = null;
    private String SanPham = null;
    private String SoPhieu = null;
    private String GioIn = null;
    private String ModifiedDate;
    private String TienBan = null;
    private String SoCTThu = null;
    private String TienThu = null;
    private String NguoiGiao = null;
    private String Chon = null;
    private String TrangThai = null;
    private String GioThu = null;
    private String SoKm = null;
    private String Serial = null;
    private String ChiNhanh;
    private String SoID = null;
    private String DacDiemKH = null;
    private String GiaoKetHop = null;
    private String KHMoiCu;
    private String MSK = null;
    private String NguoiGo;
    private String GioGiao = null;
    private String SoCTNhap = null;
    private String VoVe = null;
    private String SL = null;
    private String SLTra = null;
    private String NhomKH;
    private String MayIn = null;
    private String TT = null;
    private String MoTaLine = null;
    private String SanPhamKM = null;
    private String TraVo = null;
    private String TraTien = null;
    private String Ngaynhan;
    private String ThoiGian = null;
    private String TinhTrang = null;
    private String DichVu = null;
    private String DDBH = null;
    private String AutoDDVo = null;
    private String BHDDVo = null;
    private Boolean LaCuocGoiToi;
    private String CuocGoiVanDeID = null;
    private String NguoiThayDoiVanDe = null;
    private String ThoiGianBD = null;
    private String ThoiGianKT = null;
    private String CongViecID = null;
    private Integer CuocGoiTrangThaiID;
    private String LinhVuc = null;
    private String FileGhiAm = null;
    private String Kenh = null;
    private String NguoiLienQuan = null;
    private String SoXe = null;
    private String TaiXe = null;
    private String PhongBanID = null;
    private String TongDaiID = null;
    private String CuocGoiDoiTuongID = null;
    private String SerialVe = null;
    private String NhanVienLine = null;
    private String TenNhanVienChamSoc = null;
    private String ADR = null;
    private String NguonGoi = null;
    private String DonViID = null;
    private String MSNguoiGiao = null;
    private String TaiChiNhanh;
    private String DiaChiGH = null;
    private String FileGhiAmGoc = null;
    private String PTTT = null;
    private String NVPTTT = null;
    private String ChuoiHeThong = null;
    private String GioChuyen = null;
    private String PhanAnhKH = null;
    private String NgayCapBH = null;
    private String NhomKHNew = null;

    public CallInfo getCallInfo(String jsonString) { // phieu xuat
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<CallInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getID() {
        return ID;
    }

    public String getSupplier_ID() {
        return Supplier_ID;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getNgay() {
        return Ngay;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public String getCALLden() {
        return CALLden;
    }

    public String getSoLanGoi() {
        return SoLanGoi;
    }

    public String getLine() {
        return Line;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public String getMaSP() {
        return MaSP;
    }

    public String getSanPham() {
        return SanPham;
    }

    public String getSoPhieu() {
        return SoPhieu;
    }

    public String getGioIn() {
        return GioIn;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public String getTienBan() {
        return TienBan;
    }

    public String getSoCTThu() {
        return SoCTThu;
    }

    public String getTienThu() {
        return TienThu;
    }

    public String getNguoiGiao() {
        return NguoiGiao;
    }

    public String getChon() {
        return Chon;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getGioThu() {
        return GioThu;
    }

    public String getSoKm() {
        return SoKm;
    }

    public String getSerial() {
        return Serial;
    }

    public String getChiNhanh() {
        return ChiNhanh;
    }

    public String getSoID() {
        return SoID;
    }

    public String getDacDiemKH() {
        return DacDiemKH;
    }

    public String getGiaoKetHop() {
        return GiaoKetHop;
    }

    public String getKHMoiCu() {
        return KHMoiCu;
    }

    public String getMSK() {
        return MSK;
    }

    public String getNguoiGo() {
        return NguoiGo;
    }

    public String getGioGiao() {
        return GioGiao;
    }

    public String getSoCTNhap() {
        return SoCTNhap;
    }

    public String getVoVe() {
        return VoVe;
    }

    public String getSL() {
        return SL;
    }

    public String getSLTra() {
        return SLTra;
    }

    public String getNhomKH() {
        return NhomKH;
    }

    public String getMayIn() {
        return MayIn;
    }

    public String getTT() {
        return TT;
    }

    public String getMoTaLine() {
        return MoTaLine;
    }

    public String getSanPhamKM() {
        return SanPhamKM;
    }

    public String getTraVo() {
        return TraVo;
    }

    public String getTraTien() {
        return TraTien;
    }

    public String getNgaynhan() {
        return Ngaynhan;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public String getDichVu() {
        return DichVu;
    }

    public String getDDBH() {
        return DDBH;
    }

    public String getAutoDDVo() {
        return AutoDDVo;
    }

    public String getBHDDVo() {
        return BHDDVo;
    }

    public Boolean getLaCuocGoiToi() {
        return LaCuocGoiToi;
    }

    public String getCuocGoiVanDeID() {
        return CuocGoiVanDeID;
    }

    public String getNguoiThayDoiVanDe() {
        return NguoiThayDoiVanDe;
    }

    public String getThoiGianBD() {
        return ThoiGianBD;
    }

    public String getThoiGianKT() {
        return ThoiGianKT;
    }

    public String getCongViecID() {
        return CongViecID;
    }

    public Integer getCuocGoiTrangThaiID() {
        return CuocGoiTrangThaiID;
    }

    public String getLinhVuc() {
        return LinhVuc;
    }

    public String getFileGhiAm() {
        return FileGhiAm;
    }

    public String getKenh() {
        return Kenh;
    }

    public String getNguoiLienQuan() {
        return NguoiLienQuan;
    }

    public String getSoXe() {
        return SoXe;
    }

    public String getTaiXe() {
        return TaiXe;
    }

    public String getPhongBanID() {
        return PhongBanID;
    }

    public String getTongDaiID() {
        return TongDaiID;
    }

    public String getCuocGoiDoiTuongID() {
        return CuocGoiDoiTuongID;
    }

    public String getSerialVe() {
        return SerialVe;
    }

    public String getNhanVienLine() {
        return NhanVienLine;
    }

    public String getTenNhanVienChamSoc() {
        return TenNhanVienChamSoc;
    }

    public String getADR() {
        return ADR;
    }

    public String getNguonGoi() {
        return NguonGoi;
    }

    public String getDonViID() {
        return DonViID;
    }

    public String getMSNguoiGiao() {
        return MSNguoiGiao;
    }

    public String getTaiChiNhanh() {
        return TaiChiNhanh;
    }

    public String getDiaChiGH() {
        return DiaChiGH;
    }

    public String getFileGhiAmGoc() {
        return FileGhiAmGoc;
    }

    public String getPTTT() {
        return PTTT;
    }

    public String getNVPTTT() {
        return NVPTTT;
    }

    public String getChuoiHeThong() {
        return ChuoiHeThong;
    }

    public String getGioChuyen() {
        return GioChuyen;
    }

    public String getPhanAnhKH() {
        return PhanAnhKH;
    }

    public String getNgayCapBH() {
        return NgayCapBH;
    }

    public String getNhomKHNew() {
        return NhomKHNew;
    }

    // Setter Methods 

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setSupplier_ID(String Supplier_ID) {
        this.Supplier_ID = Supplier_ID;
    }

    public void setCompany_Name(String Company_Name) {
        this.Company_Name = Company_Name;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setNgay(String Ngay) {
        this.Ngay = Ngay;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public void setCALLden(String CALLden) {
        this.CALLden = CALLden;
    }

    public void setSoLanGoi(String SoLanGoi) {
        this.SoLanGoi = SoLanGoi;
    }

    public void setLine(String Line) {
        this.Line = Line;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void setTenKhachHang(String TenKhachHang) {
        this.TenKhachHang = TenKhachHang;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public void setSanPham(String SanPham) {
        this.SanPham = SanPham;
    }

    public void setSoPhieu(String SoPhieu) {
        this.SoPhieu = SoPhieu;
    }

    public void setGioIn(String GioIn) {
        this.GioIn = GioIn;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public void setTienBan(String TienBan) {
        this.TienBan = TienBan;
    }

    public void setSoCTThu(String SoCTThu) {
        this.SoCTThu = SoCTThu;
    }

    public void setTienThu(String TienThu) {
        this.TienThu = TienThu;
    }

    public void setNguoiGiao(String NguoiGiao) {
        this.NguoiGiao = NguoiGiao;
    }

    public void setChon(String Chon) {
        this.Chon = Chon;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public void setGioThu(String GioThu) {
        this.GioThu = GioThu;
    }

    public void setSoKm(String SoKm) {
        this.SoKm = SoKm;
    }

    public void setSerial(String Serial) {
        this.Serial = Serial;
    }

    public void setChiNhanh(String ChiNhanh) {
        this.ChiNhanh = ChiNhanh;
    }

    public void setSoID(String SoID) {
        this.SoID = SoID;
    }

    public void setDacDiemKH(String DacDiemKH) {
        this.DacDiemKH = DacDiemKH;
    }

    public void setGiaoKetHop(String GiaoKetHop) {
        this.GiaoKetHop = GiaoKetHop;
    }

    public void setKHMoiCu(String KHMoiCu) {
        this.KHMoiCu = KHMoiCu;
    }

    public void setMSK(String MSK) {
        this.MSK = MSK;
    }

    public void setNguoiGo(String NguoiGo) {
        this.NguoiGo = NguoiGo;
    }

    public void setGioGiao(String GioGiao) {
        this.GioGiao = GioGiao;
    }

    public void setSoCTNhap(String SoCTNhap) {
        this.SoCTNhap = SoCTNhap;
    }

    public void setVoVe(String VoVe) {
        this.VoVe = VoVe;
    }

    public void setSL(String SL) {
        this.SL = SL;
    }

    public void setSLTra(String SLTra) {
        this.SLTra = SLTra;
    }

    public void setNhomKH(String NhomKH) {
        this.NhomKH = NhomKH;
    }

    public void setMayIn(String MayIn) {
        this.MayIn = MayIn;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    public void setMoTaLine(String MoTaLine) {
        this.MoTaLine = MoTaLine;
    }

    public void setSanPhamKM(String SanPhamKM) {
        this.SanPhamKM = SanPhamKM;
    }

    public void setTraVo(String TraVo) {
        this.TraVo = TraVo;
    }

    public void setTraTien(String TraTien) {
        this.TraTien = TraTien;
    }

    public void setNgaynhan(String Ngaynhan) {
        this.Ngaynhan = Ngaynhan;
    }

    public void setThoiGian(String ThoiGian) {
        this.ThoiGian = ThoiGian;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public void setDichVu(String DichVu) {
        this.DichVu = DichVu;
    }

    public void setDDBH(String DDBH) {
        this.DDBH = DDBH;
    }

    public void setAutoDDVo(String AutoDDVo) {
        this.AutoDDVo = AutoDDVo;
    }

    public void setBHDDVo(String BHDDVo) {
        this.BHDDVo = BHDDVo;
    }

    public void setLaCuocGoiToi(Boolean LaCuocGoiToi) {
        this.LaCuocGoiToi = LaCuocGoiToi;
    }

    public void setCuocGoiVanDeID(String CuocGoiVanDeID) {
        this.CuocGoiVanDeID = CuocGoiVanDeID;
    }

    public void setNguoiThayDoiVanDe(String NguoiThayDoiVanDe) {
        this.NguoiThayDoiVanDe = NguoiThayDoiVanDe;
    }

    public void setThoiGianBD(String ThoiGianBD) {
        this.ThoiGianBD = ThoiGianBD;
    }

    public void setThoiGianKT(String ThoiGianKT) {
        this.ThoiGianKT = ThoiGianKT;
    }

    public void setCongViecID(String CongViecID) {
        this.CongViecID = CongViecID;
    }

    public void setCuocGoiTrangThaiID(Integer CuocGoiTrangThaiID) {
        this.CuocGoiTrangThaiID = CuocGoiTrangThaiID;
    }

    public void setLinhVuc(String LinhVuc) {
        this.LinhVuc = LinhVuc;
    }

    public void setFileGhiAm(String FileGhiAm) {
        this.FileGhiAm = FileGhiAm;
    }

    public void setKenh(String Kenh) {
        this.Kenh = Kenh;
    }

    public void setNguoiLienQuan(String NguoiLienQuan) {
        this.NguoiLienQuan = NguoiLienQuan;
    }

    public void setSoXe(String SoXe) {
        this.SoXe = SoXe;
    }

    public void setTaiXe(String TaiXe) {
        this.TaiXe = TaiXe;
    }

    public void setPhongBanID(String PhongBanID) {
        this.PhongBanID = PhongBanID;
    }

    public void setTongDaiID(String TongDaiID) {
        this.TongDaiID = TongDaiID;
    }

    public void setCuocGoiDoiTuongID(String CuocGoiDoiTuongID) {
        this.CuocGoiDoiTuongID = CuocGoiDoiTuongID;
    }

    public void setSerialVe(String SerialVe) {
        this.SerialVe = SerialVe;
    }

    public void setNhanVienLine(String NhanVienLine) {
        this.NhanVienLine = NhanVienLine;
    }

    public void setTenNhanVienChamSoc(String TenNhanVienChamSoc) {
        this.TenNhanVienChamSoc = TenNhanVienChamSoc;
    }

    public void setADR(String ADR) {
        this.ADR = ADR;
    }

    public void setNguonGoi(String NguonGoi) {
        this.NguonGoi = NguonGoi;
    }

    public void setDonViID(String DonViID) {
        this.DonViID = DonViID;
    }

    public void setMSNguoiGiao(String MSNguoiGiao) {
        this.MSNguoiGiao = MSNguoiGiao;
    }

    public void setTaiChiNhanh(String TaiChiNhanh) {
        this.TaiChiNhanh = TaiChiNhanh;
    }

    public void setDiaChiGH(String DiaChiGH) {
        this.DiaChiGH = DiaChiGH;
    }

    public void setFileGhiAmGoc(String FileGhiAmGoc) {
        this.FileGhiAmGoc = FileGhiAmGoc;
    }

    public void setPTTT(String PTTT) {
        this.PTTT = PTTT;
    }

    public void setNVPTTT(String NVPTTT) {
        this.NVPTTT = NVPTTT;
    }

    public void setChuoiHeThong(String ChuoiHeThong) {
        this.ChuoiHeThong = ChuoiHeThong;
    }

    public void setGioChuyen(String GioChuyen) {
        this.GioChuyen = GioChuyen;
    }

    public void setPhanAnhKH(String PhanAnhKH) {
        this.PhanAnhKH = PhanAnhKH;
    }

    public void setNgayCapBH(String NgayCapBH) {
        this.NgayCapBH = NgayCapBH;
    }

    public void setNhomKHNew(String NhomKHNew) {
        this.NhomKHNew = NhomKHNew;
    }
}

