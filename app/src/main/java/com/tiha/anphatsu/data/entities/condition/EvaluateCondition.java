package com.tiha.anphatsu.data.entities.condition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tiha.anphatsu.data.entities.ReasonEvaluate;

import java.lang.reflect.Type;
import java.util.List;

public class EvaluateCondition {
    private String SoCT;
    private String EmployeeID;
    private Integer NguoiDungMobileID;
    private Integer SoSao;
    private String BinhLuan;
    private String HinhAnh;   // bitmap to string.
    private List<ReasonEvaluate> ListLyDoDanhGiaSaoo;

    public EvaluateCondition getEvaluate(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<EvaluateCondition>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public String getSoCT() {
        return SoCT;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public Integer getNguoiDungMobileID() {
        return NguoiDungMobileID;
    }

    public Integer getSoSao() {
        return SoSao;
    }

    public String getBinhLuan() {
        return BinhLuan;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setSoCT(String SoCT) {
        this.SoCT = SoCT;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public void setNguoiDungMobileID(Integer NguoiDungMobileID) {
        this.NguoiDungMobileID = NguoiDungMobileID;
    }

    public void setSoSao(Integer SoSao) {
        this.SoSao = SoSao;
    }

    public void setBinhLuan(String BinhLuan) {
        this.BinhLuan = BinhLuan;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public List<ReasonEvaluate> getListLyDoDanhGiaSaoo() {
        return ListLyDoDanhGiaSaoo;
    }

    public void setListLyDoDanhGiaSaoo(List<ReasonEvaluate> listLyDoDanhGiaSaoo) {
        ListLyDoDanhGiaSaoo = listLyDoDanhGiaSaoo;
    }
}
