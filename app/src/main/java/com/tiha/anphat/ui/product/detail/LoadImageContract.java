package com.tiha.anphat.ui.product.detail;

public interface LoadImageContract {
    interface View {
        void onLoadImageSuccess(String bitMap);

        void onLoadImageError(String error);
    }

    interface Presenter {
        void LoadImage(String ID);
    }

}
