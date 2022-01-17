package com.tiha.anphat.data.network.location;

import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.condition.LocationCondition;
import com.tiha.anphat.data.entities.location.InsertLocationInfo;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;

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
                try {
                    listener.onSuccess(new InsertLocationInfo().getInsertLocationInfo(response));

                }catch (Exception e){
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
    public void GetListUserLocation(LocationCondition condition,final IGetListUserLocationFinish listener) {
        String URL = AppConstants.URL_InsertUserLocation;
        Map<String, String> params = new HashMap<>();
        params.put("MaxResults",condition.MaxResults.toString());
        params.put("UserName", condition.UserName);
        params.put("Phone",condition.Phone );
        params.put("OS", condition.OS);
        params.put("SoCT", condition.SoCT);
        params.put("LoaiPhieu", condition.LoaiPhieu);
        params.put("StartTime", condition.StartTime);
        params.put("EndTime", condition.EndTime);
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(new InsertLocationInfo().getListInsertLocationInfo(response));
                }catch (Exception e){
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
