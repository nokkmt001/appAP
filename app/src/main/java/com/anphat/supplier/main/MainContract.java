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
        void onGetListAllProductSuccess(List<ProductInfo> list);

        void onGetListAllProductError(String error);

        void onInSertCartSuccess(CartCondition info);

        void onInsertCartError(String error);

        void onUpdateCartSuccess(CartCondition info);

        void onUpdateConditionError(String error);

        void onDeleteCartSuccess();

        void onDeleteCartError(String error);

        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);

        void onGetListKhoSuccess(List<KhoInfo> list);

        void onGetListKhoError(String error);

        void onGetCategorySuccess(List<CategoryInfo>list);

        void onGetCategoryError(String error);

        void onCheckBookingSuccess(BookingInfo info);

        void onCheckBookingError(String error);

        void onCheckDaHangSuccess(List<CartInfo> info);

        void onCheckDatHangError(String error);

        void onSendBookingSuccess(CallInfo item);

        void onSendBookingError(String error);

        void onGetListProductNewSuccess(List<ProductNew> list);

        void onGetListProductNewError(String error);
    }

    interface Presenter {
        void GetListAllProduct();

        void InSertCart(CartCondition condition);

        void UpdateCart(CartCondition condition);

        void DeleteCart(Integer ID);

        void GetListAllCart(Integer UserID);

        void GetListKho();

        void GetCategory();

        void checkBooking();

        void CheckDaHang();

        void sendBooking();

        void GetListProductNew(String url);

    }
}
