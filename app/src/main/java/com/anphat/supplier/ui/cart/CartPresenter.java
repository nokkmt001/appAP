package com.anphat.supplier.ui.cart;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.condition.ProductPriceCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;
import com.anphat.supplier.data.network.cart.CartModel;
import com.anphat.supplier.data.network.cart.ICartModel;
import com.anphat.supplier.data.network.product.IProductModel;
import com.anphat.supplier.data.network.product.ProductModel;

import java.util.List;

public class CartPresenter implements CartContract.Presenter {
    CartModel model;
    ProductModel modelProduct;
    CartContract.View view;
    BookingModel modelOrder;

    public CartPresenter(CartContract.View view) {
        model = new CartModel();
        modelProduct = new ProductModel();
        this.view = view;
        modelOrder = new BookingModel();
    }

    @Override
    public void GetListCart(Integer UserID) {
        model.GetListAllCart(UserID, new ICartModel.IGetListAllCartFinishListener() {
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
    public void UpdateCart(CartCondition condition) {
        model.UpdateCart(condition, new ICartModel.IUpdateCartFinishListener() {
            @Override
            public void onSuccess(CartCondition info) {
                view.onUpdateCartSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onUpdateCartError(error);
            }
        });
    }

    @Override
    public void DeleteCart(Integer ID) {
        model.DeleteCart(ID, new ICartModel.IDeleteCartFinishListener() {
            @Override
            public void onSuccess() {
                view.onDeleteCartSuccess();
            }

            @Override
            public void onError(String error) {
                view.onDeleteCartError(error);
            }
        });
    }

    @Override
    public void checkBooking() {
        modelOrder.checkBooking(new IBookingModel.IGetBookingFinish() {
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

    @Override
    public void GetProductPriceByUser(ProductPriceCondition condition) {
        modelProduct.GetProductPriceByUserID(condition, new IProductModel.IGetProductPriceByUserIDListener() {
            @Override
            public void onSuccess(Double price) {
                view.onGetDonGiaProductByUserSuccess(price);
            }

            @Override
            public void onError(String error) {
                view.onGetDonGiaProductByUserError(error);
            }
        });
    }

    @Override
    public void GetProductInventory(String maKho, String productID, String date) {
        modelProduct.GetProductInventory(maKho, productID, date, new IProductModel.IGetProductInventoryFinish() {
            @Override
            public void onSuccess(Double result) {
                view.onGetProductInventorySuccess(result);
            }

            @Override
            public void onError(String error) {
                view.onGetProductInventoryError(error);
            }
        });
    }

    @Override
    public void InsertOrder(List<CartInfo> list) {
        modelOrder.InsertOrder(list, new IBookingModel.IInsertOrderFinish() {
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
}
