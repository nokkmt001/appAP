package com.tiha.anphatsu.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ChangeLogInFo {
    private String latestVersion;
    private String latestVersionCode;
    private String url;

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getLatestVersionCode() {
        return latestVersionCode;
    }

    public void setLatestVersionCode(String latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ChangeLogInFo getChangeLogInFo(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Type type = new TypeToken<ChangeLogInFo>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }


}
