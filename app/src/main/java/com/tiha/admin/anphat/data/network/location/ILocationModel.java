package com.tiha.admin.anphat.data.network.location;

import com.tiha.admin.anphat.data.entities.condition.LocationCondition;
import com.tiha.admin.anphat.data.entities.location.InsertLocationInfo;

import java.util.List;

public interface ILocationModel {
    void InsertUserLocation(InsertLocationInfo info,IInsertUserLocation listener);
    interface IInsertUserLocation{
        void onSuccess(InsertLocationInfo info);

        void onError(String error);
    }

    void GetListUserLocation(LocationCondition condition,IGetListUserLocationFinish listener);
    interface IGetListUserLocationFinish{
        void onSuccess(List<InsertLocationInfo> list);
        void onError(String error);
    }
}
