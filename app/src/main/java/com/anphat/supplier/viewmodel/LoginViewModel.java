package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.ChangeLogInFo;
import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.condition.CustomerLoginCondition;
import com.anphat.supplier.data.entities.condition.EmptyObject;
import com.anphat.supplier.data.entities.condition.ForGetPassCondition;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.api.RetrofitUpdate;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public final SingleLiveDate<ApiResponseSbke<FCMMobileInfo>> mItemGetFCM = new SingleLiveDate<>();
    public final SingleLiveDate<FCMMobileInfo> mItemInsertFCM = new SingleLiveDate<>();
    public final SingleLiveDate<ApiResponseSbke<NewCustomer>> mItemGetPhone = new SingleLiveDate<>();
    public final SingleLiveDate<ApiResponseSbke<NewCustomer>> mItemCheckPhoneForGetPass = new SingleLiveDate<>();

    public final SingleLiveDate<NewCustomer> mItemCheckIDPass = new SingleLiveDate<>();
    public final SingleLiveDate<NewCustomer> mItemInsert = new SingleLiveDate<>();
    public final SingleLiveDate<NewCustomer> mItemUpdate = new SingleLiveDate<>();
    public final SingleLiveDate<NewCustomer> mItemResendPin = new SingleLiveDate<>();
    public final SingleLiveDate<Boolean> mItemChangePass = new SingleLiveDate<>();

    public final SingleLiveDate<ChangeLogInFo> itemMain = new SingleLiveDate<>();

    API severUpdate = RetrofitUpdate.createService(API.class);

    public void insertFCM(FCMMobileInfo info) {
        serverWs.InsertFCMMobile(info).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<FCMMobileInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<FCMMobileInfo> value) {
                        mItemInsertFCM.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemInsertFCM.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getFCM(String token) {
        serverWs.getFCMMobileFromToken(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<FCMMobileInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<FCMMobileInfo> value) {
                        mItemGetFCM.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemGetFCM.setValue(getError(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void CheckPhone(String sdt) {
        serverWs.getCheckPhone(sdt).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<NewCustomer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<NewCustomer> value) {
                        mItemGetPhone.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemGetPhone.setValue(getError(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void forgetPass(String sdt) {
        serverWs.forgetPass(sdt).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<NewCustomer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<NewCustomer> value) {
                        mItemCheckPhoneForGetPass.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemCheckPhoneForGetPass.setValue(getError(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void changePassWord(ForGetPassCondition condition) {
        serverWs.changePassWord(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<EmptyObject>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<EmptyObject> value) {
                        mItemChangePass.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        mItemChangePass.setValue(false);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void GetLoginByIDPassWord(CustomerLoginCondition condition) {
        serverWs.checkLoginIDPass(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<NewCustomer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<NewCustomer> value) {
                        mItemCheckIDPass.setValue(value.Data);
                        if (value.Status != 0) {
                            showToast(value.Message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemCheckIDPass.setValue(null);
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void InsertNewCustomer(NewCustomer condition) {
        serverWs.insertNewCustomer(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<NewCustomer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<NewCustomer> value) {
                        mItemInsert.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemInsert.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void ReSendPINcode(String id) {
        serverWs.resendPin(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<NewCustomer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<NewCustomer> value) {
                        mItemResendPin.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemResendPin.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void UpdateCustomer(NewCustomer info) {
        serverWs.updateCustomer(info).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<NewCustomer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<NewCustomer> value) {
                        mItemUpdate.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemUpdate.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void checkUpdate() {
        severUpdate.checkUpdate().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChangeLogInFo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ChangeLogInFo value) {
                        if (value != null) {
                            itemMain.postValue(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        itemMain.postValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
