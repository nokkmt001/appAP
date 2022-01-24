package com.tiha.admin.anphat.data.network.presenteruser;

import com.android.volley.VolleyError;
import com.tiha.admin.anphat.data.entities.IntroducePerInfo;
import com.tiha.admin.anphat.data.entities.presenteruser.InsertPresenterInfo;
import com.tiha.admin.anphat.utils.AppConstants;
import com.tiha.admin.anphat.utils.AppUtils;
import com.tiha.admin.anphat.utils.PublicVariables;
import com.tiha.admin.anphat.data.network.api.APIService;
import com.tiha.admin.anphat.data.network.api.VolleyCallback;

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
        String URL = MessageFormat.format(AppConstants.URL_GetListPresenter, PublicVariables.UserInfo.getNguoiDungMobileID());
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new IntroducePerInfo().getListIntroducePerInfo(response));
            }
            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
