package com.anphat.supplier.main;

import androidx.annotation.NonNull;

import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ApiResponse;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;
import com.anphat.supplier.data.network.cart.CartModel;
import com.anphat.supplier.data.network.cart.ICartModel;
import com.anphat.supplier.data.network.user.IUserModel;
import com.anphat.supplier.data.network.user.UserModel;
import com.anphat.supplier.data.network.product.IProductModel;
import com.anphat.supplier.data.network.product.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {
    ProductModel modelProduct;
    MainContract.View view;
    CartModel modelCart;
    UserModel modelUser;
    BookingModel model;
    API sv = RetrofitTest.createService(API.class);

    public MainPresenter(MainContract.View view) {
        this.modelProduct = new ProductModel();
        this.view = view;
        this.modelCart = new CartModel();
        this.modelUser = new UserModel();
        this.model = new BookingModel();
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

    @Override
    public void CheckDaHang() {
        model.CheckDaDat(new IBookingModel.ICheckDaDatFinish()  {
            @Override
            public void onSuccess(List<CartInfo> info) {
                if (info.size()==0){
//                    sendBooking();
                } else {
                    view.onCheckDaHangSuccess(info);
                }
            }

            @Override
            public void onError(String error) {
                view.onCheckDatHangError(error);
//                sendBooking();
            }
        });
    }

}
