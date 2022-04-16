package com.anphat.supplier.data.network.fcm;

import com.android.volley.VolleyError;
import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.data.entities.ResponseInfo;
import com.anphat.supplier.data.network.api.APIService;
import com.anphat.supplier.data.network.api.VolleyCallback;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class FCM implements IFCM{
    APIService service;

    @Override
    public void InsertFCMMobile(FCMMobileInfo fcmMobileInfo, IFinishedListener listener) {
        String URL = AppConstants.URL_InsertFCMMobile;
        service = new APIService(URL);
        Map<String, String> params = new HashMap<>();
        params.put("HeDieuHanh", fcmMobileInfo.getHeDieuHanh());
        params.put("Token", fcmMobileInfo.getToken());
        params.put("SoDienThoai", fcmMobileInfo.getSoDienThoai());
        params.put("NguoiDung", fcmMobileInfo.getNguoiDung());
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onInsertToKenSuccess(new FCMMobileInfo().getFCMMobile(jsonList));
                        } catch (JSONException e) {
                            listener.onInsertToKenError(e.getMessage());
                        }
                    } else {
                        listener.onInsertToKenError(responseInfo.getMessage());
                    }

                } else {
                    listener.onInsertToKenError(AppConstants.Error_Unknown);
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onInsertToKenError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetFCMFromToken(String token, IFinishedListener listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetFCMFromTokenDevice, token);
        service = new APIService(URL);
        Map<String, String> params = new HashMap<>();
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onInsertToKenSuccess(new FCMMobileInfo().getFCMMobile(jsonList));
                        } catch (JSONException e) {
                            listener.onInsertToKenError(e.getMessage());
                        }
                    } else {
                        listener.onInsertToKenError(responseInfo.getMessage());
                    }

                } else {
                    listener.onInsertToKenError(AppConstants.Error_Unknown);
                }

            }

            @Override
            public void onError(VolleyError error) {
                listener.onInsertToKenError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }
}
