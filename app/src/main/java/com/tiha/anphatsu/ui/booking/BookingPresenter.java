package com.tiha.anphatsu.ui.booking;

import com.tiha.anphatsu.data.entities.CartInfo;
import com.tiha.anphatsu.data.entities.order.CallInfo;
import com.tiha.anphatsu.data.entities.order.OrderInfo;
import com.tiha.anphatsu.data.network.booking.BookingModel;
import com.tiha.anphatsu.data.network.booking.IBookingModel;
import com.tiha.anphatsu.data.network.cart.CartModel;
import com.tiha.anphatsu.data.network.cart.ICartModel;

import java.util.List;

public class BookingPresenter implements BookingContract.Presenter {
    BookingModel model;
    BookingContract.View view;
    CartModel modelCart;

    public BookingPresenter(BookingContract.View view) {
        this.model = new BookingModel();
        this.view = view;
        this.modelCart = new CartModel();

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

    @Override
    public void GetListAllCart(Integer UserID) {
        modelCart.GetListAllCart(UserID, new ICartModel.IGetListAllCartFinishListener() {
            @Override
            public void onSuccess(List<CartInfo> list) {
                view.onGetListAllCartSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListAllCartError(error);
            }
        });
    }
}
