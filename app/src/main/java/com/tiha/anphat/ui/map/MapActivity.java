package com.tiha.anphat.ui.map;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.RouteInfo;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.utils.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapContract.View, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    private Button btnFindPath, btnGoogleMap;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    //latitude: Vi do, longitude: Kinh do
    private ImageButton btnClear, btnClear1;
    String endAddress = "", endLatitude = "", endLongitude = "";
    String startAddress = "", startLatitude = "", startLongitude = "";
    String origin = "";
    String destination = "";
    String originFinal = "", destinationFinal = "";
    Toolbar toolbar;
    Dialog progressDialog;
    String linkGoogleMap;
    MapPresenter mapPresenter;

    @Override
    public void initView() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        btnFindPath = findViewById(R.id.btnFindPath);
//        btnGoogleMap = findViewById(R.id.btnGoogleMap);
//        etOrigin = findViewById(R.id.etOrigin);
//        etDestination = findViewById(R.id.etDestination);
//        btnClear = findViewById(R.id.btnClear);
//        btnClear1 = findViewById(R.id.btnClear1);
//        toolbar = findViewById(R.id.toolbar);
//
//        btnFindPath.setOnClickListener(this);
//        btnGoogleMap.setOnClickListener(this);
//        btnClear.setOnClickListener(this);
//        btnClear1.setOnClickListener(this);

//        etDestination.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().isEmpty())
//                    btnClear.setVisibility(View.VISIBLE);
//                else
//                    btnClear.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        etOrigin.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().isEmpty())
//                    btnClear1.setVisibility(View.VISIBLE);
//                else
//                    btnClear1.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

//    @Override
//    public void onConfigToolbar() {
////        setSupportActionBar(toolbar);
////        String tieuDeForm = getResources().getString(R.string.map_tieudeform);
////        CommonUtils.configToolbar(this, getSupportActionBar(), tieuDeForm, 0);
////        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                finish();
////            }
////        });
//    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {
        mapPresenter = new MapPresenter(this);
        Bundle bundle = this.getIntent().getExtras();
        try {
            endAddress = bundle.getString("DiaChi");
            endAddress = (endAddress == null) ? "" : endAddress;
        } catch (Exception e) {
        }
        try {
            endLatitude = bundle.getString("ViDo");
            endLatitude = (endLatitude == null) ? "" : endLatitude;
        } catch (Exception e) {
            endLatitude = "";

        }
        try {
            endLongitude = bundle.getString("KinhDo");
            endLongitude = (endLongitude == null) ? "" : endLongitude;
        } catch (Exception e) {
            endLongitude = "";
        }
//        mapPresenter.GetKhachHang(bundle.getString("SupplierId"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btnFindPath:
//                sendRequest();
//                break;
//            case R.id.btnGoogleMap:
//                if (!CreateAddress()) return;
//                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationFinal + "");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
//                break;
//            case R.id.btnClear:
//                etDestination.setText("");
//                break;
//            case R.id.btnClear1:
//                etOrigin.setText("");
//                break;
//            default:
//                break;
        }

    }
//
//    private void setDiaChiXem(KhachHangInfo khachHangInfo) {
////        Neu kho co toa do thi lay dia chi theo toa do
////        Nguoc lai lay theo dia chi trong kho
////        Neu kho ko co toa do va dia chi thi lay dia chi hien tai
////        region Origin
//        if (khachHangInfo != null) {
//            startAddress = khachHangInfo.getAddress() == null ? "" : "";
//            startLatitude = khachHangInfo.getX() == null ? "" : khachHangInfo.getX();
//            startLongitude = khachHangInfo.getY() == null ? "" : khachHangInfo.getY();
//        }
//        if (!startLatitude.isEmpty() && !startLatitude.equals("0") && !startLongitude.isEmpty() && !startLongitude.equals("0")) {
//            origin = startLatitude + "," + startLongitude;
//            try {
//                startAddress = getAddressByLocation(Double.parseDouble(startLatitude), Double.parseDouble(startLongitude));
//            } catch (NumberFormatException e) {
//            }
//        } else {
//            origin = startAddress;
//        }
////        Nếu kho mặc định không có địa chỉ thì lấy vị trí hiện tại làm điểm bắt đầu
//        if (origin == null || origin.isEmpty()) {
//
//            double latitude = 0, longitude = 0;
//            GPSTracker tracker = new GPSTracker(this);
//            if (!tracker.canGetLocation()) {
//                tracker.showSettingsAlert();
//            } else {
//                latitude = tracker.getLatitude();
//                longitude = tracker.getLongitude();
//            }
//            startAddress = getAddressByLocation(latitude, longitude);
//            origin = startAddress;
//
//        }
//
//        etOrigin.setText(startAddress);
//        if (!startAddress.isEmpty()) {
//            btnClear1.setVisibility(View.VISIBLE);
//        }
//        //endregion
//        //Neu khach hang co toa do thi lay dia chi theo toa do
//        //Nguoc lai lay theo dia chi khach hang
//        //region Destination
//        if ((!endLatitude.isEmpty() && !endLatitude.equals("0.0") && !endLatitude.equals("0"))
//                && (!endLongitude.isEmpty() && !endLongitude.equals("0.0") && !endLongitude.equals("0"))) {
//            destination = endLatitude + "," + endLongitude;
//            try {
//                endAddress = getAddressByLocation(Double.parseDouble(endLatitude), Double.parseDouble(endLongitude));
//            } catch (NumberFormatException e) {
//
//            }
//        } else {
//            destination = endAddress;
//        }
//        etDestination.setText(endAddress);
//        if (!endAddress.isEmpty()) {
//            btnClear.setVisibility(View.VISIBLE);
//        }
//        //endregion
//    }

    private String getAddressByLocation(double latitude, double longitude) {
        String So = "", Duong = "", Phuong = "", Quan = "", Tinh = "", QuocGia = "";
        List<Address> addresses = new ArrayList<>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
        }
        if (addresses != null && addresses.size() > 0) {
            So = addresses.get(0).getSubThoroughfare() == null ? "" : addresses.get(0).getSubThoroughfare();
            Duong = addresses.get(0).getThoroughfare() == null ? "" : addresses.get(0).getThoroughfare();
            Phuong = addresses.get(0).getSubLocality() == null ? "" : addresses.get(0).getSubLocality();
            Quan = addresses.get(0).getSubAdminArea() == null ? "" : addresses.get(0).getSubAdminArea();
            Tinh = addresses.get(0).getAdminArea() == null ? "" : addresses.get(0).getAdminArea();
            QuocGia = addresses.get(0).getCountryName() == null ? "" : addresses.get(0).getCountryName();
            return So + " " + Duong + " " + Phuong + " " + Quan + " " + Tinh + " " + QuocGia;
        }
        return "";
    }

    private boolean CreateAddress() {
        String originA = etOrigin.getText().toString();
        if (!startLatitude.isEmpty() && !startLongitude.isEmpty() && startAddress.equals(originA)) {
            originA = startLatitude + "," + startLongitude;
        }
        String destinationB = etDestination.getText().toString();
        if (!endLatitude.isEmpty() && !endLongitude.isEmpty() && endAddress.equals(destinationB)) {
            originA = startLatitude + "," + startLongitude;
        }
        if (originA.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ đi", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (destinationB.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ đến", Toast.LENGTH_SHORT).show();
            return false;
        }
        originA = AppUtils.chuyenCoDauThanhKhongDau(originA);
        originFinal = originA;
        destinationB = AppUtils.chuyenCoDauThanhKhongDau(destinationB);
        destinationFinal = destinationB;
        return true;
    }

    private void sendRequest() {
        if (!CreateAddress()) return;
        mapPresenter.DowloadDataMap(originFinal, destinationFinal);
    }

    @Override
    public void onDirectionFinderStart() {
        showProgressBar(true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

//    @Override
//    public void onGetKhachHangSuccess(KhachHangInfo khachHangInfo) {
//        setDiaChiXem(khachHangInfo);
//        sendRequest();
//    }
//
//    @Override
//    public void onGetKhachHangError(String error) {
//        setDiaChiXem(null);
//        sendRequest();
//    }

    @Override
    public void onDowloadDataMapSuccess(List<RouteInfo> routes, String link) {
        linkGoogleMap = link;
        showProgressBar(false);
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (RouteInfo route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getStartLocation(), 16));
//            ((TextView) findViewById(R.id.tvDuration)).setText(route.getDuration().text);
//            ((TextView) findViewById(R.id.tvDistance)).setText(route.getDistance().text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_start_location))
                    .title(route.getStartAddress())
                    .position(route.getStartLocation())));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_end_location))
                    .title(route.getEndAddress())
                    .position(route.getEndLocation())));
            int colorPolyLine = Color.parseColor("#5F98F3");

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(colorPolyLine).
                    width(20);

            for (int i = 0; i < route.getPoints().size(); i++)
                polylineOptions.add(route.getPoints().get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onDowloadDataMapError(String error) {
        Toast.makeText(MapActivity.this, error, Toast.LENGTH_LONG).show();
    }


    //Vi tri bat dau
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GPSTracker tracker = new GPSTracker(MapActivity.this);
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        }
        LatLng locationCty = new LatLng(tracker.getLatitude(), tracker.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCty, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("CTY")
                .position(locationCty)));
        if (this.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && this.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    public void showProgressBar(boolean isShow) {
        if (isShow) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progressbar_waiting);
            progressDialog.show();

        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            double la = location.getLatitude();
            double lo = location.getLongitude();
            startAddress = getAddressByLocation(la, lo);
            origin = startAddress;
            etOrigin.setText(startAddress);
        }

    }
}
