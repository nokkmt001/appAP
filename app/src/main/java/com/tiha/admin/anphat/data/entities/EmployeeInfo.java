package com.tiha.admin.anphat.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmployeeInfo {
    public String EmployeeID;
    public String EmployeeName;
    public float Sex;
    public boolean IsForeigner;
    public String Tel;
    public boolean IsSaleRep;
    public String DTNoio;
    public String DepartmentID;
    public String PositionName = null;
    public String NickName;
    public String ChiNhanh;

    public ArrayList<EmployeeInfo> getListEmployeeInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<EmployeeInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
    
}
