package com.anphat.supplier.ui.pay.history;

import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;

import java.util.List;

public class HistoryBookingPresenter implements HistoryBookingContract.Presenter {
    BookingModel model;
    HistoryBookingContract.View view;

    public HistoryBookingPresenter(HistoryBookingContract.View view) {
        this.view = view;
        this.model = new BookingModel();
    }

    @Override
    public void GetListHistoryBooking() {
        model.GetHistoryBooking(new IBookingModel.IGetHistoryBookingSuccess() {
            @Override
            public void onSuccess(List<HistoryBooking> list) {
                view.onGetListHistoryBookingSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListHistoryBookingError(error);
            }
        });
    }
}
