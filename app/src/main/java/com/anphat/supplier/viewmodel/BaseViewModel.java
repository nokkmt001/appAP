package com.anphat.supplier.viewmodel;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;

import com.anphat.supplier.R;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.utils.NetworkUtils;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava2.HttpException;

public class BaseViewModel extends AndroidViewModel implements Consumer<Disposable> {
    private CompositeDisposable mCompositeDisposable;
    protected API serverWs = RetrofitWsbke.createService(API.class);

    Application application;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
        this.application = application;
    }

    public static ObservableTransformer schedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

    protected String getError(Throwable messages) {
        try {
            String man = ((HttpException) messages).response().errorBody().string();
            ApiResponseSbke result = ApiResponseSbke.getInfo(man);
            return result.Message;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    protected void showMessage(String error) {
        error = error.isEmpty() ? application.getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(application)) {
            error = application.getString(R.string.error_msg_no_internet);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(application);
        builder.setTitle(application.getString(R.string.title_error_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(application.getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(application, mToastMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
        mCompositeDisposable.dispose();
        System.out.print("ClearVM-------------------------------");
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
