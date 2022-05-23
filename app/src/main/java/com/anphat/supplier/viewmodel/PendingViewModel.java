package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.condition.CancelOrderCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.utils.PublicVariables;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PendingViewModel extends BaseViewModel{
    API server = RetrofitWsbke.createService(API.class);

    public PendingViewModel(@NonNull Application application) {
        super(application);
    }
    public final SingleLiveDate<BookingInfo> mItemCheckBooking = new SingleLiveDate<>();
    public final SingleLiveDate<Boolean> mItemCheckCancel= new SingleLiveDate<>();

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
    public void CancelBooking(CancelOrderCondition condition){
        server.cancelOrder(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(Object value) {
                        mItemCheckCancel.setValue(true);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mItemCheckCancel.setValue(false);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
