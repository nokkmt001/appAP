package com.tiha.anphatsu.ui.map;


import com.tiha.anphatsu.data.entities.RouteInfo;

import java.util.List;

public interface MapContract {
    interface View {
        void onDirectionFinderStart();

        void onDowloadDataMapSuccess(List<RouteInfo> listRoute, String link);

        void onDowloadDataMapError(String error);
    }

    interface Presenter {
        void DowloadDataMap(String origin, String destination);
    }
}
