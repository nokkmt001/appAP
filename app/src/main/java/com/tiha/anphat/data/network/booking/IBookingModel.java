package com.tiha.anphat.data.network.booking;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.entities.order.CallInfo;
import com.tiha.anphat.data.entities.order.OrderInfo;

import java.util.List;

public interface IBookingModel {
    void InsertOrder(List<CartInfo> list,IInsertOrderFinish listener);
    interface IInsertOrderFinish{
        void onSuccess(OrderInfo item, CallInfo info);

        void onError(String error);
    }

    void GetBooking(String SoCT,IGetBookingFinish listener);

    interface IGetBookingFinish{
        void onSuccess(BookingInfo info);

        void onError(String error);
    }
}
