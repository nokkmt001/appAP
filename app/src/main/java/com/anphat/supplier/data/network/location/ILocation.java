package com.anphat.supplier.data.network.location;

import com.anphat.supplier.data.entities.location.InsertLocationInfo;

public interface ILocation {
    void InsertUserLocation(InsertLocationInfo info,IInsertUserLocation listener);
    interface IInsertUserLocation{
        void onSuccess(InsertLocationInfo info);

        void onError(String error);
    }
}
