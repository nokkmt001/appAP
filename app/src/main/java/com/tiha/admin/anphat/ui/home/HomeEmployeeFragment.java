package com.tiha.admin.anphat.ui.home;

import android.location.Location;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.DistanceCalculator;
import com.tiha.admin.anphat.data.entities.kho.KhoInfo;
import com.tiha.admin.anphat.ui.base.BaseFragment;
import com.tiha.admin.anphat.ui.home.branch.BranchContract;
import com.tiha.admin.anphat.ui.home.branch.BranchPresenter;
import com.tiha.admin.anphat.ui.map.GPSTracker;
import com.tiha.admin.anphat.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeEmployeeFragment extends BaseFragment implements BranchContract.View {
    BranchPresenter presenter;
    List<KhoInfo> listDataBranch = new ArrayList<>();
    TextView textView;
List<DistanceCalculator> listCalculator = new ArrayList<>();
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home_employee;
    }

    @Override
    protected void initView(View view) {
        textView = view.findViewById(R.id.textBranch);
        textView.setOnClickListener(v -> {
            if (listDataBranch.size() == 0) return;
            PopupMenu menu = new PopupMenu(getContext(), textView);
            for (KhoInfo item : listDataBranch) {
                menu.getMenu().add(item.getTenkho());
            }
            menu.setOnMenuItemClickListener(item -> {
                textView.setText(item.getTitle());
                return true;
            });
            menu.show();
        });
    }

    @Override
    protected void initData() {
        presenter = new BranchPresenter(this);
//        presenter.GetListBranch();
    }

    private void setLocation(){
        double latitude = 0, longitude = 0;
        GPSTracker tracker = new GPSTracker(getContext());
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
        }

        Location startPoint = new Location("locationA");
        startPoint.setLatitude(latitude);
        startPoint.setLongitude(longitude);

        if (listDataBranch.size() == 0) return;
        for (KhoInfo item : listDataBranch) {
            DistanceCalculator gg = new DistanceCalculator();
            LatLng latLng = AppUtils.getLocationFromAddress(item.getDiachi(), getContext());
            Location endPoint = new Location("locationB");
            endPoint.setLatitude(latLng.latitude);
            endPoint.setLongitude(latLng.longitude);
            double distance=startPoint.distanceTo(endPoint);
            gg.MSK = item.getMSK();
            gg.diachi = item.getDiachi();
            gg.Tenkho = item.getTenkho();
            gg.calculator = distance;
            listCalculator.add(gg);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListBranchSuccess(List<KhoInfo> list) {
        listDataBranch = list;
        setLocation();
    }

    @Override
    public void onGetListBranchError(String error) {

    }
}
