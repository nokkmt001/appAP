package com.tiha.anphatsu.data.network.cart;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.tiha.anphatsu.data.entities.CartInfo;
import com.tiha.anphatsu.data.entities.ResponseInfo;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.data.network.api.APIService;
import com.tiha.anphatsu.data.network.api.VolleyCallback;
import com.tiha.anphatsu.utils.AppConstants;
import com.tiha.anphatsu.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onSuccess(new CartCondition().getCart(jsonList));
                        } catch (JSONException e) { listener.onError(e.getMessage());
                        }
                    } else { listener.onError(responseInfo.getMessage());
                    }
                } else { listener.onError(AppConstants.Error_Unknown);
                }
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
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            listener.onSuccess();
                        } catch (Exception e) { listener.onError(e.getMessage());
                        }
                    } else { listener.onError(responseInfo.getMessage());
                    }
                } else { listener.onError(AppConstants.Error_Unknown);
                }
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
        params.put("GhiChu", condition.getGhiChu()==null?"":condition.getGhiChu());
        params.put("CreateDate", condition.getCreateDate());
        params.put("ModifiedDate", condition.getModifiedDate());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onSuccess(new CartCondition().getCart(jsonList));
                        } catch (JSONException e) {
                            listener.onError(e.getMessage());
                        }
                    } else {
                        listener.onError(responseInfo.getMessage());
                    }
                }
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
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONArray("Data").toString();
                            listener.onSuccess(new CartInfo().getListCart(jsonList));
                        } catch (JSONException e) {
                            listener.onError(e.getMessage());
                        }
                    } else {
                        listener.onError(responseInfo.getMessage());
                    }
                }
            }
            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
