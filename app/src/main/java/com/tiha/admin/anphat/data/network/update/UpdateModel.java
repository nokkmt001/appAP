package com.tiha.admin.anphat.data.network.update;

import com.android.volley.VolleyError;
import com.tiha.admin.anphat.data.entities.ChangeLogInFo;
import com.tiha.admin.anphat.data.network.api.APIService;
import com.tiha.admin.anphat.data.network.api.VolleyCallback;
import com.tiha.admin.anphat.utils.AppConstants;
import com.tiha.admin.anphat.utils.AppUtils;

import java.text.MessageFormat;

public class UpdateModel implements IUpdateModel {
    APIService service;

    @Override
    public void GetUrlUpdate(final IGetUrlUpdate listener) {
        AppConstants.URL_SERVER = "";
        String URL = MessageFormat.format(AppConstants.URL_UPDATE_CHANGELOG, "");
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new ChangeLogInFo().getChangeLogInFo(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
