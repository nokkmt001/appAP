package com.tiha.anphat.data.network.evaluate;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tiha.anphat.data.entities.ReasonEvaluate;
import com.tiha.anphat.data.entities.condition.EvaluateCondition;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class EvaluateModel implements IEvaluateModel{
    APIService service;
    @Override
    public void InsertEvaluate(EvaluateCondition condition, final IInsertEvaluateFinish listener) {
        String URL = AppConstants.URL_INSERT_EVALUATE;
        Map<String, String> params = new HashMap<>();
        params.put("EmployeeID", condition.getEmployeeID());
        params.put("SoSao", condition.getSoSao().toString());
        params.put("BinhLuan", condition.getBinhLuan());
        params.put("HinhAnh", condition.getHinhAnh()); //string bitmap
        params.put("NguoiDungMobileID", PublicVariables.UserInfo.getNguoiDungMobileID().toString());
        params.put("ListLyDoDanhGiaSaoo", new Gson().toJson(condition.getListLyDoDanhGiaSaoo()));
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(new EvaluateCondition().getEvaluate(response));
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
    public void GetListReasonEvaluate(String ID, final IGetListEvaluate listener) {
        String URL = MessageFormat.format(AppConstants.URL_GET_LIST_EVALUATE, ID);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new ReasonEvaluate().getListReasonEvaluate(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
