package com.tiha.anphat.data.network.presenteruser;

import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.presenteruser.InsertPresenterInfo;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

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
}
