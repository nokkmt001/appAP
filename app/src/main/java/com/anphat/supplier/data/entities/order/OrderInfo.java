package com.anphat.supplier.data.entities.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class OrderInfo {
    private String SoCt;
    private String Ngay;
    private String MSK;
    private String Supplier_ID;
    private Integer TriGia;
    private Integer Thanhtoan;
    private String PTTT;
    private String Message = null;
    private String DDBH = null;
    private String Ghichu = null;
    private Integer DuocSua;
    private String SHTKN;
    private String SHTKC;
    private String TT = null;
    private String VND = null;
    private String Lydo;
    private String Tygia = null;
    private String Ngaygiogiao = null;
    private String Solanchinh = null;
    private String GVON = null;
    private String GThue = null;
    private String Nguoigo;
    private String Tennguoimua;
    private String Diachi;
    private String KHHD = null;
    private String SoHD = null;
    private String NgayHD;
    private String MaSoThue = null;
    private String TenDDnguoimua;
    private String Diengiai = null;
    private String ThueSuat = null;
    private String Ngaygio;
    private String LoaiDiscount = null;
    private String GiatriDiscount = null;
    private String LydoDiscount = null;
    private String CoHD = null;
    private String Solanin = null;
    private String SOCT2 = null;
    private String SN = null;
    private String PhantramChietkhau = null;
    private String SoNgaytraduochuongCK = null;
    private String MSDKTT;
    private String PhantramPhat = null;
    private String Daxuat = null;
    private String NgaytinhBH = null;
    private String MSNguoigiao = null;
    private String Giove = null;
    private String Diachigiaohang;
    private String LPhieu = null;
    private String DaxuatTH = null;
    private String Ngaytinhlai = null;
    private String Chinhanh;
    private String Khachdua = null;
    private String ModifiedDate = null;
    private String ModifiedBy = null;
    private String TrangThai = null;
    private String Tuyen = null;
    private String KhachDenNhan = null;
    private String DTDD = null;
    private String Supplier_IDHH = null;
    private String PhuThu = null;
    private String LoaiHinhThanhToan;
    private String GhiChuHoaDon = null;
    private String NgayDungTinhLai = null;
    private String SoPhieuVietTay;
    private String CAID = null;
    private String EMailPx = null;
    private String Nguoiduyet = null;
    private String Chanh = null;
    private String Ingia = null;
    private String ThoiGianGiao = null;
    private String MSKNhap = null;
    private String DaiDienMuaHang2 = null;
    private String LydoHD = null;
    private String NhanVienGiaoHang = null;
    private String SoCTBG = null;
    private String MaHD = null;
    private String KHMauHD = null;
    private String TKVon = null;
    private String TaiXe = null;
    private String SoCTTH = null;
    private String LoaiPhieu;
    private String GiaTriBH = null;
    private String IDTG = null;
    private String NguoiLap = null;
    private String ThuKho = null;
    private String Duyet = null;
    private String LaiSuat = null;
    private String MSNV = null;
    private String LichSuTrangThai = null;
    private String KhachDuocNo = null;
    private String IDKhachHangTraGop = null;
    private String MaChietKhau = null;
    private String ThueTruocBa = null;
    private String SoCTPhieuDatCoc = null;
    private String PXKhongThue = null;
    private String SoCTCK = null;
    private String NguoiTrungGian = null;
    private String DiaChiTrungGian = null;
    private String DienThoaiTrungGian = null;
    private String JobCode = null;
    private String ContractID = null;
    private String Ctr = null;
    private String TinhTrangNo = null;
    private String IDSupplierChiPhi = null;
    private String MaTK = null;
    private String KhoanMuc = null;
    private String TrangThaiEInvoice = null;
    private String MaEInvoice = null;
    private String GhiChuEInvoice = null;
    private String LyDoEInvoice = null;
    private String SupplierIDVattu = null;
    private String GhiChuChietKhau = null;
    private String NhanVienLapDat = null;
    private String NhanVienThuTien = null;
    private String IDPhieuPhoiMauSX = null;
    private String TLSD = null;
    private String TLQC = null;
    private String NgaySX = null;
    private String NoS = null;
    private String IDMaMau = null;
    private String SoLan = null;
    private String SoBienNhan = null;
    private String SoPhieuNhanTin = null;
    private String MaSoTraCuuHDDT = null;
    private String VanBanThoaThuan = null;
    private String NgayThoaThuan = null;
    private String MaTrangThaiHDDT = null;
    private String EMail = null;
    private String IDKhuVuc = null;
    private String HopDongGoc = null;
    private String TienMat = null;
    private String TienTheCK = null;
    private String MaThe = null;
    private String TemPhieu = null;
    private String SerialTem = null;
    private String DoiDiem = null;
    private String TienDiem = null;
    private String TieuDung = null;
    private String DungHoaDonDienTu = null;
    private String Ghe = null;
    private String LoaiTien = null;
    private String MaLoaiHoaDon = null;
    private String GiaTriCPKhac = null;
    private String NVPTTT = null;
    private String BHTN = null;
    private String CongNo = null;
    private String DVSC = null;
    private String GopGasDuTrenHoaDon = null;
    private String GioIn = null;
    private String NgayNhanHang = null;
    private String TriGiaQuyDoi = null;
    private String Supplier = null;
    private String kho = null;
    private String kho1 = null;
    private String Supplier1 = null;

    public OrderInfo getOrderInfo(String jsonString) { // phieu xuat
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<OrderInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

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

    public Integer getTriGia() {
        return TriGia;
    }

    public Integer getThanhtoan() {
        return Thanhtoan;
    }

    public String getPTTT() {
        return PTTT;
    }

    public String getMessage() {
        return Message;
    }

    public String getDDBH() {
        return DDBH;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public Integer getDuocSua() {
        return DuocSua;
    }

    public String getSHTKN() {
        return SHTKN;
    }

    public String getSHTKC() {
        return SHTKC;
    }

    public String getTT() {
        return TT;
    }

    public String getVND() {
        return VND;
    }

    public String getLydo() {
        return Lydo;
    }

    public String getTygia() {
        return Tygia;
    }

    public String getNgaygiogiao() {
        return Ngaygiogiao;
    }

    public String getSolanchinh() {
        return Solanchinh;
    }

    public String getGVON() {
        return GVON;
    }

    public String getGThue() {
        return GThue;
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

    public String getKHHD() {
        return KHHD;
    }

    public String getSoHD() {
        return SoHD;
    }

    public String getNgayHD() {
        return NgayHD;
    }

    public String getMaSoThue() {
        return MaSoThue;
    }

    public String getTenDDnguoimua() {
        return TenDDnguoimua;
    }

    public String getDiengiai() {
        return Diengiai;
    }

    public String getThueSuat() {
        return ThueSuat;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public String getLoaiDiscount() {
        return LoaiDiscount;
    }

    public String getGiatriDiscount() {
        return GiatriDiscount;
    }

    public String getLydoDiscount() {
        return LydoDiscount;
    }

    public String getCoHD() {
        return CoHD;
    }

    public String getSolanin() {
        return Solanin;
    }

    public String getSOCT2() {
        return SOCT2;
    }

    public String getSN() {
        return SN;
    }

    public String getPhantramChietkhau() {
        return PhantramChietkhau;
    }

    public String getSoNgaytraduochuongCK() {
        return SoNgaytraduochuongCK;
    }

    public String getMSDKTT() {
        return MSDKTT;
    }

    public String getPhantramPhat() {
        return PhantramPhat;
    }

    public String getDaxuat() {
        return Daxuat;
    }

    public String getNgaytinhBH() {
        return NgaytinhBH;
    }

    public String getMSNguoigiao() {
        return MSNguoigiao;
    }

    public String getGiove() {
        return Giove;
    }

    public String getDiachigiaohang() {
        return Diachigiaohang;
    }

    public String getLPhieu() {
        return LPhieu;
    }

    public String getDaxuatTH() {
        return DaxuatTH;
    }

    public String getNgaytinhlai() {
        return Ngaytinhlai;
    }

    public String getChinhanh() {
        return Chinhanh;
    }

    public String getKhachdua() {
        return Khachdua;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getTuyen() {
        return Tuyen;
    }

    public String getKhachDenNhan() {
        return KhachDenNhan;
    }

    public String getDTDD() {
        return DTDD;
    }

    public String getSupplier_IDHH() {
        return Supplier_IDHH;
    }

    public String getPhuThu() {
        return PhuThu;
    }

    public String getLoaiHinhThanhToan() {
        return LoaiHinhThanhToan;
    }

    public String getGhiChuHoaDon() {
        return GhiChuHoaDon;
    }

    public String getNgayDungTinhLai() {
        return NgayDungTinhLai;
    }

    public String getSoPhieuVietTay() {
        return SoPhieuVietTay;
    }

    public String getCAID() {
        return CAID;
    }

    public String getEMailPx() {
        return EMailPx;
    }

    public String getNguoiduyet() {
        return Nguoiduyet;
    }

    public String getChanh() {
        return Chanh;
    }

    public String getIngia() {
        return Ingia;
    }

    public String getThoiGianGiao() {
        return ThoiGianGiao;
    }

    public String getMSKNhap() {
        return MSKNhap;
    }

    public String getDaiDienMuaHang2() {
        return DaiDienMuaHang2;
    }

    public String getLydoHD() {
        return LydoHD;
    }

    public String getNhanVienGiaoHang() {
        return NhanVienGiaoHang;
    }

    public String getSoCTBG() {
        return SoCTBG;
    }

    public String getMaHD() {
        return MaHD;
    }

    public String getKHMauHD() {
        return KHMauHD;
    }

    public String getTKVon() {
        return TKVon;
    }

    public String getTaiXe() {
        return TaiXe;
    }

    public String getSoCTTH() {
        return SoCTTH;
    }

    public String getLoaiPhieu() {
        return LoaiPhieu;
    }

    public String getGiaTriBH() {
        return GiaTriBH;
    }

    public String getIDTG() {
        return IDTG;
    }

    public String getNguoiLap() {
        return NguoiLap;
    }

    public String getThuKho() {
        return ThuKho;
    }

    public String getDuyet() {
        return Duyet;
    }

    public String getLaiSuat() {
        return LaiSuat;
    }

    public String getMSNV() {
        return MSNV;
    }

    public String getLichSuTrangThai() {
        return LichSuTrangThai;
    }

    public String getKhachDuocNo() {
        return KhachDuocNo;
    }

    public String getIDKhachHangTraGop() {
        return IDKhachHangTraGop;
    }

    public String getMaChietKhau() {
        return MaChietKhau;
    }

    public String getThueTruocBa() {
        return ThueTruocBa;
    }

    public String getSoCTPhieuDatCoc() {
        return SoCTPhieuDatCoc;
    }

    public String getPXKhongThue() {
        return PXKhongThue;
    }

    public String getSoCTCK() {
        return SoCTCK;
    }

    public String getNguoiTrungGian() {
        return NguoiTrungGian;
    }

    public String getDiaChiTrungGian() {
        return DiaChiTrungGian;
    }

    public String getDienThoaiTrungGian() {
        return DienThoaiTrungGian;
    }

    public String getJobCode() {
        return JobCode;
    }

    public String getContractID() {
        return ContractID;
    }

    public String getCtr() {
        return Ctr;
    }

    public String getTinhTrangNo() {
        return TinhTrangNo;
    }

    public String getIDSupplierChiPhi() {
        return IDSupplierChiPhi;
    }

    public String getMaTK() {
        return MaTK;
    }

    public String getKhoanMuc() {
        return KhoanMuc;
    }

    public String getTrangThaiEInvoice() {
        return TrangThaiEInvoice;
    }

    public String getMaEInvoice() {
        return MaEInvoice;
    }

    public String getGhiChuEInvoice() {
        return GhiChuEInvoice;
    }

    public String getLyDoEInvoice() {
        return LyDoEInvoice;
    }

    public String getSupplierIDVattu() {
        return SupplierIDVattu;
    }

    public String getGhiChuChietKhau() {
        return GhiChuChietKhau;
    }

    public String getNhanVienLapDat() {
        return NhanVienLapDat;
    }

    public String getNhanVienThuTien() {
        return NhanVienThuTien;
    }

    public String getIDPhieuPhoiMauSX() {
        return IDPhieuPhoiMauSX;
    }

    public String getTLSD() {
        return TLSD;
    }

    public String getTLQC() {
        return TLQC;
    }

    public String getNgaySX() {
        return NgaySX;
    }

    public String getNoS() {
        return NoS;
    }

    public String getIDMaMau() {
        return IDMaMau;
    }

    public String getSoLan() {
        return SoLan;
    }

    public String getSoBienNhan() {
        return SoBienNhan;
    }

    public String getSoPhieuNhanTin() {
        return SoPhieuNhanTin;
    }

    public String getMaSoTraCuuHDDT() {
        return MaSoTraCuuHDDT;
    }

    public String getVanBanThoaThuan() {
        return VanBanThoaThuan;
    }

    public String getNgayThoaThuan() {
        return NgayThoaThuan;
    }

    public String getMaTrangThaiHDDT() {
        return MaTrangThaiHDDT;
    }

    public String getEMail() {
        return EMail;
    }

    public String getIDKhuVuc() {
        return IDKhuVuc;
    }

    public String getHopDongGoc() {
        return HopDongGoc;
    }

    public String getTienMat() {
        return TienMat;
    }

    public String getTienTheCK() {
        return TienTheCK;
    }

    public String getMaThe() {
        return MaThe;
    }

    public String getTemPhieu() {
        return TemPhieu;
    }

    public String getSerialTem() {
        return SerialTem;
    }

    public String getDoiDiem() {
        return DoiDiem;
    }

    public String getTienDiem() {
        return TienDiem;
    }

    public String getTieuDung() {
        return TieuDung;
    }

    public String getDungHoaDonDienTu() {
        return DungHoaDonDienTu;
    }

    public String getGhe() {
        return Ghe;
    }

    public String getLoaiTien() {
        return LoaiTien;
    }

    public String getMaLoaiHoaDon() {
        return MaLoaiHoaDon;
    }

    public String getGiaTriCPKhac() {
        return GiaTriCPKhac;
    }

    public String getNVPTTT() {
        return NVPTTT;
    }

    public String getBHTN() {
        return BHTN;
    }

    public String getCongNo() {
        return CongNo;
    }

    public String getDVSC() {
        return DVSC;
    }

    public String getGopGasDuTrenHoaDon() {
        return GopGasDuTrenHoaDon;
    }

    public String getGioIn() {
        return GioIn;
    }

    public String getNgayNhanHang() {
        return NgayNhanHang;
    }

    public String getTriGiaQuyDoi() {
        return TriGiaQuyDoi;
    }

    public String getSupplier() {
        return Supplier;
    }

    public String getKho() {
        return kho;
    }

    public String getKho1() {
        return kho1;
    }

    public String getSupplier1() {
        return Supplier1;
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

    public void setTriGia(Integer TriGia) {
        this.TriGia = TriGia;
    }

    public void setThanhtoan(Integer Thanhtoan) {
        this.Thanhtoan = Thanhtoan;
    }

    public void setPTTT(String PTTT) {
        this.PTTT = PTTT;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setDDBH(String DDBH) {
        this.DDBH = DDBH;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }

    public void setDuocSua(Integer DuocSua) {
        this.DuocSua = DuocSua;
    }

    public void setSHTKN(String SHTKN) {
        this.SHTKN = SHTKN;
    }

    public void setSHTKC(String SHTKC) {
        this.SHTKC = SHTKC;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    public void setVND(String VND) {
        this.VND = VND;
    }

    public void setLydo(String Lydo) {
        this.Lydo = Lydo;
    }

    public void setTygia(String Tygia) {
        this.Tygia = Tygia;
    }

    public void setNgaygiogiao(String Ngaygiogiao) {
        this.Ngaygiogiao = Ngaygiogiao;
    }

    public void setSolanchinh(String Solanchinh) {
        this.Solanchinh = Solanchinh;
    }

    public void setGVON(String GVON) {
        this.GVON = GVON;
    }

    public void setGThue(String GThue) {
        this.GThue = GThue;
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

    public void setKHHD(String KHHD) {
        this.KHHD = KHHD;
    }

    public void setSoHD(String SoHD) {
        this.SoHD = SoHD;
    }

    public void setNgayHD(String NgayHD) {
        this.NgayHD = NgayHD;
    }

    public void setMaSoThue(String MaSoThue) {
        this.MaSoThue = MaSoThue;
    }

    public void setTenDDnguoimua(String TenDDnguoimua) {
        this.TenDDnguoimua = TenDDnguoimua;
    }

    public void setDiengiai(String Diengiai) {
        this.Diengiai = Diengiai;
    }

    public void setThueSuat(String ThueSuat) {
        this.ThueSuat = ThueSuat;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }

    public void setLoaiDiscount(String LoaiDiscount) {
        this.LoaiDiscount = LoaiDiscount;
    }

    public void setGiatriDiscount(String GiatriDiscount) {
        this.GiatriDiscount = GiatriDiscount;
    }

    public void setLydoDiscount(String LydoDiscount) {
        this.LydoDiscount = LydoDiscount;
    }

    public void setCoHD(String CoHD) {
        this.CoHD = CoHD;
    }

    public void setSolanin(String Solanin) {
        this.Solanin = Solanin;
    }

    public void setSOCT2(String SOCT2) {
        this.SOCT2 = SOCT2;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public void setPhantramChietkhau(String PhantramChietkhau) {
        this.PhantramChietkhau = PhantramChietkhau;
    }

    public void setSoNgaytraduochuongCK(String SoNgaytraduochuongCK) {
        this.SoNgaytraduochuongCK = SoNgaytraduochuongCK;
    }

    public void setMSDKTT(String MSDKTT) {
        this.MSDKTT = MSDKTT;
    }

    public void setPhantramPhat(String PhantramPhat) {
        this.PhantramPhat = PhantramPhat;
    }

    public void setDaxuat(String Daxuat) {
        this.Daxuat = Daxuat;
    }

    public void setNgaytinhBH(String NgaytinhBH) {
        this.NgaytinhBH = NgaytinhBH;
    }

    public void setMSNguoigiao(String MSNguoigiao) {
        this.MSNguoigiao = MSNguoigiao;
    }

    public void setGiove(String Giove) {
        this.Giove = Giove;
    }

    public void setDiachigiaohang(String Diachigiaohang) {
        this.Diachigiaohang = Diachigiaohang;
    }

    public void setLPhieu(String LPhieu) {
        this.LPhieu = LPhieu;
    }

    public void setDaxuatTH(String DaxuatTH) {
        this.DaxuatTH = DaxuatTH;
    }

    public void setNgaytinhlai(String Ngaytinhlai) {
        this.Ngaytinhlai = Ngaytinhlai;
    }

    public void setChinhanh(String Chinhanh) {
        this.Chinhanh = Chinhanh;
    }

    public void setKhachdua(String Khachdua) {
        this.Khachdua = Khachdua;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public void setTuyen(String Tuyen) {
        this.Tuyen = Tuyen;
    }

    public void setKhachDenNhan(String KhachDenNhan) {
        this.KhachDenNhan = KhachDenNhan;
    }

    public void setDTDD(String DTDD) {
        this.DTDD = DTDD;
    }

    public void setSupplier_IDHH(String Supplier_IDHH) {
        this.Supplier_IDHH = Supplier_IDHH;
    }

    public void setPhuThu(String PhuThu) {
        this.PhuThu = PhuThu;
    }

    public void setLoaiHinhThanhToan(String LoaiHinhThanhToan) {
        this.LoaiHinhThanhToan = LoaiHinhThanhToan;
    }

    public void setGhiChuHoaDon(String GhiChuHoaDon) {
        this.GhiChuHoaDon = GhiChuHoaDon;
    }

    public void setNgayDungTinhLai(String NgayDungTinhLai) {
        this.NgayDungTinhLai = NgayDungTinhLai;
    }

    public void setSoPhieuVietTay(String SoPhieuVietTay) {
        this.SoPhieuVietTay = SoPhieuVietTay;
    }

    public void setCAID(String CAID) {
        this.CAID = CAID;
    }

    public void setEMailPx(String EMailPx) {
        this.EMailPx = EMailPx;
    }

    public void setNguoiduyet(String Nguoiduyet) {
        this.Nguoiduyet = Nguoiduyet;
    }

    public void setChanh(String Chanh) {
        this.Chanh = Chanh;
    }

    public void setIngia(String Ingia) {
        this.Ingia = Ingia;
    }

    public void setThoiGianGiao(String ThoiGianGiao) {
        this.ThoiGianGiao = ThoiGianGiao;
    }

    public void setMSKNhap(String MSKNhap) {
        this.MSKNhap = MSKNhap;
    }

    public void setDaiDienMuaHang2(String DaiDienMuaHang2) {
        this.DaiDienMuaHang2 = DaiDienMuaHang2;
    }

    public void setLydoHD(String LydoHD) {
        this.LydoHD = LydoHD;
    }

    public void setNhanVienGiaoHang(String NhanVienGiaoHang) {
        this.NhanVienGiaoHang = NhanVienGiaoHang;
    }

    public void setSoCTBG(String SoCTBG) {
        this.SoCTBG = SoCTBG;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public void setKHMauHD(String KHMauHD) {
        this.KHMauHD = KHMauHD;
    }

    public void setTKVon(String TKVon) {
        this.TKVon = TKVon;
    }

    public void setTaiXe(String TaiXe) {
        this.TaiXe = TaiXe;
    }

    public void setSoCTTH(String SoCTTH) {
        this.SoCTTH = SoCTTH;
    }

    public void setLoaiPhieu(String LoaiPhieu) {
        this.LoaiPhieu = LoaiPhieu;
    }

    public void setGiaTriBH(String GiaTriBH) {
        this.GiaTriBH = GiaTriBH;
    }

    public void setIDTG(String IDTG) {
        this.IDTG = IDTG;
    }

    public void setNguoiLap(String NguoiLap) {
        this.NguoiLap = NguoiLap;
    }

    public void setThuKho(String ThuKho) {
        this.ThuKho = ThuKho;
    }

    public void setDuyet(String Duyet) {
        this.Duyet = Duyet;
    }

    public void setLaiSuat(String LaiSuat) {
        this.LaiSuat = LaiSuat;
    }

    public void setMSNV(String MSNV) {
        this.MSNV = MSNV;
    }

    public void setLichSuTrangThai(String LichSuTrangThai) {
        this.LichSuTrangThai = LichSuTrangThai;
    }

    public void setKhachDuocNo(String KhachDuocNo) {
        this.KhachDuocNo = KhachDuocNo;
    }

    public void setIDKhachHangTraGop(String IDKhachHangTraGop) {
        this.IDKhachHangTraGop = IDKhachHangTraGop;
    }

    public void setMaChietKhau(String MaChietKhau) {
        this.MaChietKhau = MaChietKhau;
    }

    public void setThueTruocBa(String ThueTruocBa) {
        this.ThueTruocBa = ThueTruocBa;
    }

    public void setSoCTPhieuDatCoc(String SoCTPhieuDatCoc) {
        this.SoCTPhieuDatCoc = SoCTPhieuDatCoc;
    }

    public void setPXKhongThue(String PXKhongThue) {
        this.PXKhongThue = PXKhongThue;
    }

    public void setSoCTCK(String SoCTCK) {
        this.SoCTCK = SoCTCK;
    }

    public void setNguoiTrungGian(String NguoiTrungGian) {
        this.NguoiTrungGian = NguoiTrungGian;
    }

    public void setDiaChiTrungGian(String DiaChiTrungGian) {
        this.DiaChiTrungGian = DiaChiTrungGian;
    }

    public void setDienThoaiTrungGian(String DienThoaiTrungGian) {
        this.DienThoaiTrungGian = DienThoaiTrungGian;
    }

    public void setJobCode(String JobCode) {
        this.JobCode = JobCode;
    }

    public void setContractID(String ContractID) {
        this.ContractID = ContractID;
    }

    public void setCtr(String Ctr) {
        this.Ctr = Ctr;
    }

    public void setTinhTrangNo(String TinhTrangNo) {
        this.TinhTrangNo = TinhTrangNo;
    }

    public void setIDSupplierChiPhi(String IDSupplierChiPhi) {
        this.IDSupplierChiPhi = IDSupplierChiPhi;
    }

    public void setMaTK(String MaTK) {
        this.MaTK = MaTK;
    }

    public void setKhoanMuc(String KhoanMuc) {
        this.KhoanMuc = KhoanMuc;
    }

    public void setTrangThaiEInvoice(String TrangThaiEInvoice) {
        this.TrangThaiEInvoice = TrangThaiEInvoice;
    }

    public void setMaEInvoice(String MaEInvoice) {
        this.MaEInvoice = MaEInvoice;
    }

    public void setGhiChuEInvoice(String GhiChuEInvoice) {
        this.GhiChuEInvoice = GhiChuEInvoice;
    }

    public void setLyDoEInvoice(String LyDoEInvoice) {
        this.LyDoEInvoice = LyDoEInvoice;
    }

    public void setSupplierIDVattu(String SupplierIDVattu) {
        this.SupplierIDVattu = SupplierIDVattu;
    }

    public void setGhiChuChietKhau(String GhiChuChietKhau) {
        this.GhiChuChietKhau = GhiChuChietKhau;
    }

    public void setNhanVienLapDat(String NhanVienLapDat) {
        this.NhanVienLapDat = NhanVienLapDat;
    }

    public void setNhanVienThuTien(String NhanVienThuTien) {
        this.NhanVienThuTien = NhanVienThuTien;
    }

    public void setIDPhieuPhoiMauSX(String IDPhieuPhoiMauSX) {
        this.IDPhieuPhoiMauSX = IDPhieuPhoiMauSX;
    }

    public void setTLSD(String TLSD) {
        this.TLSD = TLSD;
    }

    public void setTLQC(String TLQC) {
        this.TLQC = TLQC;
    }

    public void setNgaySX(String NgaySX) {
        this.NgaySX = NgaySX;
    }

    public void setNoS(String NoS) {
        this.NoS = NoS;
    }

    public void setIDMaMau(String IDMaMau) {
        this.IDMaMau = IDMaMau;
    }

    public void setSoLan(String SoLan) {
        this.SoLan = SoLan;
    }

    public void setSoBienNhan(String SoBienNhan) {
        this.SoBienNhan = SoBienNhan;
    }

    public void setSoPhieuNhanTin(String SoPhieuNhanTin) {
        this.SoPhieuNhanTin = SoPhieuNhanTin;
    }

    public void setMaSoTraCuuHDDT(String MaSoTraCuuHDDT) {
        this.MaSoTraCuuHDDT = MaSoTraCuuHDDT;
    }

    public void setVanBanThoaThuan(String VanBanThoaThuan) {
        this.VanBanThoaThuan = VanBanThoaThuan;
    }

    public void setNgayThoaThuan(String NgayThoaThuan) {
        this.NgayThoaThuan = NgayThoaThuan;
    }

    public void setMaTrangThaiHDDT(String MaTrangThaiHDDT) {
        this.MaTrangThaiHDDT = MaTrangThaiHDDT;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public void setIDKhuVuc(String IDKhuVuc) {
        this.IDKhuVuc = IDKhuVuc;
    }

    public void setHopDongGoc(String HopDongGoc) {
        this.HopDongGoc = HopDongGoc;
    }

    public void setTienMat(String TienMat) {
        this.TienMat = TienMat;
    }

    public void setTienTheCK(String TienTheCK) {
        this.TienTheCK = TienTheCK;
    }

    public void setMaThe(String MaThe) {
        this.MaThe = MaThe;
    }

    public void setTemPhieu(String TemPhieu) {
        this.TemPhieu = TemPhieu;
    }

    public void setSerialTem(String SerialTem) {
        this.SerialTem = SerialTem;
    }

    public void setDoiDiem(String DoiDiem) {
        this.DoiDiem = DoiDiem;
    }

    public void setTienDiem(String TienDiem) {
        this.TienDiem = TienDiem;
    }

    public void setTieuDung(String TieuDung) {
        this.TieuDung = TieuDung;
    }

    public void setDungHoaDonDienTu(String DungHoaDonDienTu) {
        this.DungHoaDonDienTu = DungHoaDonDienTu;
    }

    public void setGhe(String Ghe) {
        this.Ghe = Ghe;
    }

    public void setLoaiTien(String LoaiTien) {
        this.LoaiTien = LoaiTien;
    }

    public void setMaLoaiHoaDon(String MaLoaiHoaDon) {
        this.MaLoaiHoaDon = MaLoaiHoaDon;
    }

    public void setGiaTriCPKhac(String GiaTriCPKhac) {
        this.GiaTriCPKhac = GiaTriCPKhac;
    }

    public void setNVPTTT(String NVPTTT) {
        this.NVPTTT = NVPTTT;
    }

    public void setBHTN(String BHTN) {
        this.BHTN = BHTN;
    }

    public void setCongNo(String CongNo) {
        this.CongNo = CongNo;
    }

    public void setDVSC(String DVSC) {
        this.DVSC = DVSC;
    }

    public void setGopGasDuTrenHoaDon(String GopGasDuTrenHoaDon) {
        this.GopGasDuTrenHoaDon = GopGasDuTrenHoaDon;
    }

    public void setGioIn(String GioIn) {
        this.GioIn = GioIn;
    }

    public void setNgayNhanHang(String NgayNhanHang) {
        this.NgayNhanHang = NgayNhanHang;
    }

    public void setTriGiaQuyDoi(String TriGiaQuyDoi) {
        this.TriGiaQuyDoi = TriGiaQuyDoi;
    }

    public void setSupplier(String Supplier) {
        this.Supplier = Supplier;
    }

    public void setKho(String kho) {
        this.kho = kho;
    }

    public void setKho1(String kho1) {
        this.kho1 = kho1;
    }

    public void setSupplier1(String Supplier1) {
        this.Supplier1 = Supplier1;
    }
    
}
