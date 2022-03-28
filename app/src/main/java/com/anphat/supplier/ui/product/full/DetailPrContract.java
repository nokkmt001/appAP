package com.anphat.supplier.ui.product.full;

import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.order.BookingInfo;

public interface DetailPrContract {

    interface View {
        void onGetProductSuccess(ProductNew info);

        void onGetProductError(String error);

        void onCheckBookingSuccess(BookingInfo info);

        void onCheckBookingError(String error);
    }

    interface Presenter {
        void GetProduct(String ID);

        void checkBooking();

    }
}
