package com.anphat.supplier.data.network.repository;

import com.anphat.supplier.utils.AppController;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Repository<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        AppController.addSubscribe(d);
    }

    @Override
    public void onNext(T value) {
        Result(value);
    }

    @Override
    public void onError(Throwable e) {
        Error(e.getMessage());
    }

    @Override
    public void onComplete() {
        Finish();
    }
    protected void Result(T value) {}
    protected void Error(String error) {}
    protected void Finish() {}

}
