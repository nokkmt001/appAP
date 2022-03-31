package com.anphat.supplier.ui.address;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.databinding.ActivityChangeAddressBinding;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.login.forgetpass.ForgetPassContract;
import com.anphat.supplier.ui.login.forgetpass.ForgetPassPresenter;
import com.anphat.supplier.utils.PublicVariables;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ChangeAddressActivity extends BaseTestActivity<ActivityChangeAddressBinding> implements OnMapReadyCallback, ForgetPassContract.View {
    NewCustomer info;
    private GoogleMap mMap;
    ForgetPassPresenter presenter;
    private static int REQUESTMAP = 1;

    @Override
    public ActivityChangeAddressBinding getViewBinding() {
        return ActivityChangeAddressBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        presenter = new ForgetPassPresenter(this);
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
            info.setDiaChi(binding.textAddress.getText().toString());
            presenter.UpdateCustomer(info);
        });

    }

    @Override
    protected void initData() {

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
//        etOrigin.setText(Address);
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

    @Override
    public void onUpdateCustomerSuccess(NewCustomer info) {
        AppPreference.saveUser(info);
        showToast("Cập nhật địa chỉ thành công!");
        finish();
    }

    @Override
    public void onUpdateCustomerError(String error) {
        showMessage(error);
    }
}
