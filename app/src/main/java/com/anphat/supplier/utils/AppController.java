package com.anphat.supplier.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import com.orhanobut.hawk.Hawk;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    public static final String Chanel_id = "FCM_CHANNEL_ID";

    private static AppController mInstance;

    public static Application application;

    private static CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        mInstance = this;
        createNotificationChannel();
        application = this;
    }

    public static void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public static void clearSubscribe() {
        if (mCompositeDisposable == null) {
            return;
        }
        mCompositeDisposable.clear();
        mCompositeDisposable.dispose();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    Chanel_id,
                    "FCM_CHANNEL_ID",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
