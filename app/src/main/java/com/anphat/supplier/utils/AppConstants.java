package com.anphat.supplier.utils;

public class AppConstants {
    /**
     * API
     */
    public static String SERVER_NAME = "anphatapp.tiha.vn/";
    //    public static String FTP_USERNAME = "demogas";
//    public static String FTP_PASSWORD = "tiha@demo.123$";
    public static String URL_SERVER = "http://" + SERVER_NAME;
    public static String URL_IMAGE = "http://" + SERVER_NAME + "/resources/filereports/";
    public static String URL_UPDATE_CHANGELOG = "http://autoupdate.tiha.vn/DocumentMobiles/AppUpdate/ANPHATNB/change_log_app.json";

    public static final String Error_KetNoiServer = "Kết nối đến Server thất bại, hãy thử lại!";
    public static final String Error_KhongCoInternet = "Không có kết nối Internet, hãy thử lại!";
    //login
    public static final String URL_CHECK_PHONE = "api/NguoiDungMobile/KiemTraNguoiDungBySoDT?soDienThoai={0}";
    public static final String URL_INSERT_NEW_CUSTOMER = "api/NguoiDungMobile/InsertNguoiDungMobile";
    public static final String URL_LOGIN_BY_ID_PASS_WORD = "api/NguoiDungMobile/GetNguoiDungMobileByIDPassword";
    public static final String URL_RESEND_PIN = "api/NguoiDungMobile/GuiLaiMaPin?id={0}";
    public static final String URL_UPDATE_CUSTOMER = "api/NguoiDungMobile/UpdateNguoiDungMobile";
    //cart
    public static final String URL_INSERT_CART = "api/GioHangMobile/InsertGioHang";
    public static final String URL_UPDATE_CART = "api/GioHangMobile/UpdateGioHang";
    public static final String URL_DELETE_CART = "api/GioHangMobile/DeleteGioHang?id={0}";
    public static final String URL_GET_LIST_CART = "api/GioHangMobile/GetListGioHang?nguoiDungMobileID={0}";
    public static final String URL_GET_CATEGORY = "api/LoaiHang/GetListLoaiHangByUser?userName={0}";
    // evaluate
    public static final String URL_GET_LIST_EVALUATE = "api/DanhGiaNhanVien/GetListLyDoDanhGiaSao?soSao={0}";
    public static final String URL_INSERT_EVALUATE = "api/DanhGiaNhanVien/InsertDanhGiaNhanVien";

    //product
    public static final String URL_GET_LIST_All_PRODUCT = "api/Product/GetListAllProduct";
    public static final String URL_GET_LIST_PRODUCT = "api/Product/GetListProduct";
    public static final String URL_GET_IMAGE_PRODUCT = "api/Product/GetHinhAnhByProductID?productID={0}";
    public static final String URL_GET_PRICE_PRODUCT = "api/Product/GetProductDonGiaByNguoiDungMobileID";
    public static final String URL_GET_PRODUCT_TON_KHO = "api/Product/GetProductTonKho?makho={0}&productID={1}&ngay={2}";
    public static final String URL_GetListTonKho = "api/PhanHeKho/GetListTonKho";
    //booking
    public static final String URL_GetDonHang = "api/DonHangMobile/GetDonHang?cuocGoiID={0}"; // chi tiết đơn hàng + price
    public static final String URL_InsertDonHang = "api/DonHangMobile/InsertDonHang";
    public static final String URL_CheckBooking = "api/DonHangMobile/GetDonHangGanNhat?nguoiDungMobileID={0}";
    public static final String URL_CheckDaDat = "api/DonHangMobile/KiemTraSanPhamGasDaDatHang?nguoiDungMobileID={0}";
    public static final String URL_SendRequestBooking = "api/DonHangMobile/GuiYeuCauDatHang";

    // history
    public static final String URL_GetListHISTORYBOOKING = "api/DonHangMobile/GetListLichSuMuaHang?nguoiDungMobileID={0}";
    public static final String URL_HuyDonHang = "api/DonHangMobile/HuyDonHang";
    // user location
    public static final String URL_InsertUserLocation = "api/UserLocation/InsertUserLocation";
    public static final String URL_GetListUserLocation = "api/UserLocation/GetListUserLocation";

    //kho
    public static final String URL_GetListKhoByUser = "api/Kho/GetListKhoByUser?userName={0}";
    // nguoi gioi thieu
    public static final String URL_InsertPresenter = "api/NguoiGioiThieuMobile/InsertNguoiGioiThieuMobile?nguoiDungMobileID={0}&soDienThoaiGioiThieu={1}";
    public static final String URL_GetListPresenter = "api/NguoiGioiThieuMobile/GetListNguoiDaGioiThieu?nguoiDungMobileID={0}";
    //FCM
    public static String URL_InsertFCMMobile = "api/FCM/InsertFCMMobileAP";
    public static String URL_GetFCMFromTokenDevice = "api/FCM/GetFCMMobileByToken?Token={0}";

    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    /**
     * Max size a image (kb)
     */
    public static int MAX_SIZE_IMAGE = 500;
    public static long DELAY_FIND_DATA = 0;
    public static long DELAY_FIND_DATA_SEARCH = 1000;

    //px
    public static int MAX_WIDTH_IMAGE = 1000;
    public static int MAX_HEIGHT_IMAGE = 1000;
    public static int config_shortAnimTime = 200;

    public static final String Error_Unknown = "Đã xảy ra lỗi không mong muốn";

    private AppConstants() {

    }
}
