package com.tiha.admin.anphat.ui.map;


import com.tiha.admin.anphat.data.entities.RouteInfo;

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
