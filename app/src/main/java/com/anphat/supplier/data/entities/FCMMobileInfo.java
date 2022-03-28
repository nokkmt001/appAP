package com.anphat.supplier.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class FCMMobileInfo {
    public Integer FCMID;
    public String SoDienThoai;
    public String HeDieuHanh;
    public String Token;
    public String NguoiDung;

    public Integer getFCMID() {
        return FCMID;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public String getHeDieuHanh() {
        return HeDieuHanh;
    }

    public String getToken() {
        return Token;
    }

    public String getNguoiDung() {
        return NguoiDung;
    }

    public Integer getRowID() {
        return RowID;
    }

    public Integer RowID;

    public FCMMobileInfo getFCMMobile(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<FCMMobileInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
