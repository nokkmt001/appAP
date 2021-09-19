package com.tiha.anphat.data.network.cart;

import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

public class CartModel implements ICartModel{
    APIService service;
    @Override
    public void InsertCart(CartCondition condition, final IInsertCartFinishListener listener) {
        String URL = AppConstants.URL_GET_LIST_All_PRODUCT;
        Map<String, String> params = new HashMap<>();
        params.put("UserName", "TIHA");
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
//                listener.onSuccess(new );

            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void DeleteCart(Integer ID, IDeleteCartFinishListener listener) {

    }

    @Override
    public void UpdateCart(CartCondition condition, IUpdateCartFinishListener listener) {

    }

    @Override
    public void GetListAllCart(Integer UserID, IGetListAllCartFinishListener listener) {

    }
}
