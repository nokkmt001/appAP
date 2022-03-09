package com.tiha.anphatsu.data.network.apiretrofit;

import com.tiha.anphatsu.data.entities.CategoryNew;
import com.tiha.anphatsu.data.entities.ProductNew;

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

}
