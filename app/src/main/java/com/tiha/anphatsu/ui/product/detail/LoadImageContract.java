package com.tiha.anphatsu.ui.product.detail;

public interface LoadImageContract {
    interface View {
        void onLoadImageSuccess(String bitMap);

        void onLoadImageError(String error);
    }

    interface Presenter {
        void LoadImage(String ID);
    }

}
