package com.anphat.supplier.viewmodel;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.network.repository.Repository;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel extends BaseViewModel{
    API server = RetrofitWsbke.createService(API.class);

    public CartViewModel(@NonNull Application application) {
        super(application);
    }
    public final SingleLiveDate<ApiResponseSbke<CartCondition>> mItemInsertCart = new SingleLiveDate<>();
    public final SingleLiveDate<CartCondition> mItemUpdateCart = new SingleLiveDate<>();
    public final SingleLiveDate<Boolean> mItemDeleteCart = new SingleLiveDate<>();
    public final SingleLiveDate<List<CartInfo>> itemListCart = new SingleLiveDate<>();

    public void GetListAllCart() {
        server.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID()).compose(schedulers())
                .subscribe(new Repository<ApiResponseSbke<List<CartInfo>>>() {
                    @Override
                    protected void Result(ApiResponseSbke<List<CartInfo>> value) {
                        super.Result(value);
                        itemListCart.setValue(value.Data);
                    }
                });
    }

    public void insertCart(CartCondition condition) {
        server.insertCart(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<CartCondition>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }
                    @Override
                    public void onNext(ApiResponseSbke<CartCondition> value) {
                        mItemInsertCart.setValue(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mItemInsertCart.setValue(getError(e.getMessage()));
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    public void UpdateCart(CartCondition condition){
        server.updateCart(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<CartCondition>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }
                    @Override
                    public void onNext(ApiResponseSbke<CartCondition> value) {
                        mItemUpdateCart.setValue(value.Data);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mItemUpdateCart.setValue(null);
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void DeleteCart(String ID){
        server.deleteCart(ID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }
                    @Override
                    public void onNext(Object value) {
                        mItemDeleteCart.setValue(true);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mItemDeleteCart.setValue(false);
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
