package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.condition.NotificationCondition;
import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.RvList;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationViewModel extends BaseViewModel {

    public NotificationViewModel(@NonNull Application application) {
        super(application);
    }

    public final SingleLiveDate<RvList<NotificationMain>> itemListSuccess = new SingleLiveDate<>();

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
                    public void onComplete() {}
                });
    }
}