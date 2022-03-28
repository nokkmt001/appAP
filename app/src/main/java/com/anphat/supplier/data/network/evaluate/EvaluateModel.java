package com.anphat.supplier.data.network.evaluate;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.anphat.supplier.data.entities.ReasonEvaluate;
import com.anphat.supplier.data.entities.ResponseInfo;
import com.anphat.supplier.data.entities.condition.EvaluateCondition;
import com.anphat.supplier.data.network.api.APIService;
import com.anphat.supplier.data.network.api.VolleyCallback;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class EvaluateModel implements IEvaluateModel {
    APIService service;

    @Override
    public void InsertEvaluate(EvaluateCondition condition, final IInsertEvaluateFinish listener) {
        String URL = AppConstants.URL_INSERT_EVALUATE;
        Map<String, String> params = new HashMap<>();
        params.put("SoCT", condition.getSoCT());
        params.put("EmployeeID", condition.getEmployeeID());
        params.put("SoSao", condition.getSoSao().toString());
        params.put("BinhLuan", condition.getBinhLuan());
        params.put("HinhAnh", condition.getHinhAnh() == null ? "" : condition.getHinhAnh()); //string bitmap
        params.put("NguoiDungMobileID", PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        params.put("ListLyDoDanhGiaSaoo", new Gson().toJson(condition.getListLyDoDanhGiaSaoo()));
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
                            listener.onSuccess(new EvaluateCondition().getEvaluate(jsonList));
                        } catch (JSONException e) {
                            listener.onError(e.getMessage());
                        }
                    } else {
                        listener.onError(responseInfo.getMessage());
                    }

                } else {
                    listener.onError(AppConstants.Error_Unknown);
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetListReasonEvaluate(String ID, final IGetListEvaluate listener) {
        String URL = MessageFormat.format(AppConstants.URL_GET_LIST_EVALUATE, ID);
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
                            listener.onSuccess(new ReasonEvaluate().getListReasonEvaluate(jsonList));
                        } catch (JSONException e) {
                            listener.onError(e.getMessage());
                        }
                    } else {
                        listener.onError(responseInfo.getMessage());
                    }

                } else {
                    listener.onError(AppConstants.Error_Unknown);
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
