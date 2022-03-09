package com.tiha.anphatsu.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReasonEvaluate {
    private Integer LyDoDanhGiaSaoID;
    private String TenLyDo;
    private Integer SaoBatDau;
    private Integer SaoKetThuc;
    private String CreateDate;

    private Boolean isCheck = false;

    public ArrayList<ReasonEvaluate> getListReasonEvaluate(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<ReasonEvaluate>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public Integer getLyDoDanhGiaSaoID() {
        return LyDoDanhGiaSaoID;
    }

    public String getTenLyDo() {
        return TenLyDo;
    }

    public Integer getSaoBatDau() {
        return SaoBatDau;
    }

    public Integer getSaoKetThuc() {
        return SaoKetThuc;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setLyDoDanhGiaSaoID(Integer LyDoDanhGiaSaoID) {
        this.LyDoDanhGiaSaoID = LyDoDanhGiaSaoID;
    }

    public void setTenLyDo(String TenLyDo) {
        this.TenLyDo = TenLyDo;
    }

    public void setSaoBatDau(Integer SaoBatDau) {
        this.SaoBatDau = SaoBatDau;
    }

    public void setSaoKetThuc(Integer SaoKetThuc) {
        this.SaoKetThuc = SaoKetThuc;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
