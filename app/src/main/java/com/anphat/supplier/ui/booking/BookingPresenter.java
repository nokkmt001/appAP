package com.anphat.supplier.ui.booking;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;
import com.anphat.supplier.data.network.cart.CartModel;
import com.anphat.supplier.data.network.cart.ICartModel;

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

    @Override
    public void checkBooking() {
        model.checkBooking(new IBookingModel.IGetBookingFinish() {
            @Override
            public void onSuccess(BookingInfo info) {
                view.onCheckBookingSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onCheckBookingError(error);
            }
        });
    }
}
