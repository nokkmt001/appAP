package com.anphat.supplier.data.network.apiretrofit;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface API {

   @GET("api/categorys")
   Call<ApiResponse<CategoryNew>> GetCategoryNew() ;

   @GET()
   Call<ApiResponse<ProductNew>> GetListProductNew(@Url String url);

   @GET("api/products/{id}")
   Call<ResponseData<ProductNew>> GetProduct(@Path("id") String id);

   @GET()
   Call<ApiResponse<BannerInfo>> GetListBanner(@Url String url);

}
