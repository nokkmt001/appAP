package com.anphat.supplier.main;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;
import com.anphat.supplier.data.network.cart.CartModel;
import com.anphat.supplier.data.network.cart.ICartModel;
import com.anphat.supplier.data.network.user.IUserModel;
import com.anphat.supplier.data.network.user.UserModel;
import com.anphat.supplier.data.network.product.IProductModel;
import com.anphat.supplier.data.network.product.ProductModel;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    ProductModel modelProduct;
    MainContract.View view;
    CartModel modelCart;
    UserModel modelUser;
    BookingModel model;

    public MainPresenter(MainContract.View view) {
        this.modelProduct = new ProductModel();
        this.view = view;
        this.modelCart = new CartModel();
        this.modelUser = new UserModel();
        this.model = new BookingModel();
    }

    @Override
    public void GetListAllProduct() {
        modelProduct.GetListAllProduct(new IProductModel.IGetListAllProductFinishListener() {
            @Override
            public void onSuccess(List<ProductInfo> list) {
                view.onGetListAllProductSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListAllProductError(error);
            }
        });
    }

    @Override
    public void InSertCart(CartCondition condition) {

    }

    @Override
    public void UpdateCart(CartCondition condition) {

    }

    @Override
    public void DeleteCart(Integer ID) {

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
    public void GetListKho() {
        modelUser.GetListKhoByUser(new IUserModel.IGetListKhoByUserFinish() {
            @Override
            public void onSuccess(List<KhoInfo> list) {
                view.onGetListKhoSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListKhoError(error);
            }
        });
    }

    @Override
    public void GetCategory() {
        modelProduct.GetListCategory(new IProductModel.IGetListCategoryFinish() {
            @Override
            public void onSuccess(List<CategoryInfo> list) {
                view.onGetCategorySuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetCategoryError(error);
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

    @Override
    public void CheckDaHang() {
        model.CheckDaDat(new IBookingModel.ICheckDaDatFinish()  {
            @Override
            public void onSuccess(List<CartInfo> info) {
                if (info.size()==0){
                    sendBooking();
                } else {
                    view.onCheckDaHangSuccess(info);
                }
            }

            @Override
            public void onError(String error) {
                view.onCheckDatHangError(error);
                sendBooking();
            }
        });
    }

    @Override
    public void sendBooking() {
        model.SendBooking(new IBookingModel.ISendBookingFinish() {
            @Override
            public void onSuccess(CallInfo item) {
                view.onSendBookingSuccess(item);
            }

            @Override
            public void onError(String error) {
                view.onSendBookingError(error);
            }
        });
    }
}
