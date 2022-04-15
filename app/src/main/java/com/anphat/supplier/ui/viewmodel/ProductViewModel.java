package com.anphat.supplier.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ApiResponse;
import com.anphat.supplier.data.network.apiretrofit.ResponseData;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;
import com.anphat.supplier.data.network.apiretrofit.SingleLiveDate;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.IoScheduler;

public class ProductViewModel extends BaseViewModel {

    API server = RetrofitTest.createService(API.class);

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    private final SingleLiveDate<List<ProductNew>> mItemProductFull = new SingleLiveDate<>();
    private final SingleLiveDate<List<ProductNew>> mItemProductPromotion = new SingleLiveDate<>();
    private final SingleLiveDate<List<BannerInfo>> mItemBanner = new SingleLiveDate<>();
    private final SingleLiveDate<List<BannerInfo>> mItemSlider = new SingleLiveDate<>();
    private final SingleLiveDate<ProductNew> mItemGetProduct = new SingleLiveDate<>();

    public SingleLiveDate<ProductNew> getDataGetProduct() {
        return mItemGetProduct;
    }

    public SingleLiveDate<List<BannerInfo>> getmItemBanner() {
        return mItemBanner;
    }

    public SingleLiveDate<List<BannerInfo>> getmItemSlider() {
        return mItemSlider;
    }

    public SingleLiveDate<List<ProductNew>> getDataProductPromotion() {
        return mItemProductPromotion;
    }

    public SingleLiveDate<List<ProductNew>> getDataProductSuccess() {
        return mItemProductFull;
    }

    public void getListAllProduct(Integer begin, Integer end) {
        server.GetListProductNewAP(begin, end).subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse<ProductNew>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponse<ProductNew> value) {
                        mItemProductFull.setValue(value.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemProductFull.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getListProductPromotion() {
        server.GetListProductPromotion().subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse<ProductNew>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponse<ProductNew> value) {
                        mItemProductPromotion.setValue(value.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemProductPromotion.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getBanner() {
        server.GetListBannerAP("api/slider").subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse<BannerInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponse<BannerInfo> value) {
                        mItemSlider.setValue(value.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemSlider.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        server.GetListBannerAP("api/banner").subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse<BannerInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ApiResponse<BannerInfo> value) {
                        mItemBanner.setValue(value.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemBanner.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void GetProduct(String ID) {
        server.GetProductAP(ID).subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseData<ProductNew>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(ResponseData<ProductNew> value) {
                        mItemGetProduct.setValue(value.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mItemGetProduct.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
