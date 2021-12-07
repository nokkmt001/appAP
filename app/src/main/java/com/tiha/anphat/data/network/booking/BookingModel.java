package com.tiha.anphat.data.network.booking;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.data.entities.condition.CancelOrderCondition;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.entities.order.CallInfo;
import com.tiha.anphat.data.entities.order.OrderInfo;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingModel implements IBookingModel {
    APIService service;

    @Override
    public void InsertOrder(List<CartInfo> list, final IInsertOrderFinish listener) {
        String URL = AppConstants.URL_InsertDonHang;
        Map<String, String> params = new HashMap<>();
        params.put("ListGioHang", new Gson().toJson(list));
        params.put("NguoiDungMobileID", PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        params.put("HeDieuHanh", "APPANDROID");
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String jsonOrder = jsonObject.getJSONObject("PhieuXuat").toString();
                    String jsonCall = jsonObject.getJSONObject("Data").toString();
                    listener.onSuccess(new OrderInfo().getOrderInfo(jsonOrder), new CallInfo().getCallInfo(jsonCall));
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
    public void GetBooking(String SoCT, final IGetBookingFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetDonHang, SoCT);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new BookingInfo().getBookingInfo(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void GetHistoryBooking(final IGetHistoryBookingSuccess listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetListHISTORYBOOKING, PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new HistoryBooking().getListHistoryBooking(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void CancelOrder(CancelOrderCondition condition,final ICancelOrderFinish listener) {
        String URL = AppConstants.URL_HuyDonHang;
        Map<String, String> params = new HashMap<>();
        params.put("SoDonHang", condition.getSoDonHang());
        params.put("NguoiDungMobileID", PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        params.put("LyDoHuy", condition.getLyDoHuy());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(Boolean.valueOf(response));
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
}
