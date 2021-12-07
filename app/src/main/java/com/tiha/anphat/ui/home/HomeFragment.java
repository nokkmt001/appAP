package com.tiha.anphat.ui.home;

import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.DistanceCalculator;
import com.tiha.anphat.data.entities.kho.KhoInfo;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.home.branch.BranchContract;
import com.tiha.anphat.ui.home.branch.BranchPresenter;
import com.tiha.anphat.ui.map.GPSTracker;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends BaseFragment implements BranchContract.View {
    BranchPresenter presenter;
    List<KhoInfo> listDataBranch = new ArrayList<>();
    TextView textView;
    List<DistanceCalculator> listCalculator = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
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
        if (PublicVariables.listKho.size()==0){
            presenter.GetListBranch();
        } else {
            setLocation();
        }
    }

    private void setLocation() {
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

        if (PublicVariables.listKho.size() == 0) return;
        for (KhoInfo item : PublicVariables.listKho) {
            DistanceCalculator gg = new DistanceCalculator();
            LatLng latLng = AppUtils.getLocationFromAddress(item.getDiachi(), getContext());
            Location endPoint = new Location("locationB");
            endPoint.setLatitude(latLng.latitude);
            endPoint.setLongitude(latLng.longitude);
            double distance = startPoint.distanceTo(endPoint);
            gg.MSK = item.getMSK();
            gg.diachi = item.getDiachi();
            gg.Tenkho = item.getTenkho();
            gg.calculator = distance;
            listCalculator.add(gg);
        }
        if (listCalculator.size()==0) return;
        Collections.sort(listCalculator, (o1, o2) -> o1.calculator.compareTo(o2.calculator));
        double gg = listCalculator.get(0).calculator;
        for (KhoInfo item : listDataBranch){
            if (item.getMSK().equals(listCalculator.get(0).MSK)){
                PublicVariables.khoInfoNear =item;
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListBranchSuccess(List<KhoInfo> list) {
        PublicVariables.listKho = list;

    }

    @Override
    public void onGetListBranchError(String error) {
        showMessage(error);
    }
}
