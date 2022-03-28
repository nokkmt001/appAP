package com.anphat.supplier.data.network.user;

import com.android.volley.VolleyError;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.ResponseInfo;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.network.api.APIService;
import com.anphat.supplier.data.network.api.VolleyCallback;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class UserModel implements IUserModel {
    APIService service;

    @Override
    public void CheckPhone(String sdt, final ICheckPhoneFinishListener listener) {
        String URL = MessageFormat.format(AppConstants.URL_CHECK_PHONE, sdt);
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
                            listener.onSuccess(new NewCustomer().getNewCustomer(jsonList));
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

    @Override
    public void GetLoginByIDPassWord(String id, String passWord, final IGetLoginByIDPassWord listener) {
        String URL = AppConstants.URL_LOGIN_BY_ID_PASS_WORD;
        service = new APIService(URL);
        Map<String, String> params = new HashMap<>();
        params.put("NguoiDungMobielID", id);
        params.put("Password", passWord);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onSuccess(new NewCustomer().getNewCustomer(jsonList));
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
        },params);
    }

    @Override
    public void InsertNewCustomer(NewCustomer condition, final IInsertNewCustomer listener) {
        String URL = AppConstants.URL_INSERT_NEW_CUSTOMER;
        Map<String, String> params = new HashMap<>();
        params.put("HoTen", condition.getHoTen());
        params.put("SoDienThoai", condition.getSoDienThoai());
        params.put("DiaChi", condition.getDiaChi());
        params.put("NgayGio", condition.getNgayGio());
        params.put("MaPIN", "");
        params.put("ModifiedDate", condition.getNgayGio());
        params.put("Password", condition.getPassword());
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
                            listener.onSuccess(new NewCustomer().getNewCustomer(jsonList));
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
        },params);
    }

    @Override
    public void ReSendPINcode(String id, final IReSendPINcode listener) {
        String URL = MessageFormat.format(AppConstants.URL_RESEND_PIN, id);
        service = new APIService(URL);
        service.DownloadJsonPut(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                ResponseInfo responseInfo = new ResponseInfo().getResponse(response);
                if (responseInfo != null) {
                    if (responseInfo.getStatus() == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonList = jsonObject.getJSONObject("Data").toString();
                            listener.onSuccess(new NewCustomer().getNewCustomer(jsonList));
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

    @Override
    public void GetListKhoByUser(final IGetListKhoByUserFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GetListKhoByUser, "TIHA");
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess(new KhoInfo().getListKhoInfo(response));
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void UpdateCustomer(NewCustomer condition, IUpdateCustomerFinish listener) {
        String URL = AppConstants.URL_INSERT_NEW_CUSTOMER;
        Map<String, String> params = new HashMap<>();
        params.put("HoTen", condition.getHoTen());
        params.put("SoDienThoai", condition.getSoDienThoai());
        params.put("DiaChi", condition.getDiaChi());
        params.put("NgayGio", condition.getNgayGio());
        params.put("MaPIN", condition.getMaPIN().toString());
        params.put("ModifiedDate", condition.getNgayGio());
        params.put("Password", condition.getPassword());
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
                            listener.onSuccess(new NewCustomer().getNewCustomer(jsonList));
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
        },params);
    }
}
