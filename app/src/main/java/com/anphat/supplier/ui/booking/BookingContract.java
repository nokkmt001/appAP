package com.anphat.supplier.ui.booking;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;

import java.util.List;

public interface BookingContract {

    interface View{
        void onInsertOrderSuccess(OrderInfo item, CallInfo info);

        void onInsertOrderError(String error);

        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);

        void onCheckBookingSuccess(BookingInfo info);

        void onCheckBookingError(String error);
    }

    interface Presenter{
        void InsertOrder(List<CartInfo> list);

        void GetListAllCart(Integer UserID);

        void checkBooking();

    }
}
