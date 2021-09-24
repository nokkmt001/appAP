package com.tiha.anphat.utils;

public class AppConstants {
    /**
     * API
     */
    public static String SERVER_NAME = "anphatapp.tiha.vn/";
    //    public static String FTP_USERNAME = "demogas";
//    public static String FTP_PASSWORD = "tiha@demo.123$";
    public static String URL_SERVER = "http://" + SERVER_NAME;
    public static String URL_IMAGE = "http://" + SERVER_NAME + "/resources/filereports/";
    public static String URL_UPDATE_CHANGELOG = URL_SERVER + "/resources/filereports/appupdate/update-changelog.json";
    public static final String URL_UPDATE_FILE_APK = "http://autoupdate.tiha.vn/DocumentMobiles/AppUpdate/TiHaGas/BMTiHaGas.apk";

    public static final String Error_KetNoiServer = "Kết nối đến Server thất bại, hãy thử lại!";
    public static final String Error_KhongCoInternet = "Không có kết nối Internet, hãy thử lại!";
    //login
    public static final String URL_CHECK_PHONE = "api/NguoiDungMobile/KiemTraNguoiDungBySoDT?soDienThoai={0}";
    public static final String URL_INSERT_NEW_CUSTOMER = "api/NguoiDungMobile/InsertNguoiDungMobile";
    public static final String URL_LOGIN_BY_ID_PASS_WORD = "api/NguoiDungMobile/GetNguoiDungMobileByIDPassword?id={0}&password={1}";
    public static final String URL_RESEND_PIN = "api/NguoiDungMobile/GuiLaiMaPin?id={0}";
    //cart
    public static final String URL_INSERT_CART = "api/GioHangMobile/InsertGioHang";
    public static final String URL_UPDATE_CART = "api/GioHangMobile/UpdateGioHang";
    public static final String URL_DELETE_CART = "api/GioHangMobile/DeleteGioHang?id={0}";
    public static final String URL_GET_LIST_CART = "api/GioHangMobile/GetListGioHang?nguoiDungMobileID={0}";
    //product
    public static final String URL_GET_LIST_All_PRODUCT = "api/Product/GetListAllProduct";
    public static final String URL_GET_LIST_PRODUCT = "api/Product/GetListProduct";
    public static final String URL_GET_IMAGE_PRODUCT = "api/Product/GetHinhAnhByProductID?productID={0}";

    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    /**
     * Max size a image (kb)
     */
    public static int MAX_SIZE_IMAGE = 500;
    public static long DELAY_FIND_DATA = 0;
    //px
    public static int MAX_WIDTH_IMAGE = 1000;
    public static int MAX_HEIGHT_IMAGE = 1000;
    public static int config_shortAnimTime = 200;

    public static final String Error_Unknown = "Đã xảy ra lỗi không mong muốn";

    private AppConstants() {
    }


}
