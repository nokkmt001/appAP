package com.tiha.anphat.data.entities;

public class EmployeeLocationCondition {
    private Integer MaxResults;
    private String UserName;
    private String Phone;
    private String OS;
    private String SoCT;
    private String LoaiPhieu;
    private String StartTime;
    private String EndTime;

    public Integer getMaxResults() {
        return MaxResults;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getOS() {
        return OS;
    }

    public String getSoCT() {
        return SoCT;
    }

    public String getLoaiPhieu() {
        return LoaiPhieu;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    // Setter Methods

    public void setMaxResults(Integer MaxResults) {
        this.MaxResults = MaxResults;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public void setSoCT(String SoCT) {
        this.SoCT = SoCT;
    }

    public void setLoaiPhieu(String LoaiPhieu) {
        this.LoaiPhieu = LoaiPhieu;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

}
