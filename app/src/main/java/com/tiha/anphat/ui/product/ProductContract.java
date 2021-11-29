package com.tiha.anphat.ui.product;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.InventoryCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.data.entities.product.FullProductInfo;

import java.util.List;

public interface ProductContract {
    interface View{
        void onGetListProductSuccess(List<ProductInfo> list,Integer total);

        void onGetListProductError(String error);

        void onGetImageByProDuctIDSuccess(String imageBitmap);

        void onGetImageByProDuctIDError(String error);

        void onGetProductInventorySuccess(Double result);

        void onGetProductInventoryError(String error);

        void onGetListProductInventorySuccess(List<ProductInfo> list);

        void onGetListProductInventoryError(String error);

//        void onGetProductSuccess(FullProductInfo info);
//
//        void onGetProductError(String error);
//
//        void onUpdateProductSuccess(FullProductInfo info);
//
//        void onUpdateProductError(String  error);
    }

    interface Presenter{
        void GetListProduct(ProductCondition condition);

        void GetImageByProDuctID(String ID);

        void GetProductInventory(String maKho,String productID, String date );

        void GetListProductInventory(InventoryCondition condition) ;

    }
}
