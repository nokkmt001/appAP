package com.tiha.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserLoginInfo {
    public String UserName;
    public String GroupName;
    public String Password;
    public String MaSoBangGia;
    public String EmployeeID;
    public String EmployeeName;
    public String DTNoio;
    public String ChiNhanh;
    public String Picture;
    public String Khomacdinh;
    public Integer BatSoDienThoai;
    public String SecurityNo;
    public String BirthDate;
    public String Noiohiennay;

    public UserLoginInfo getUserLogin(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<UserLoginInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
