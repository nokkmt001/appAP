package com.anphat.supplier.ui.map.mapcty;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.RouteInfo;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.ui.base.BaseActivity;
import com.anphat.supplier.ui.map.GPSTracker;
import com.anphat.supplier.ui.map.MapContract;
import com.anphat.supplier.ui.map.MapPresenter;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.CommonUtils;
import com.anphat.supplier.utils.PublicVariables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapCustomerActivity extends BaseActivity implements OnMapReadyCallback, MapContract.View, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    ConstraintLayout layoutMain;
    RelativeLayout layoutSearch;
    //latitude: Vi do, longitude: Kinh do
    String endAddress = "", endLatitude = "", endLongitude = "";
    String startAddress = "", startLatitude = "", startLongitude = "";
    String origin = "";
    String destination = "";
    String originFinal = "", destinationFinal = "";
    String linkGoogleMap;
    MapPresenter mapPresenter;
    ImageView imageBack;
    RelativeLayout appbar;
    Boolean isClick = false;
    LocationManager lm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_customer;
    }

    @Override
    protected void initView() {
        showProgressDialog(true);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        registerReceiver(gpsReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        if (this.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && this.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapCustomerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        appbar = findViewById(R.id.appbar);
        layoutSearch = findViewById(R.id.layoutSearch);
        layoutMain = findViewById(R.id.layoutMain);
        imageBack = findViewById(R.id.imageBack);

        imageBack.setOnClickListener((View view) -> finish());
    }

    @Override
    protected void initData() {
        mapPresenter = new MapPresenter(this);
        Bundle bundle = this.getIntent().getExtras();
        try {
            endAddress = bundle.getString("Address");
            endAddress = (endAddress == null) ? "" : endAddress;
        } catch (Exception e) {
        }

        setDiaChiXem("");
        sendRequest();
    }

    @Override
    public void onClick(View view) {
    }

    private void setDiaChiXem(String address) {
//        Neu kho co toa do thi lay dia chi theo toa do
//        Nguoc lai lay theo dia chi trong kho
//        Neu kho ko co toa do va dia chi thi lay dia chi hien tai
//        region Origin
//        if (khachHangInfo != null) {
//             = khachHangInfo.getAddress() == null ? "" : "";
//            startLatitude = khachHangInfo.getX() == null ? "" : khachHangInfo.getX();
//            startLongitude = khachHangInfo.getY() == null ? "" : khachHangInfo.getY();
//        }
        startAddress = "";
        if (!startLatitude.isEmpty() && !startLatitude.equals("0") && !startLongitude.isEmpty() && !startLongitude.equals("0")) {
            origin = startLatitude + "," + startLongitude;
            try {
                startAddress = getAddressByLocation(Double.parseDouble(startLatitude), Double.parseDouble(startLongitude));
            } catch (NumberFormatException e) {
            }
        } else {
            origin = startAddress;
        }
//        Nếu kho mặc định không có địa chỉ thì lấy vị trí hiện tại làm điểm bắt đầu
        if (origin == null || origin.isEmpty()) {

            double latitude = 0, longitude = 0;
            GPSTracker tracker = new GPSTracker(this);
            if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {
                latitude = tracker.getLatitude();
                longitude = tracker.getLongitude();
            }
            startAddress = getAddressByLocation(latitude, longitude);
            origin = startAddress;

        }


        if (!startAddress.isEmpty()) {
        }
        //endregion
        //Neu khach hang co toa do thi lay dia chi theo toa do
        //Nguoc lai lay theo dia chi khach hang
        //region Destination
        if ((!endLatitude.isEmpty() && !endLatitude.equals("0.0") && !endLatitude.equals("0"))
                && (!endLongitude.isEmpty() && !endLongitude.equals("0.0") && !endLongitude.equals("0"))) {
            destination = endLatitude + "," + endLongitude;
            try {
                endAddress = getAddressByLocation(Double.parseDouble(endLatitude), Double.parseDouble(endLongitude));
            } catch (NumberFormatException e) {

            }
        } else {
            destination = endAddress;
        }
        //endregion
    }

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
        String originA = "";
        if (!startLatitude.isEmpty() && !startLongitude.isEmpty() && startAddress.equals(originA)) {
            originA = startLatitude + "," + startLongitude;
        }
        String destinationB = "";
        if (!endLatitude.isEmpty() && !endLongitude.isEmpty() && endAddress.equals(destinationB)) {
            originA = startLatitude + "," + startLongitude;
        }
        if (originA.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập địa chỉ đi", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (destinationB.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập địa chỉ đến", Toast.LENGTH_SHORT).show();
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
        showProgressDialog(false);

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

    @Override
    public void onDowloadDataMapSuccess(List<RouteInfo> routes, String link) {
        linkGoogleMap = link;
        showProgressDialog(false);
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (RouteInfo route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getStartLocation(), 16));

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
        showMessage(error);
        showProgressDialog(false);
    }

    //Vi tri bat dau
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showProgressDialog(false);
        Location gg = AppUtils.getLocationWithCheckNetworkAndGPS(this);
        GPSTracker tracker = new GPSTracker(getApplicationContext());
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LatLng locationCty = new LatLng(gg.getLatitude(), gg.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCty, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title(getAddressByLocation(locationCty.latitude, locationCty.longitude))
                .position(locationCty)));

        mMap.setMyLocationEnabled(true);
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );

        if (PublicVariables.listKho.size() > 0) {
            for (KhoInfo item : PublicVariables.listKho) {
                if (item.getDiachi()!=null){
                    LatLng latLng = null;
                    latLng = getLocationFromAddress(item.getDiachi());
                    if (latLng != null) {
                        mMap.addMarker(new MarkerOptions()
                                .icon(AppUtils.bitmapDescriptorFromVector(this, R.drawable.ic_home_blue))
                                .title(item.getTenkho())
                                .snippet(item.getDiachi())
                                .position(new LatLng(latLng.latitude, latLng.longitude)));
                    }
                }
            }
        }
//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//                LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                        ltlng, 16f);
//                mMap.animateCamera(cameraUpdate);
//            }
//        });
//        Location location1 = mMap.getMyLocation();

//        mMap.setOnMapLongClickListener(latLng -> {
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            if (isDestination) {
//                etDestination.setText(getAddressByLocation(latLng.latitude, latLng.longitude));
//            }
//            markerOptions.title(getAddressByLocation(latLng.latitude, latLng.longitude));
//            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
//                    latLng, 15);
//            mMap.animateCamera(location);
//            mMap.addMarker(markerOptions);
//        });
//
//        mMap.setOnMarkerClickListener(marker -> {
//            if (!isClick) {
//                marker.showInfoWindow();
//            } else {
//                marker.hideInfoWindow();
//            }
//            return true;
//        });
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
        }

    }

    private String getAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() == 0) {
                return "No Address Found";
            }
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {

                ft.remove(prev);
            }
            ft.addToBackStack(null);
//            DialogFragment dialogFragment = new ConfirmAddress();
//
//            Bundle args = new Bundle();
//            args.putDouble("lat", latLng.latitude);
//            args.putDouble("long", latLng.longitude);
//            args.putString("address", address);
//            dialogFragment.setArguments(args);
//            dialogFragment.show(ft, "dialog");
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "No Address Found";

        }
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

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

//            Collections.sort(list, (o1, o2) -> o1.getTinhChatCongViec().compareTo(o2.getTinhChatCongViec()));
//            adapterShow.clear();
//            adapterShow.addAll(list);
//            etDestination.setText(list.get(0).getDiachi());
//            for (CongViecInfo item : list) {
//                LatLng latLng = null;
//                latLng = getLocationFromAddress(item.getDiachi());
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
//                if (latLng != null) {
//                    mMap.addMarker(new MarkerOptions()
//                            .icon(CommonUtils.bitmapDescriptorFromVector(this, R.drawable.ic_map_location_customer))
//                            .title(item.getMaKhachHang())
//                            .snippet(item.getDiachi())
//                            .position(new LatLng(latLng.latitude, latLng.longitude)))
//                            .showInfoWindow();
//                }
//            }

    private BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                //Do your stuff on GPS status change
                if (!CommonUtils.checkLocation(getApplicationContext())) {
                    buildAlertMessageNoGps();
                }
            }
        }
    };

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng bật chức năng định vị để tiếp tục sử dụng dịch vụ")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                    finish();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}