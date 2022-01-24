package com.tiha.admin.anphat.data.entities.condition;

public class SupplierCondition {
    private Integer Begin;
    private Integer End;
    private String TextSearch;
    private String UserName;

    public Integer getBegin() {
        return Begin;
    }

    public void setBegin(Integer begin) {
        Begin = begin;
    }

    public Integer getEnd() {
        return End;
    }

    public void setEnd(Integer end) {
        End = end;
    }

    public String getTextSearch() {
        return TextSearch;
    }

    public void setTextSearch(String textSearch) {
        TextSearch = textSearch;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
