package com.tiha.anphatsu.ui.product.full;

import androidx.annotation.NonNull;

import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.network.apiretrofit.API;
import com.tiha.anphatsu.data.network.apiretrofit.ResponseData;
import com.tiha.anphatsu.data.network.apiretrofit.RetrofitTest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductPresenter implements DetailPrContract.Presenter{

    API sv = RetrofitTest.createService(API.class);
    DetailPrContract.View view;

    public DetailProductPresenter( DetailPrContract.View view){
        this.view = view;
    }

    @Override
    public void GetProduct(String ID) {
        sv.GetProduct(ID).enqueue(new Callback<ResponseData<ProductNew>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<ProductNew>> call, @NonNull Response<ResponseData<ProductNew>> response) {
              try {
                  ResponseData<ProductNew> result = response.body();
                  assert result != null;
                  view.onGetProductSuccess(result.data);
              } catch (Exception e){
                  view.onGetProductError(e.getMessage());
              }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseData<ProductNew>> call, @NonNull Throwable t) {
                view.onGetProductError(t.getMessage());
            }
        });
    }
}
