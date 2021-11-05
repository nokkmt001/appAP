package com.tiha.anphat.ui.product.detail;

import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;

public class LoadImagePresenter implements LoadImageContract.Presenter {
    ProductModel model;
    LoadImageContract.View view;

    public LoadImagePresenter(LoadImageContract.View view) {
        this.view = view;
        this.model = new ProductModel();
    }

    @Override
    public void LoadImage(String ID) {
        model.GetImageFromProductID(ID, new IProductModel.IGetImageFromProductIDFinishListener() {
            @Override
            public void onSuccess(String imageBitmap) {
                view.onLoadImageSuccess(imageBitmap);
            }

            @Override
            public void onError(String error) {
                view.onLoadImageError(error);
            }
        });
    }
}
