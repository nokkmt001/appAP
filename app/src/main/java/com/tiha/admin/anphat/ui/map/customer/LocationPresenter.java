package com.tiha.admin.anphat.ui.map.customer;

import com.tiha.admin.anphat.data.entities.condition.LocationCondition;
import com.tiha.admin.anphat.data.entities.location.InsertLocationInfo;
import com.tiha.admin.anphat.data.network.location.ILocationModel;
import com.tiha.admin.anphat.data.network.location.LocationModel;

import java.util.List;

public class LocationPresenter implements LocationContract.Presenter {
    LocationModel model;
    LocationContract.View view;

    public LocationPresenter(LocationContract.View view) {
        this.view = view;
        this.model = new LocationModel();
    }

    @Override
    public void GetListLocation(LocationCondition condition) {
        model.GetListUserLocation(condition, new ILocationModel.IGetListUserLocationFinish() {
            @Override
            public void onSuccess(List<InsertLocationInfo> list) {
                view.onGetListLocationSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListLocationError(error);
            }
        });
    }
}
