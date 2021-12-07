package com.tiha.anphat.data.network.presenteruser;

import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.IntroducePerInfo;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.ResponseInfo;
import com.tiha.anphat.data.entities.presenteruser.InsertPresenterInfo;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

public class PresenterUserModel implements IPresenterUserModel {
    APIService service;

    @Override
    public void InsertPresenter(Integer ID, final IInsertPresenterFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_InsertPresenter, PublicVariables.UserInfo.getNguoiDungMobileID(), ID);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new InsertPresenterInfo().getPresenterInfo(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void GetListIntroducePer(final IGetListIntroducePerFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetListPresenter, "13");
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
