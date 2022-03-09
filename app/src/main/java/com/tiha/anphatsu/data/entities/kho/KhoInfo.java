package com.tiha.anphatsu.data.entities.kho;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class KhoInfo {
    private String MSK;
    private String Tenkho;
    private String diachi;
    private String Tel;
    private String THUKHO;
    private String Ngaygio;
    private Integer Coxemhangton;
    private Integer CkLaino;
    private String ChiNhanhID;
    private String ModifiedDate = null;
    private String ModifiedBy;
    private String UserName;
    private Integer InLenLau;
    private String Unit;
    private String SHTK = null;
    private String KhuVuc;
    private String Mien;
    // Getter Methods

    public String getMSK() {
        return MSK;
    }

    public String getTenkho() {
        return Tenkho;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getTel() {
        return Tel;
    }

    public String getTHUKHO() {
        return THUKHO;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public Integer getCoxemhangton() {
        return Coxemhangton;
    }

    public Integer getCkLaino() {
        return CkLaino;
    }

    public String getChiNhanhID() {
        return ChiNhanhID;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public String getUserName() {
        return UserName;
    }

    public Integer getInLenLau() {
        return InLenLau;
    }

    public String getUnit() {
        return Unit;
    }

    public String getSHTK() {
        return SHTK;
    }

    public String getKhuVuc() {
        return KhuVuc;
    }

    public String getMien() {
        return Mien;
    }

    // Setter Methods

    public void setMSK(String MSK) {
        this.MSK = MSK;
    }

    public void setTenkho(String Tenkho) {
        this.Tenkho = Tenkho;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public void setTHUKHO(String THUKHO) {
        this.THUKHO = THUKHO;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }

    public void setCoxemhangton(Integer Coxemhangton) {
        this.Coxemhangton = Coxemhangton;
    }

    public void setCkLaino(Integer CkLaino) {
        this.CkLaino = CkLaino;
    }

    public void setChiNhanhID(String ChiNhanhID) {
        this.ChiNhanhID = ChiNhanhID;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setInLenLau(Integer InLenLau) {
        this.InLenLau = InLenLau;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public void setSHTK(String SHTK) {
        this.SHTK = SHTK;
    }

    public void setKhuVuc(String KhuVuc) {
        this.KhuVuc = KhuVuc;
    }

    public void setMien(String Mien) {
        this.Mien = Mien;
    }

    public KhoInfo getKhoInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<KhoInfo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public ArrayList<KhoInfo> getListKhoInfo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ArrayList<KhoInfo>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
