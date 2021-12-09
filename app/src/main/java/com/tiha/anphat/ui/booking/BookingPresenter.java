package com.tiha.anphat.ui.booking;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.entities.order.CallInfo;
import com.tiha.anphat.data.entities.order.OrderInfo;
import com.tiha.anphat.data.network.booking.BookingModel;
import com.tiha.anphat.data.network.booking.IBookingModel;

import java.util.List;

public class BookingPresenter implements BookingContract.Presenter {
    BookingModel model;
    BookingContract.View view;

    public BookingPresenter(BookingContract.View view) {
        this.model = new BookingModel();
        this.view = view;
    }

    @Override
    public void InsertOrder(List<CartInfo> list) {
        model.InsertOrder(list, new IBookingModel.IInsertOrderFinish() {
            @Override
            public void onSuccess(OrderInfo item, CallInfo info) {
                view.onInsertOrderSuccess(item, info);
            }

            @Override
            public void onError(String error) {
                view.onInsertOrderError(error);
            }
        });
    }
}
