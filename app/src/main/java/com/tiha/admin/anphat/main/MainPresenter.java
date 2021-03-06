package com.tiha.admin.anphat.main;

import com.tiha.admin.anphat.data.entities.condition.CartCondition;
import com.tiha.admin.anphat.data.entities.CartInfo;
import com.tiha.admin.anphat.data.entities.CategoryInfo;
import com.tiha.admin.anphat.data.entities.ProductInfo;
import com.tiha.admin.anphat.data.entities.kho.KhoInfo;
import com.tiha.admin.anphat.data.network.cart.CartModel;
import com.tiha.admin.anphat.data.network.cart.ICartModel;
import com.tiha.admin.anphat.data.network.user.IUserModel;
import com.tiha.admin.anphat.data.network.user.UserModel;
import com.tiha.admin.anphat.data.network.product.IProductModel;
import com.tiha.admin.anphat.data.network.product.ProductModel;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    ProductModel modelProduct;
    MainContract.View view;
    CartModel modelCart;
    UserModel modelUser;

    public MainPresenter(MainContract.View view) {
        this.modelProduct = new ProductModel();
        this.view = view;
        this.modelCart = new CartModel();
        this.modelUser = new UserModel();
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
}
