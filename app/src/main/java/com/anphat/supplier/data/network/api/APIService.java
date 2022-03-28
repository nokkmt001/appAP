package com.anphat.supplier.data.network.api;

import android.util.Base64;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppController;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class APIService {
    String URL;
    String tag = "VolleyError";
    String userName = "gasanphat";
    String passWord = "P@ssw0rd@AnPhat.123@#$";
    int MY_SOCKET_TIMEOUT_MS = 100000;

    public APIService(String URL) {
        this.URL = AppConstants.URL_SERVER + URL;
    }

    public void DownloadJson(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, URL, callback::onSuccess, callback::onError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = userName + ":" + passWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void DownloadJsonPut(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.PUT, URL, callback::onSuccess, callback::onError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = userName + ":" + passWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void DownloadJsonPOST(final VolleyCallback callback, final Map<String, String> params) {

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, URL, callback::onSuccess, callback::onError) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @NotNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = userName + ":" + passWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }

//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void Insert(final VolleyCallback callback, final Map<String, String> params) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, URL, callback::onSuccess, callback::onError) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = userName + ":" + passWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void Update(int mothod, final VolleyCallback callback, final Map<String, String> params) {
        StringRequest stringRequest = new StringRequest
                (mothod, URL, callback::onSuccess, callback::onError) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = userName + ":" + passWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void Delete(int method, final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest
                (method, URL, callback::onSuccess, callback::onError) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String credentials = userName + ":" + passWord;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
