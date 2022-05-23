package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ResponseData;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends CartViewModel {
    API server = RetrofitWsbke.createService(API.class);
    API serverW = RetrofitTest.createService(API.class);

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public final SingleLiveDate<ApiResponseSbke<List<CartInfo>>> mItem = new SingleLiveDate<>();
    public final SingleLiveDate<ApiResponseSbke<BookingInfo>> mItemCheckBooking = new SingleLiveDate<>();
    public final SingleLiveDate<ApiResponseSbke<List<CartInfo>>> mItemDaHang = new SingleLiveDate<>();
    public final SingleLiveDate<List<KhoInfo>> mItemKho = new SingleLiveDate<>();
    public final SingleLiveDate<ProductNew> mItemGetProduct = new SingleLiveDate<>();

    public SingleLiveDate<List<KhoInfo>> getmItemKho() {
        return mItemKho;
    }

    public SingleLiveDate<ApiResponseSbke<BookingInfo>> getmItemCheckBooking() {
        return mItemCheckBooking;
    }

    public SingleLiveDate<ApiResponseSbke<List<CartInfo>>> getmItemDaHang() {
        return mItemDaHang;
    }


    public SingleLiveDate<ApiResponseSbke<List<CartInfo>>> getItem() {
        return mItem;
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
                        mItemCheckBooking.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemCheckBooking.setValue(getError(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void CheckDaHang() {
        server.CheckDaDat(PublicVariables.UserInfo.getNguoiDungMobileID()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<List<CartInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }
                    @Override
                    public void onNext(ApiResponseSbke<List<CartInfo>> value) {
                        mItemDaHang.setValue(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mItemDaHang.setValue(getError(e.getMessage()));
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void GetProduct(String ID) {
        serverW.GetProductAP(ID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseData<ProductNew>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ResponseData<ProductNew> value) {
                        mItemGetProduct.setValue(value.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemGetProduct.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
