package com.tiha.anphat.ui.booking;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.order.CallInfo;
import com.tiha.anphat.data.entities.order.OrderInfo;

import java.util.List;

public interface BookingContract {

    interface View{
        void onInsertOrderSuccess(OrderInfo item, CallInfo info);

        void onInsertOrderError(String error);

        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);
    }

    interface Presenter{
        void InsertOrder(List<CartInfo> list);

        void GetListAllCart(Integer UserID);

    }
}
