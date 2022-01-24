package com.tiha.admin.anphat.data.network.booking;

import com.tiha.admin.anphat.data.entities.CartInfo;
import com.tiha.admin.anphat.data.entities.HistoryBooking;
import com.tiha.admin.anphat.data.entities.condition.CancelOrderCondition;
import com.tiha.admin.anphat.data.entities.order.BookingInfo;
import com.tiha.admin.anphat.data.entities.order.CallInfo;
import com.tiha.admin.anphat.data.entities.order.OrderInfo;

import java.util.List;

public interface IBookingModel {
    void InsertOrder(List<CartInfo> list, IInsertOrderFinish listener);
    interface IInsertOrderFinish{
        void onSuccess(OrderInfo item, CallInfo info);

        void onError(String error);
    }

    void GetBooking(String SoCT,IGetBookingFinish listener);

    interface IGetBookingFinish{
        void onSuccess(BookingInfo info);

        void onError(String error);
    }

    void GetHistoryBooking(IGetHistoryBookingSuccess listener);
    interface IGetHistoryBookingSuccess{
        void onSuccess(List<HistoryBooking> list);

        void onError(String error);
    }

    void CancelOrder(CancelOrderCondition condition, ICancelOrderFinish listener);
    interface ICancelOrderFinish{
        void onSuccess(Boolean isOk);

        void onError(String error);
    }
}
