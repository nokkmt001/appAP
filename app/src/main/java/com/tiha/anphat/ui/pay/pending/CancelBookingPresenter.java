package com.tiha.anphat.ui.pay.pending;

import com.tiha.anphat.data.entities.condition.CancelOrderCondition;
import com.tiha.anphat.data.network.booking.BookingModel;
import com.tiha.anphat.data.network.booking.IBookingModel;

public class CancelBookingPresenter implements CancelBookingContract.Presenter {
    BookingModel model;
    CancelBookingContract.View view;

    public CancelBookingPresenter(CancelBookingContract.View view) {
        this.model = new BookingModel();
        this.view = view;
    }

    @Override
    public void CancelBooking(CancelOrderCondition condition) {
        model.CancelOrder(condition, new IBookingModel.ICancelOrderFinish() {
            @Override
            public void onSuccess(Boolean isOk) {
                view.onCancelBookingSuccess();
            }

            @Override
            public void onError(String error) {
                view.onCancelBookingError(error);
            }
        });
    }
}
