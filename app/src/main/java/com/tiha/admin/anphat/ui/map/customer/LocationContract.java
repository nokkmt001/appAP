package com.tiha.admin.anphat.ui.map.customer;

import com.tiha.admin.anphat.data.entities.condition.LocationCondition;
import com.tiha.admin.anphat.data.entities.location.InsertLocationInfo;

import java.util.List;

public interface LocationContract {
    interface View{
        void onGetListLocationSuccess(List<InsertLocationInfo> list);

        void onGetListLocationError(String error);
    }
    interface Presenter{
        void GetListLocation(LocationCondition condition);
    }
}
