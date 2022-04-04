package com.anphat.supplier.ui.home;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ApiResponse;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;
import com.anphat.supplier.data.network.cart.CartModel;
import com.anphat.supplier.data.network.cart.ICartModel;
import com.anphat.supplier.data.network.product.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {
    ProductModel model;
    HomeContract.View view;
    CartModel modelCart;
    API sv = RetrofitTest.createService(API.class);


    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.model = new ProductModel();
        this.modelCart = new CartModel();
    }

    @Override
    public void GetListProduct(String url) {
        sv.GetListProductNew(url).enqueue(new Callback<ApiResponse<ProductNew>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<ProductNew>> call, @NonNull Response<ApiResponse<ProductNew>> response) {
                try {
                    ApiResponse<ProductNew> result = response.body();
                    assert result != null;
                    view.onGetListProductSuccess(result.data);
                } catch (Exception e){
                    view.onGetListProductError(e.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<ProductNew>> call, @NonNull Throwable t) {
                view.onGetListProductError(t.getMessage());
            }
        });
    }

    @Override
    public void InsertCart(CartCondition condition) {
        modelCart.InsertCart(condition, new ICartModel.IInsertCartFinishListener() {
            @Override
            public void onSuccess(CartCondition info) {
                view.onInsertCartSuccess(info);
            }
            @Override
            public void onError(String error) {
                view.onInsertCartError(error);
            }
        });
    }

    @Override
    public void GetListBanner(String url) {
        sv.GetListBanner(url).enqueue(new Callback<ApiResponse<BannerInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<BannerInfo>> call, @NonNull Response<ApiResponse<BannerInfo>> response) {
                try {
                    ApiResponse<BannerInfo> result = response.body();
                    assert result != null;
                    view.onGetListBannerSuccess(result.data);
                } catch (Exception e){
                    view.onGetListBannerError(e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<BannerInfo>> call, @NonNull Throwable t) {
                view.onGetListBannerError(t.getMessage());
            }
        });
    }
}
