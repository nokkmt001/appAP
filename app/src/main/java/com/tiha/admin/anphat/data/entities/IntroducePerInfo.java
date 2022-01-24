package com.tiha.admin.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class IntroducePerInfo {
    public String SupplierID;
    public Integer NguoiDungMobileID;
    public String HoTen;
    public String NgayMuaGanNhat;
    public String ProductID;
    public String ProductName;

    public ArrayList<IntroducePerInfo> getListIntroducePerInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<IntroducePerInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

}
