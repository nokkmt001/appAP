package com.anphat.supplier.ui.product.full;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ResponseData;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductPresenter implements DetailPrContract.Presenter{
    BookingModel model;

    API sv = RetrofitTest.createService(API.class);
    DetailPrContract.View view;

    public DetailProductPresenter( DetailPrContract.View view){
        this.view = view;
        this.model = new BookingModel();
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

    @Override
    public void checkBooking() {
        model.checkBooking(new IBookingModel.IGetBookingFinish() {
            @Override
            public void onSuccess(BookingInfo info) {
                view.onCheckBookingSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onCheckBookingError(error);
            }
        });
    }
}
