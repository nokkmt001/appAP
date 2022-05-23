package com.anphat.supplier.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.condition.CartBooking;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.utils.PublicVariables;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookingViewModel extends BaseViewModel {

    API server = RetrofitWsbke.createService(API.class);

    public BookingViewModel(@NonNull Application application) {
        super(application);
    }

    public final SingleLiveDate<OrderInfo> itemBooking = new SingleLiveDate<>();
    public final SingleLiveDate<List<CartInfo>> itemListCart = new SingleLiveDate<>();

    public final SingleLiveDate<BookingInfo> mItemCheckBooking = new SingleLiveDate<>();

    public void InsertOrder(List<CartInfo> list) {
        CartBooking booking = new CartBooking();
        booking.ListGioHang = new Gson().toJson(list);
        booking.HeDieuHanh = "APPANDROID";
        booking.NguoiDungMobileID = PublicVariables.UserInfo.getNguoiDungMobileID();
        Log.d("ITEMBOOKING", new Gson().toJson(booking));
        server.InsertOrder(booking).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<OrderInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<OrderInfo> value) {
                        if (value.Status!=0){
                            showToast(value.Message);
                        }
                        itemBooking.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        itemBooking.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void GetListAllCart() {
        server.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<List<CartInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<List<CartInfo>> value) {
                        itemListCart.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        itemListCart.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void checkBooking() {
        server.CheckBooking(PublicVariables.UserInfo.getNguoiDungMobileID()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<BookingInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<BookingInfo> value) {
                        mItemCheckBooking.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemCheckBooking.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
