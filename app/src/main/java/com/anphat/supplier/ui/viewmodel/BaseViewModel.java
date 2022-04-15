package com.anphat.supplier.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.anphat.supplier.data.network.api.ApiResponseSbke;

import java.io.File;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseViewModel extends AndroidViewModel implements Consumer<Disposable> {
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }

    public static <T> ApiResponseSbke<T> getError(String error) {
        ApiResponseSbke<T> object = new ApiResponseSbke<>();
        object.Status = -1;
        object.Message = error;
        return object;
    }

    public static MultipartBody getMultipartBody(Map<String, String> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static MultipartBody getMultipartFileBody(Map<String, String> key, Map<String, File> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : key.keySet()) {
            builder.addFormDataPart(str, key.get(str));
        }
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str).getName(), RequestBody.create(MediaType.parse("*/*"), keySet.get(str)));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    @Override
    public void accept(Disposable disposable) {
        addSubscribe(disposable);
    }

    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
