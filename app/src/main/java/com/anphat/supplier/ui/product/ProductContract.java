package com.anphat.supplier.ui.product;

import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.condition.InventoryCondition;
import com.anphat.supplier.data.entities.condition.ProductCondition;

import java.util.List;

public interface ProductContract {
    interface View{
        void onGetListProductSuccess(List<ProductInfo> list,Integer total);

        void onGetListProductError(String error);

        void onGetImageByProDuctIDSuccess(String imageBitmap);

        void onGetImageByProDuctIDError(String error);

        void onInsertCartSuccess(CartCondition info);

        void onInsertCartError(String error);

        void onGetProductInventorySuccess(Double result);

        void onGetProductInventoryError(String error);

        void onGetListProductInventorySuccess(List<ProductInfo> list);

        void onGetListProductInventoryError(String error);
    }

    interface Presenter{
        void GetListProduct(ProductCondition condition);

        void GetImageByProDuctID(String ID);

        void InsertCart(CartCondition condition);

        void GetProductInventory(String maKho,String productID, String date );

        void GetListProductInventory(InventoryCondition condition) ;

    }
}
