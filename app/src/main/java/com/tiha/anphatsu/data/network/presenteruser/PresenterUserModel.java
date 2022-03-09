package com.tiha.anphatsu.data.network.presenteruser;

import com.android.volley.VolleyError;
import com.tiha.anphatsu.data.entities.IntroducePerInfo;
import com.tiha.anphatsu.data.entities.ResponseInfo;
import com.tiha.anphatsu.data.entities.presenteruser.InsertPresenterInfo;
import com.tiha.anphatsu.data.network.api.APIService;
import com.tiha.anphatsu.data.network.api.VolleyCallback;
import com.tiha.anphatsu.utils.AppConstants;
import com.tiha.anphatsu.utils.AppUtils;
import com.tiha.anphatsu.utils.PublicVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class PresenterUserModel implements IPresenterUserModel {
    APIService service;

    @Override
    public void InsertPresenter(String ID, final IInsertPresenterFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_InsertPresenter, PublicVariables.UserInfo.getNguoiDungMobileID(), ID);
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
                            listener.onSuccess(new InsertPresenterInfo().getPresenterInfo(jsonList));
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
    public void GetListIntroducePer(final IGetListIntroducePerFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetListPresenter, PublicVariables.UserInfo.getNguoiDungMobileID().toString());
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
                            listener.onSuccess(new IntroducePerInfo().getListIntroducePerInfo(jsonList));
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
