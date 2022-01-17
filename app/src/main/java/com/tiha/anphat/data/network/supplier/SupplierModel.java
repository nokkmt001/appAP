package com.tiha.anphat.data.network.supplier;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tiha.anphat.data.entities.SupplierInfo;
import com.tiha.anphat.data.entities.condition.SupplierCondition;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class SupplierModel implements ISupplierModel {
    APIService service;

    @Override
    public void GetListSupplier(SupplierCondition condition, IGetListSupplierFinish listener) {
        String URL = AppConstants.URL_GetListSupplier;
        Map<String, String> params = new HashMap<>();
        params.put("Begin", condition.getBegin().toString());
        params.put("End", condition.getEnd().toString());
        if (condition.getTextSearch() != null && condition.getTextSearch().length() > 0) {
            params.put("TextSearch", condition.getTextSearch());
        }
        params.put("UserName", PublicVariables.userLoginInfo.UserName);
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String jsonOrder = jsonObject.getJSONArray("List").toString();
                    long jsonCall = jsonObject.getLong("Total");
                    listener.onSuccess(new SupplierInfo().getListSupplierInfo(jsonOrder), jsonCall);
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

    @Override
    public void GetSupplier(String supplierID, ISupplierFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetSupplier, supplierID);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(new SupplierInfo().getSupplierInfo(response));
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }
            }
            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void InsertSupplier(SupplierInfo info, ISupplierFinish listener) {
        String URL = AppConstants.URL_InsertSupplier;
        Map<String, String> params = new HashMap<>();
        params.put("itemSupplier", new Gson().toJson(info));
        params.put("UserName", PublicVariables.userLoginInfo.UserName);
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(new SupplierInfo().getSupplierInfo(response));
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void UpdateSupplier(SupplierInfo info, ISupplierFinish listener) {
        String URL = AppConstants.URL_UpdateSupplier;
        Map<String, String> params = new HashMap<>();
        params.put("itemSupplier", new Gson().toJson(info));
        params.put("UserName", PublicVariables.userLoginInfo.UserName);
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(new SupplierInfo().getSupplierInfo(response));
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void DeleteSupplier(String MKH, IDeleteSupplierFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_DeleteSupplier, MKH, PublicVariables.userLoginInfo.UserName);
        service = new APIService(URL);
        service.Delete(Request.Method.DELETE, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess();
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
