package com.anphat.supplier.data.network.apiretrofit;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("api/GioHangMobile/InsertGioHang")
    Observable<ApiResponseSbke<CartCondition>> insertCart(@Body CartCondition condition);

    @GET("api/DonHangMobile/GetListLichSuMuaHang")
    Observable<ApiResponseSbke<List<HistoryBooking>>> GetListHistory(@Query("nguoiDungMobileID") String userName);

    @GET("api/DonHangMobile/GetDonHang")
    Observable<ApiResponseSbke<BookingInfo>> GetDetailHistory(@Query("cuocGoiID") String ID);
}
