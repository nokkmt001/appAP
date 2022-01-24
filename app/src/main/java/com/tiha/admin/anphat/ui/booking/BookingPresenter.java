package com.tiha.admin.anphat.ui.booking;

import com.tiha.admin.anphat.data.entities.order.BookingInfo;
import com.tiha.admin.anphat.data.network.booking.BookingModel;
import com.tiha.admin.anphat.data.network.booking.IBookingModel;

public class BookingPresenter implements BookingContract.Presenter {
    BookingModel model;
    BookingContract.View view;

    public BookingPresenter(BookingContract.View view) {
        this.model = new BookingModel();
        this.view = view;
    }

    @Override
    public void GetBooking(String soCT) {
        model.GetBooking(soCT, new IBookingModel.IGetBookingFinish() {
            @Override
            public void onSuccess(BookingInfo info) {
                view.GetBookingSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.GetBookingError(error);
            }
        });
    }
}
