package com.anphat.supplier.ui.account;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.databinding.ActivityEditAccountBinding;
import com.anphat.supplier.ui.address.PlaceActivity;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.viewmodel.LoginViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class EditAccountActivity extends BaseMVVMActivity<ActivityEditAccountBinding, LoginViewModel> implements OnMapReadyCallback {
    NewCustomer info;
    private GoogleMap mMap;
    private static int REQUESTMAP = 1;

    @Override
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivityEditAccountBinding getViewBinding() {
        return ActivityEditAccountBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        info = PublicVariables.UserInfo;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());
        binding.layoutHeader.textTitle.setText("Địa chỉ");
        binding.textName.setText(info.getHoTen());
        binding.textNumberPhone.setText(info.getSoDienThoai());
        binding.textAddress.setText(info.getDiaChi());

        binding.layoutMap.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUESTMAP);
        });

        binding.textAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUESTMAP);
        });
        binding.layoutHeader.textOK.setOnClickListener(v -> {
            validate();
        });

    }

    private void validate() {
        if (TextUtils.isEmpty(binding.textName.getText())) {
            showMessage("Bạn chưa nhập họ tên!");
            return;
        }
        if (TextUtils.isEmpty(binding.textNumberPhone.getText())) {
            showMessage("Bạn chưa nhập số điện thoại!");
            return;
        }
        if (TextUtils.isEmpty(binding.textAddress.getText())) {
            showMessage("Bạn chưa nhập địa chỉ!");
            return;
        }

        info.setDiaChi(binding.textAddress.getText().toString());
        info.setHoTen(Objects.requireNonNull(binding.textName.getText()).toString());
        info.setSoDienThoai(Objects.requireNonNull(binding.textNumberPhone.getText()).toString());
        viewModel.UpdateCustomer(info);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemUpdate.observe(this, info -> {
            if (info != null) {
                AppPreference.saveUser(info);
                showToast("Cập nhật thông tin thành công!");
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void checkLocation(String address) {
        LatLng locationMain = getLocationFromAddress(address);
        MarkerOptions markerOptions = new MarkerOptions();
        if (locationMain == null) return;
        markerOptions.position(locationMain);
        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                locationMain, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        String address = info.getDiaChi();
        LatLng locationMain = getLocationFromAddress(address);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        MarkerOptions markerOptions = new MarkerOptions();
        if (locationMain == null) return;
        markerOptions.position(locationMain);

        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                locationMain, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
        Log.d("status", "success");

    }

    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = null;
            if (address.size() > 0) {
                location = address.get(0);
            }
            if (location == null) return null;
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTMAP) {
                if (data == null) return;
                String gg = data.getExtras().getString("TITLE");
                binding.textAddress.setText(gg);
                checkLocation(gg);
            }
        }

    }
}