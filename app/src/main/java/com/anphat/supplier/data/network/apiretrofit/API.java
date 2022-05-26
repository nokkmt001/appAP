package com.anphat.supplier.data.network.apiretrofit;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ChangeLogInFo;
import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.ReasonEvaluate;
import com.anphat.supplier.data.entities.condition.CancelOrderCondition;
import com.anphat.supplier.data.entities.condition.CartBooking;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.condition.CustomerLoginCondition;
import com.anphat.supplier.data.entities.condition.EvaluateCondition;
import com.anphat.supplier.data.entities.condition.EmptyObject;
import com.anphat.supplier.data.entities.condition.ForGetPassCondition;
import com.anphat.supplier.data.entities.condition.InsertIntroduceInfo;
import com.anphat.supplier.data.entities.condition.NotificationCondition;
import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;
import com.anphat.supplier.data.entities.presenteruser.InsertPresenterInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API {

    @GET("api/categorys")
    Call<ApiResponse<CategoryNew>> GetCategoryNew();

    @GET()
    Call<ApiResponse<ProductNew>> GetListProductNew(@Url String url);

    @GET("api/products/{id}")
    Call<ResponseData<ProductNew>> GetProduct(@Path("id") String id);

    @GET()
    Call<ApiResponse<BannerInfo>> GetListBanner(@Url String url);

    /**
     * rxjava
     */
    @GET("api/GioHangMobile/GetListGioHang")
    Observable<ApiResponseSbke<List<CartInfo>>> GetListAllCart(@Query("nguoiDungMobileID") Integer ID);

    @GET("api/DonHangMobile/GetDonHangGanNhat")
    Observable<ApiResponseSbke<BookingInfo>> CheckBooking(@Query("nguoiDungMobileID") Integer ID);

    @GET("api/DonHangMobile/KiemTraSanPhamGasDaDatHang")
    Observable<ApiResponseSbke<List<CartInfo>>> CheckDaDat(@Query("nguoiDungMobileID") Integer ID);

    @GET("api/products")
    Observable<ApiResponse<ProductNew>> GetListProductNewAP(@Query("start") Integer begin, @Query("limit") Integer end);

    @GET("api/promotions")
    Observable<ApiResponse<ProductNew>> GetListProductPromotion();

    @GET()
    Observable<ApiResponse<BannerInfo>> GetListBannerAP(@Url String url);

    @GET("api/categorys")
    Observable<ApiResponse<CategoryNew>> GetCategoryNewAP();

    @GET("api/products/{id}")
    Observable<ResponseData<ProductNew>> GetProductAP(@Path("id") String id);

    @GET("api/Kho/GetListKhoByUser")
    Observable<List<KhoInfo>> GetListKho(@Query("userName") String userName);

    /**
     * cart
     */
    @POST("api/GioHangMobile/InsertGioHang")
    Observable<ApiResponseSbke<CartCondition>> insertCart(@Body CartCondition condition);

    @POST("api/GioHangMobile/UpdateGioHang")
    Observable<ApiResponseSbke<CartCondition>> updateCart(@Body CartCondition condition);

    @DELETE("api/GioHangMobile/DeleteGioHang")
    Observable<Objects> deleteCart(@Query("id") String id);

    @GET("api/DonHangMobile/GetListLichSuMuaHang")
    Observable<ApiResponseSbke<List<HistoryBooking>>> GetListHistory(@Query("nguoiDungMobileID") String userName);

    /**
     * booking
     */
    @GET("api/DonHangMobile/GetDonHang")
    Observable<ApiResponseSbke<BookingInfo>> GetDetailHistory(@Query("cuocGoiID") String ID);

    @GET("api/DonHangMobile/GetDonHang")
    Call<ApiResponseSbke<BookingInfo>> GetDetailHistoryDefault(@Query("cuocGoiID") String ID);

    @POST("api/DonHangMobile/InsertDonHang")
    Observable<ApiResponseSbke<OrderInfo>> InsertOrder(@Body CartBooking body);

    @POST("api/DonHangMobile/HuyDonHang")
    Observable<Objects> cancelOrder(@Body CancelOrderCondition body);

    /**
     * introduce
     */

    @GET("api/NguoiGioiThieuMobile/GetListNguoiDaGioiThieu")
    Observable<ApiResponseSbke<List<IntroducePerInfo>>> getListIntroduce(@Query("nguoiDungMobileID") String userName);

    // ?nguoiDungMobileID={nguoiDungMobileID}&soDienThoaiGioiThieu={soDienThoaiGioiThieu}
    //Call<ApiResponseSbke<InsertPresenterInfo>> insertIntroduce(@Query("nguoiDungMobileID") String id, @Query("soDienThoaiGioiThieu") String number);
    @POST("api/NguoiGioiThieuMobile/InsertNguoiGioiThieuMobile")
    Observable<ApiResponseSbke<InsertPresenterInfo>> insertIntroduce(@Body InsertIntroduceInfo object);

    /**
     * vote employee
     */
    @GET("api/DanhGiaNhanVien/GetListLyDoDanhGiaSao")
    Observable<ApiResponseSbke<List<ReasonEvaluate>>> getListReasonEvaluate(@Query("soSao") String number);

    @POST("api/DanhGiaNhanVien/InsertDanhGiaNhanVien")
    Observable<ApiResponseSbke<EvaluateCondition>> InsertEvaluate(@Body EvaluateCondition condition);

    /**
     * token fcm
     */
    @POST("api/FCM/InsertFCMMobileAP")
    Observable<ApiResponseSbke<FCMMobileInfo>> InsertFCMMobile(@Body FCMMobileInfo condition);

    @GET("api/FCM/GetFCMMobileByToken")
    Observable<ApiResponseSbke<FCMMobileInfo>> getFCMMobileFromToken(@Query("Token") String token);

    /**
     * login customer
     */
    @GET("api/NguoiDungMobile/KiemTraNguoiDungBySoDT")
    Observable<ApiResponseSbke<NewCustomer>> getCheckPhone(@Query("soDienThoai") String token);

    @GET("api/NguoiDungMobile/QuenMatKhau")
    Observable<ApiResponseSbke<NewCustomer>> forgetPass(@Query("soDienThoai") String token);

    @POST("api/NguoiDungMobile/InsertNguoiDungMobile")
    Observable<ApiResponseSbke<NewCustomer>> insertNewCustomer(@Body NewCustomer token);

    @POST("api/NguoiDungMobile/DoiMatKhau")
    Observable<ApiResponseSbke<EmptyObject>> changePassWord(@Body ForGetPassCondition token);

    @POST("api/NguoiDungMobile/GetNguoiDungMobileByIDPassword")
    Observable<ApiResponseSbke<NewCustomer>> checkLoginIDPass(@Body CustomerLoginCondition token);

    @PUT("api/NguoiDungMobile/GuiLaiMaPin")
    Observable<ApiResponseSbke<NewCustomer>> resendPin(@Query("id") String token);

    @GET("api/NguoiDungMobile/UpdateNguoiDungMobile")
    Observable<ApiResponseSbke<NewCustomer>> updateCustomer(@Body NewCustomer token);

    @GET("DocumentMobiles/AppUpdate/ANPHATNB/change_log_app.json")
    Observable<ChangeLogInFo> checkUpdate();

    // notification
    @POST("api/ThongBaoMobile/GetListThongBaoMobileByNguoiDungID")
    Observable<ApiResponseSbke<RvList<NotificationMain>>> getListNotification(@Body NotificationCondition condition);

    @POST("api/ThongBaoMobile/DanhDauDaDoc")
    Observable<ApiResponseSbke<EmptyObject>> updateViewNotification(@Body NotificationCondition condition);

    @POST("api/ThongBaoMobile/DanhDauDaDocTatCa")
    Observable<ApiResponseSbke<EmptyObject>> updateViewAllNotification(@Body NotificationCondition condition);

}
