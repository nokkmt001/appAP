package com.anphat.supplier.data.network.product;

import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.InventoryCondition;
import com.anphat.supplier.data.entities.condition.ProductCondition;
import com.anphat.supplier.data.entities.condition.ProductPriceCondition;

import java.util.List;

public interface IProductModel {
    void GetListAllProduct(IGetListAllProductFinishListener listener);

    interface IGetListAllProductFinishListener {
        void onSuccess(List<ProductInfo> list);

        void onError(String error);
    }

    void GetListProduct(ProductCondition condition, IGetListProductFinishListener listener);

    interface IGetListProductFinishListener {
        void onSuccess(List<ProductInfo> list, Integer counter);

        void onError(String error);

    }

    void GetImageFromProductID(String productID, IGetImageFromProductIDFinishListener listener);

    interface IGetImageFromProductIDFinishListener {
        void onSuccess(String imageBitmap);

        void onError(String error);
    }

    void GetProductPriceByUserID(ProductPriceCondition condition, IGetProductPriceByUserIDListener listener);

    interface IGetProductPriceByUserIDListener {
        void onSuccess(Double price);

        void onError(String error);
    }

    void GetProductInventory(String makho, String productID, String Ngay,IGetProductInventoryFinish listener); // tồn kho

    interface IGetProductInventoryFinish {
        void onSuccess(Double result);

        void onError(String error);
    }

    void GetListProductInventory(InventoryCondition condition,IGetAllProductInventoryFinish listener);
    interface IGetAllProductInventoryFinish {
        void onSuccess(List<ProductInfo> list);

        void onError(String error);
    }

    void GetListCategory(IGetListCategoryFinish listener);
    interface IGetListCategoryFinish{
        void onSuccess(List<CategoryInfo>list);

        void onError(String error);
    }

    void GetProductNew(String ID,IGetProductNewFinish listener);
    interface IGetProductNewFinish {
        void onSuccess(ProductNew info);

        void onError(String error);
    }

}
