package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.data.entities.condition.EmptyObject;
import com.anphat.supplier.data.entities.condition.InsertIntroduceInfo;
import com.anphat.supplier.data.entities.presenteruser.InsertPresenterInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.data.network.presenteruser.IPresenterUserModel;
import com.anphat.supplier.data.network.presenteruser.PresenterUserModel;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroduceViewModel extends BaseViewModel {
    PresenterUserModel model;

    public IntroduceViewModel(@NonNull Application application) {
        super(application);
        model = new PresenterUserModel();
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

//        EmptyObject object = new EmptyObject();
//        object.nguoiDungMobileID = PublicVariables.UserInfo.getNguoiDungMobileID().toString();
//        object.soDienThoaiGioiThieu = ID;
//
//        server.insertIntroduce(object).enqueue(new Callback<ApiResponseSbke<InsertPresenterInfo>>() {
//            @Override
//            public void onResponse(@NonNull Call<ApiResponseSbke<InsertPresenterInfo>> call, @NonNull Response<ApiResponseSbke<InsertPresenterInfo>> response) {
//                if (response.isSuccessful()){
//                    assert response.body() != null;
//                    itemMain.postValue(response.body().Data);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ApiResponseSbke<InsertPresenterInfo>> call, @NonNull Throwable t) {
//                itemMain.postValue(null);
//                showToast(t.getMessage());
//            }
//        });

//        model.InsertPresenter(ID, new IPresenterUserModel.IInsertPresenterFinish() {
//            @Override
//            public void onSuccess(InsertPresenterInfo info) {
//                itemMain.setValue(info);
//            }
//
//            @Override
//            public void onError(String error) {
//                itemMain.setValue(null);
//                showToast(error);
//            }
//        });
    }

}
