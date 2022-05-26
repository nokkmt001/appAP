package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.data.entities.condition.InsertIntroduceInfo;
import com.anphat.supplier.data.entities.presenteruser.InsertPresenterInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IntroduceViewModel extends BaseViewModel {
    public IntroduceViewModel(@NonNull Application application) {
        super(application);
    }

    public final SingleLiveDate<List<IntroducePerInfo>> itemList = new SingleLiveDate<>();
    public final SingleLiveDate<ApiResponseSbke<InsertPresenterInfo>> itemMain = new SingleLiveDate<>();

    public void GetListIntroduce() {
        serverWs.getListIntroduce(PublicVariables.UserInfo.getNguoiDungMobileID().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<List<IntroducePerInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<List<IntroducePerInfo>> value) {
                        if (value.Status != 0) {
                            showMessage(value.Message);
                            itemList.setValue(null);
                            return;
                        }
                        itemList.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        itemList.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void InsertIntroduce(String ID) {
        InsertIntroduceInfo info = new InsertIntroduceInfo();
        info.nguoiDungMobileID = PublicVariables.UserInfo.getNguoiDungMobileID();
        info.soDienThoaiGioiThieu = ID;
        serverWs.insertIntroduce(info).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<InsertPresenterInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<InsertPresenterInfo> value) {
                        itemMain.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(getError(e));
                        itemMain.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

}
