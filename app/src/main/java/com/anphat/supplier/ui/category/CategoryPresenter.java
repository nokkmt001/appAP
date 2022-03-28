package com.anphat.supplier.ui.category;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ApiResponse;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter implements CategoryContract.Presenter {
    API sv = RetrofitTest.createService(API.class);
    CategoryContract.View view;

    public CategoryPresenter(CategoryContract.View view) {
        this.view = view;
    }

    @Override
    public void GetCategory() {
        sv.GetCategoryNew().enqueue(new Callback<ApiResponse<CategoryNew>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<CategoryNew>> call, @NonNull Response<ApiResponse<CategoryNew>> response) {
                try {
                    ApiResponse<CategoryNew> result = response.body();
                    assert result != null;
                    view.onGetCategorySuccess(result.data);
                } catch (Exception e) {
                    view.onGetCategoryError(e.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<CategoryNew>> call, @NonNull Throwable t) {
                view.onGetCategoryError(t.getMessage());
            }
        });
    }
}
