package com.anphat.supplier.ui.cart;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.condition.ProductPriceCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;

import java.util.List;

public interface CartContract {
    interface View {
        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);

        void onUpdateCartSuccess(CartCondition info);

        void onUpdateCartError(String error);

        void onDeleteCartSuccess();

        void onDeleteCartError(String error);

        void onGetDonGiaProductByUserSuccess(Double price);

        void onGetDonGiaProductByUserError(String error);

        void onGetProductInventorySuccess(Double result);

        void onGetProductInventoryError(String error);

        void onInsertOrderSuccess(OrderInfo item, CallInfo info);

        void onInsertOrderError(String error);

        void onCheckBookingSuccess(BookingInfo info);

        void onCheckBookingError(String error);
    }

    interface Presenter {
        void GetListCart(Integer UserID);

        void UpdateCart(CartCondition condition);

        void DeleteCart(Integer ID);

        void checkBooking();

        void GetProductPriceByUser(ProductPriceCondition condition);

        void GetProductInventory(String maKho, String productID, String date);

        void InsertOrder(List<CartInfo> list);

//        void InsertUserLocation(InsertLocationInfo info);
    }
}
