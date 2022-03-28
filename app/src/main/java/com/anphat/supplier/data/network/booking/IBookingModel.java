package com.anphat.supplier.data.network.booking;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.condition.CancelOrderCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;

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

    void checkBooking(IGetBookingFinish listener);

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

    void CheckDaDat(ICheckDaDatFinish listener);

    void SendBooking(ISendBookingFinish listener);

    interface ICheckDaDatFinish{
        void onSuccess(List<CartInfo> item);

        void onError(String error);
    }

    interface ISendBookingFinish{
        void onSuccess(CallInfo item);

        void onError(String error);
    }

}
