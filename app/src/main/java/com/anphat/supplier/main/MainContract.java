package com.anphat.supplier.main;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;

import java.util.List;

public interface MainContract {
    interface View {

        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);

        void onCheckBookingSuccess(BookingInfo info);

        void onCheckBookingError(String error);

        void onCheckDaHangSuccess(List<CartInfo> info);

        void onCheckDatHangError(String error);

    }

    interface Presenter {

        void GetListAllCart(Integer UserID);

        void checkBooking();

        void CheckDaHang();

    }
}
