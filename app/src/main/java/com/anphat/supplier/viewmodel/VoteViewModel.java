package com.anphat.supplier.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.ReasonEvaluate;
import com.anphat.supplier.data.entities.condition.EvaluateCondition;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VoteViewModel extends BaseViewModel {
    API server = RetrofitWsbke.createService(API.class);

    public VoteViewModel(@NonNull Application application) {
        super(application);
    }

    public final SingleLiveDate<List<ReasonEvaluate>> itemList = new SingleLiveDate<>();
    public final SingleLiveDate<EvaluateCondition> itemMain = new SingleLiveDate<>();

    public void GetListVote(String ID) {
        server.getListReasonEvaluate(ID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<List<ReasonEvaluate>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<List<ReasonEvaluate>> value) {
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

    public void InsertVote(EvaluateCondition condition) {
        server.InsertEvaluate(condition).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseSbke<EvaluateCondition>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponseSbke<EvaluateCondition> value) {
                        itemMain.setValue(value.Data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        itemMain.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
