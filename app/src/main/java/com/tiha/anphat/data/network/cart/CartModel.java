package com.tiha.anphat.data.network.cart;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class CartModel implements ICartModel {
    APIService service;

    @Override
    public void InsertCart(CartCondition condition, final IInsertCartFinishListener listener) {
        String URL = AppConstants.URL_INSERT_CART;
        Map<String, String> params = new HashMap<>();
        params.put("NguoiDungMobileID", condition.getNguoiDungMobileID().toString());
        params.put("ProductID", condition.getProductID());
        params.put("SoLuong", condition.getSoLuong().toString());
        params.put("GhiChu", condition.getGhiChu());
        params.put("CreateDate", condition.getCreateDate());
        params.put("ModifiedDate", condition.getModifiedDate());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new CartCondition().getCart(response));

            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void DeleteCart(Integer ID, final IDeleteCartFinishListener listener) {
        String URL = MessageFormat.format(AppConstants.URL_DELETE_CART,ID);
        service = new APIService(URL);
        service.Delete(Request.Method.DELETE, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess();
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void UpdateCart(CartCondition condition, final IUpdateCartFinishListener listener) {
        String URL = AppConstants.URL_UPDATE_CART;
        Map<String, String> params = new HashMap<>();
        params.put("ID", condition.getID().toString());
        params.put("NguoiDungMobileID", condition.getNguoiDungMobileID().toString());
        params.put("ProductID", condition.getProductID());
        params.put("SoLuong", condition.getSoLuong().toString());
        params.put("GhiChu", condition.getGhiChu());
        params.put("CreateDate", condition.getCreateDate());
        params.put("ModifiedDate", condition.getModifiedDate());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new CartCondition().getCart(response));

            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetListAllCart(Integer UserID, final IGetListAllCartFinishListener listener) {
        String URL = MessageFormat.format(AppConstants.URL_GET_LIST_CART, UserID.toString());
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new CartInfo().getListCart(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));

            }
        });
    }
}
