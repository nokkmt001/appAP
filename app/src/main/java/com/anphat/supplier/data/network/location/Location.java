package com.anphat.supplier.data.network.location;

import com.android.volley.VolleyError;
import com.anphat.supplier.data.entities.location.InsertLocationInfo;
import com.anphat.supplier.data.network.api.APIService;
import com.anphat.supplier.data.network.api.VolleyCallback;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

public class Location implements ILocation {
    APIService service;
    @Override
    public void InsertUserLocation(InsertLocationInfo info,final IInsertUserLocation listener) {
        String URL = AppConstants.URL_InsertUserLocation;
        Map<String, String> params = new HashMap<>();
        params.put("UserName", info.getUserName());
        params.put("Phone",info.getPhone() );
        params.put("Latitude", info.getLatitude());
        params.put("Longitude", info.getLongitude());
        params.put("OS", info.getOS());
        params.put("CreateDate", info.getCreateDate());
        params.put("EventName", info.getEventName());
        params.put("SoCT", info.getSoCT());
        params.put("LoaiPhieu", info.getLoaiPhieu());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new InsertLocationInfo().getInsertLocationInfo(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }
}
