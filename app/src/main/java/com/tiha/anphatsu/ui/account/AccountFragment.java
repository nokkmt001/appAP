package com.tiha.anphatsu.ui.account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.ui.base.BaseFragment;
import com.tiha.anphatsu.ui.map.mapcty.MapCustomerActivity;
import com.tiha.anphatsu.utils.CommonUtils;
import com.tiha.anphatsu.utils.PublicVariables;

public class AccountFragment extends BaseFragment {
    String[] permissionsRequired = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    TextView textName, textSdt;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView(View view) {
        TextView textMapCty = view.findViewById(R.id.textMapCty);
        textMapCty.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkSelfPermission(permissionsRequired);
                return;
            }
            if (!CommonUtils.checkLocation(getContext())) {
                buildAlertMessageNoGps();
            } else {
                Intent intent = new Intent(getContext(), MapCustomerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textSdt = bind(view, R.id.textSdt);
        textName = bind(view, R.id.textName);
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
}
