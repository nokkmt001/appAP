package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryViewModel extends BaseViewModel{
    API server = RetrofitWsbke.createService(API.class);

    public MutableLiveData<List<HistoryBooking>> ItemList = new MutableLiveData<>();
    public MutableLiveData<BookingInfo> ItemDetail = new MutableLiveData<>();

    public HistoryViewModel(@NonNull Application application) {
        super(application);
    }

    public void getHistoryBooking(){
        server.GetListHistory(PublicVariables.UserInfo.getNguoiDungMobileID().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<List<HistoryBooking>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<List<HistoryBooking>> value) {
                        ItemList.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ItemList.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void GetDetailHistory(String ID){
        server.GetDetailHistory(ID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<BookingInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<BookingInfo> value) {
                        ItemDetail.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ItemDetail.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
