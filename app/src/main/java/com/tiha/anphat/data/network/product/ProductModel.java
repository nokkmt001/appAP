package com.tiha.anphat.data.network.product;

import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductModel implements IProductModel {
    APIService service;

    @Override
    public void GetListAllProduct(final IGetListAllProductFinishListener listener) {
        String URL = AppConstants.URL_GET_LIST_All_PRODUCT;
        Map<String, String> params = new HashMap<>();
        params.put("UserName", "TIHA");
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new ProductInfo().getListAllProduct(response));

            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetListProduct(ProductCondition condition, final IGetListProductFinishListener listener) {
        String URL = AppConstants.URL_GET_LIST_PRODUCT;
        Map<String, String> params = new HashMap<>();
        params.put("Begin", condition.getBegin().toString());
        params.put("End", condition.getEnd().toString());
        params.put("NhomLoaiHang", condition.getNhomLoaiHang());
        params.put("UserName", condition.getUserName());
        params.put("TextSearch", condition.getTextSearch());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String jsonList = jsonObject.getJSONArray("List").toString();
                    Integer counter = Integer.parseInt(jsonObject.get("Total").toString());
                    listener.onSuccess(new ProductInfo().getListAllProduct(jsonList),counter);
                } catch (JSONException e) {
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }
}
