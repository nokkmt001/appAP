package com.tiha.anphatsu.data.network.location;

import com.tiha.anphatsu.data.entities.location.InsertLocationInfo;

public interface ILocation {
    void InsertUserLocation(InsertLocationInfo info,IInsertUserLocation listener);
    interface IInsertUserLocation{
        void onSuccess(InsertLocationInfo info);

        void onError(String error);
    }
}
