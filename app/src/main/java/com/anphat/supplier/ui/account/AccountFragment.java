package com.anphat.supplier.ui.account;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.map.mapcty.MapCustomerActivity;
import com.anphat.supplier.utils.CommonUtils;
import com.anphat.supplier.utils.PublicVariables;

public class AccountFragment extends BaseFragment {
    String[] permissionsRequired = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    TextView textName, textSdt;
    RelativeLayout layoutDetail;
    private static final int REQUESTUPDATE = 1;

    private Boolean isClick = false;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView(View view) {
        TextView textMapCty = view.findViewById(R.id.textMapCty);
        textMapCty.setOnClickListener(v -> {
            isClick = true;
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkSelfPermission(permissionsRequired);
                return;
            }
            showLocation();

        });

        textSdt = bind(view, R.id.textSdt);
        textName = bind(view, R.id.textName);
        layoutDetail = bind(view,R.id.layoutDetail);
        layoutDetail.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditAccountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,REQUESTUPDATE);
        });
    }

    private void showLocation(){
        if (!CommonUtils.checkLocation(getContext())) {
            buildAlertMessageNoGps();
        } else {
            Intent intent = new Intent(getContext(), MapCustomerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void initData() {
        NewCustomer user = PublicVariables.UserInfo;
        if (user != null) {
            textName.setText(user.getHoTen());
            textSdt.setText(user.getSoDienThoai());
        }
    }

    @Override
    public void onClick(View view) {
    }

    private void buildAlertMessageNoGps() {
        alertDialog("VỊ TRÍ", "Để tiếp tục, vui lòng bật chức năng xác định vị trí.",
                "OK", null, (dialog, which) ->
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isClick){
            showLocation();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTUPDATE) {
                initData();
            }
        }
    }
}
