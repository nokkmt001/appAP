package com.tiha.anphat.ui.account;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.ViewBinding;

import com.tiha.anphat.R;
import com.tiha.anphat.databinding.FragmentAccountBinding;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.base.BaseTestAdapter;
import com.tiha.anphat.ui.base.BaseTestFragment;
import com.tiha.anphat.ui.map.mapcty.MapCustomerActivity;
import com.tiha.anphat.utils.CommonUtils;

public class AccountFragment extends BaseFragment {
    String[] permissionsRequired = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

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
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Để tiếp tục, vui lòng bật chức năng xác định vị trí.")
                .setCancelable(false)
                .setTitle("VỊ TRÍ")
                .setPositiveButton("CÀI ĐẶT", (dialog, id) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("HỦY", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
