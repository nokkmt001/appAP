package com.anphat.supplier.data.network.booking;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.ResponseInfo;
import com.anphat.supplier.data.entities.condition.CancelOrderCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;
import com.anphat.supplier.data.network.api.APIService;
import com.anphat.supplier.data.network.api.VolleyCallback;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
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
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").getJSONObject("PhieuXuat").toString();
                            listener.onSuccess(new OrderInfo().getOrderInfo(jsonList), new CallInfo());
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
    public void GetBooking(String SoCT, final IGetBookingFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetDonHang, SoCT);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onSuccess(new BookingInfo().getBookingInfo(jsonList));
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

    @Override
    public void checkBooking(IGetBookingFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_CheckBooking, PublicVariables.UserInfo.getNguoiDungMobileID());
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            BookingInfo item = new BookingInfo().getBookingInfo(jsonList);
                            if (!item.MaTrangThai.equals("HUY")) {
                                listener.onSuccess(item);
                            } else {
                                listener.onError("cancel");
                            }
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

    @Override
    public void GetHistoryBooking(final IGetHistoryBookingSuccess listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetListHISTORYBOOKING, PublicVariables.UserInfo.getNguoiDungMobileID().toString());
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
                            listener.onSuccess(new HistoryBooking().getListHistoryBooking(jsonList));
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

    @Override
    public void CancelOrder(CancelOrderCondition condition, final ICancelOrderFinish listener) {
        String URL = AppConstants.URL_HuyDonHang;
        Map<String, String> params = new HashMap<>();
        params.put("SoDonHang", condition.getSoDonHang());
        params.put("NguoiDungMobileID", PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        params.put("LyDoHuy", condition.getLyDoHuy());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            listener.onSuccess(true);
                        } catch (Exception e) {
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
    public void CheckDaDat(ICheckDaDatFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_CheckDaDat, PublicVariables.UserInfo.getNguoiDungMobileID());
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

    @Override
    public void SendBooking(ISendBookingFinish listener) {
        String URL = AppConstants.URL_SendRequestBooking;
        Map<String, String> params = new HashMap<>();
        params.put("ListGioHang", "");
        params.put("NguoiDungMobileID", PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        params.put("HeDieuHanh", "APPANDROID");
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").getJSONObject("CuocGoi").toString();
                            listener.onSuccess(new CallInfo().getCallInfo(jsonList));
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
}
