package com.anphat.supplier.data.network.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ApiResponseSbke<T> {
    public Integer Status;
    public String Message;
    public List<T> listData;
    public List<T> list;
    public T Data;

    public ApiResponseSbke getResponse(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ApiResponseSbke>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
