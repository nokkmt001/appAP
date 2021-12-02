package com.tiha.anphat.ui.map.customer;

import com.tiha.anphat.data.entities.condition.LocationCondition;
import com.tiha.anphat.data.entities.location.InsertLocationInfo;
import com.tiha.anphat.data.network.location.ILocation;
import com.tiha.anphat.data.network.location.Location;

import java.util.List;

public class LocationPresenter implements LocationContract.Presenter {
    Location model;
    LocationContract.View view;

    public LocationPresenter(LocationContract.View view) {
        this.view = view;
        this.model = new Location();
    }

    @Override
    public void GetListLocation(LocationCondition condition) {
        model.GetListUserLocation(condition, new ILocation.IGetListUserLocationFinish() {
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
