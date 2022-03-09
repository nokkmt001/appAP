package com.tiha.anphatsu.ui.home;

import androidx.annotation.NonNull;

import com.tiha.anphatsu.data.entities.ProductInfo;
import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.data.entities.condition.ProductCondition;
import com.tiha.anphatsu.data.network.apiretrofit.API;
import com.tiha.anphatsu.data.network.apiretrofit.ApiResponse;
import com.tiha.anphatsu.data.network.apiretrofit.RetrofitTest;
import com.tiha.anphatsu.data.network.cart.CartModel;
import com.tiha.anphatsu.data.network.cart.ICartModel;
import com.tiha.anphatsu.data.network.product.IProductModel;
import com.tiha.anphatsu.data.network.product.ProductModel;

import java.util.List;

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
}
