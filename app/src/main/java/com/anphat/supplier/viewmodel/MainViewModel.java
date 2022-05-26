package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.condition.NotificationCondition;
import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.RvList;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {
    API server = RetrofitWsbke.createService(API.class);

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    private final SingleLiveDate<ApiResponseSbke<List<CartInfo>>> mItem = new SingleLiveDate<>();
    private final SingleLiveDate<ApiResponseSbke<BookingInfo>> mItemCheckBooking = new SingleLiveDate<>();
    private final SingleLiveDate<ApiResponseSbke<List<CartInfo>>> mItemDaHang = new SingleLiveDate<>();
    private final SingleLiveDate<List<KhoInfo>> mItemKho = new SingleLiveDate<>();
    public final SingleLiveDate<RvList<NotificationMain>> itemListSuccess = new SingleLiveDate<>();

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
                        mItem.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItem.setValue(getError(e.getMessage()));
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

    public void GetListKho() {
        server.GetListKho("TIHA").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<KhoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) { addSubscribe(d); }
                    @Override
                    public void onNext(List<KhoInfo> value) {
                        if (value!=null){
                            PublicVariables.listKho = value;
                            mItemKho.setValue(value);
                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        mItemKho.setValue(null); }
                    @Override
                    public void onComplete() {}
                });
    }


    public void getListNotification(NotificationCondition condition) {
        serverWs.getListNotification(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<RvList<NotificationMain>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<RvList<NotificationMain>> value) {
                        itemListSuccess.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        itemListSuccess.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
