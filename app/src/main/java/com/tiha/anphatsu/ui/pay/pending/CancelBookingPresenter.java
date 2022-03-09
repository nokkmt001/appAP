package com.tiha.anphatsu.ui.pay.pending;

import com.tiha.anphatsu.data.entities.condition.CancelOrderCondition;
import com.tiha.anphatsu.data.entities.order.BookingInfo;
import com.tiha.anphatsu.data.network.booking.BookingModel;
import com.tiha.anphatsu.data.network.booking.IBookingModel;

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
