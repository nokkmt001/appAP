package com.tiha.anphatsu.ui.cart;

import com.tiha.anphatsu.data.entities.CartInfo;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.data.entities.condition.ProductPriceCondition;
import com.tiha.anphatsu.data.entities.location.InsertLocationInfo;
import com.tiha.anphatsu.data.entities.order.CallInfo;
import com.tiha.anphatsu.data.entities.order.OrderInfo;

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

//        void onInsertUserLocationSuccess(InsertLocationInfo);
//
//        void onInsertUserLocationError();
    }

    interface Presenter {
        void GetListCart(Integer UserID);

        void UpdateCart(CartCondition condition);

        void DeleteCart(Integer ID);

        void GetProductPriceByUser(ProductPriceCondition condition);

        void GetProductInventory(String maKho, String productID, String date);

        void InsertOrder(List<CartInfo> list);

//        void InsertUserLocation(InsertLocationInfo info);
    }
}
