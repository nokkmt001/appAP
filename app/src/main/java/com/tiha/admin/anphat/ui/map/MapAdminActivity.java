package com.tiha.admin.anphat.ui.map;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
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

import com.tiha.admin.anphat.ui.custom.DateDialogAdapter;
import com.tiha.admin.anphat.ui.employee.EmployeePresenter;
import com.tiha.admin.anphat.ui.map.customer.LocationContract;
import com.tiha.admin.anphat.ui.map.customer.LocationPresenter;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.EmployeeInfo;
import com.tiha.admin.anphat.data.entities.RouteInfo;
import com.tiha.admin.anphat.data.entities.condition.LocationCondition;
import com.tiha.admin.anphat.data.entities.location.InsertLocationInfo;
import com.tiha.admin.anphat.ui.base.BaseActivity;
import com.tiha.admin.anphat.ui.map.customer.SearchManAdapter;
import com.tiha.admin.anphat.utils.AppConstants;
import com.tiha.admin.anphat.utils.AppUtils;
import com.tiha.admin.anphat.utils.CommonUtils;
import com.tiha.admin.anphat.utils.PublicVariables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MapAdminActivity extends BaseActivity implements LocationContract.View, OnMapReadyCallback, MapContract.View, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    EmployeePresenter presenterKT;
    private Button btnFindPath, btnGoogleMap;
    private EditText etOrigin, inputKT, inputSearch;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    ConstraintLayout layoutMain;
    RelativeLayout layoutSearch;
    //latitude: Vi do, longitude: Kinh do
    private ImageButton btnClear, btnClear1;
    String endAddress = "", endLatitude = "", endLongitude = "";
    String startAddress = "", startLatitude = "", startLongitude = "";
    String origin = "";
    String destination = "";
    String originFinal = "", destinationFinal = "";
    Dialog progressDialog;
    String linkGoogleMap;
    MapPresenter mapPresenter;
    ImageView imageBack, imageFilter, imageExit, imageClear;
    SearchManAdapter adapterKT;
    RecyclerView recyclerViewKT;
    List<EmployeeInfo> listNhanVien = new ArrayList<>();
    TextView textUser, textChose;
    LocationPresenter presenterLocation;
    DateDialogAdapter adapterDateDialog;
    EmployeeInfo infoKT;
    LocationCondition condition;
    Date timeIn, timeEnd;
    RelativeLayout appbar;
    String[] permissionsRequired = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,};
    private final int REQUEST_MULTIPLE_PERMISSIONS = 100;
    LocationManager lm;
    Boolean isClick = false;
    private Timer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_admin;
    }

    @Override
    protected void initData() {
        registerReceiver(gpsReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        GPSTracker tracker = new GPSTracker(getApplicationContext());
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
            return;
        }
        presenterLocation = new LocationPresenter(this);
        mapPresenter = new MapPresenter(this);
        Bundle bundle = this.getIntent().getExtras();
        try {
            endAddress = bundle.getString("Address");
            endAddress = (endAddress == null) ? "" : endAddress;
        } catch (Exception e) {
        }
//        try {
//            endLatitude = bundle.getString("ViDo");
//            endLatitude = (endLatitude == null) ? "" : endLatitude;
//        } catch (Exception e) {
//            endLatitude = "";
//
//        }
//        try {
//            endLongitude = bundle.getString("KinhDo");
//            endLongitude = (endLongitude == null) ? "" : endLongitude;
//        } catch (Exception e) {
//            endLongitude = "";
//        }

        setDiaChiXem("");
        sendRequest();
    }

    private void checkSelfPermission() {
        if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED

        ) {
            ActivityCompat.requestPermissions(this, permissionsRequired, REQUEST_MULTIPLE_PERMISSIONS);
            //return;
        }
    }

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

    @Override
    public void initView() {
        checkSelfPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listNhanVien = PublicVariables.listEmployee;

        appbar = findViewById(R.id.appbar);
        imageExit = findViewById(R.id.imageExit);
        imageClear = findViewById(R.id.imageClear);
        textChose = findViewById(R.id.textChose);
        inputSearch = bind(R.id.inputSearch);
        inputKT = findViewById(R.id.inputKT);
        textUser = findViewById(R.id.textUser);
        layoutSearch = findViewById(R.id.layoutSearch);
        layoutMain = findViewById(R.id.layoutMain);
        recyclerViewKT = findViewById(R.id.recyclerViewKT);
        adapterKT = new SearchManAdapter(this, new ArrayList<>());
        recyclerViewKT.setAdapter(adapterKT);
        imageFilter = findViewById(R.id.imageFilter);
        imageBack = findViewById(R.id.imageBack);
        btnFindPath = findViewById(R.id.btnFindPath);
        btnGoogleMap = findViewById(R.id.btnGoogleMap);
        etOrigin = findViewById(R.id.etOrigin);
        etDestination = findViewById(R.id.etDestination);
        btnClear = findViewById(R.id.btnClear);
        btnClear1 = findViewById(R.id.btnClear1);

        btnFindPath.setOnClickListener(this);
        btnGoogleMap.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnClear1.setOnClickListener(this);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty())
                    imageClear.setVisibility(View.VISIBLE);
                else
                    imageClear.setVisibility(View.GONE);
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        MapAdminActivity.this.runOnUiThread(() -> adapterKT.getFilter().filter(inputSearch.getText().toString()));
                    }
                }, AppConstants.DELAY_FIND_DATA_SEARCH);
            }
        });

        etDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty())
                    btnClear.setVisibility(View.VISIBLE);
                else
                    btnClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etOrigin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty())
                    btnClear1.setVisibility(View.VISIBLE);
                else
                    btnClear1.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputKT.setOnClickListener(v -> onShowSearch(true));
//        imageFilter.setOnClickListener(view -> {
//            if (inputKT.getText().length() == 0) {
//                showNoResult();
//            } else {
//                PopupMenu menu = new PopupMenu(MapAdminActivity.this, imageFilter);
//                menu.getMenu().add(R.string.tilte_choose_time);
//                menu.getMenu().add(R.string.list_customer);
//                menu.setOnMenuItemClickListener(menuItem -> {
//                    if (menuItem.getTitle().equals(getResources().getString(R.string.tilte_choose_time))) {
//                        showFilter();
//                    } else {
//                        showPopupCustomer();
//                    }
//                    menu.dismiss();
//                    return true;
//                });
//                menu.show();
//            }
//        });

        adapterKT.setOnClickListener((view, position) -> {
            infoKT = adapterKT.getItem(position);

        });
        textChose.setOnClickListener(view -> {
            if (infoKT == null) {
                Toast.makeText(MapAdminActivity.this, R.string.error_no_choose_employee, Toast.LENGTH_SHORT).show();
            } else {
                mMap.clear();
                onShowSearch(false);
                inputKT.setText(infoKT.EmployeeName);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.clear(Calendar.MINUTE);
                cal.clear(Calendar.SECOND);
                cal.clear(Calendar.MILLISECOND);

                condition = new LocationCondition();
                condition.MaxResults = 1;
                condition.UserName = (infoKT.EmployeeID);
                timeIn = cal.getTime();
                timeEnd = Calendar.getInstance().getTime();
                condition.StartTime = (AppUtils.formatDateToString(cal.getTime(), "yyyy-MM-dd'T'HH:mm:ss"));
                condition.EndTime = (AppUtils.formatDateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd'T'HH:mm:ss"));
                presenterLocation.GetListLocation(condition);


            }
        });

//        imageExit.setOnClickListener(view -> onShowSearch(false));
        imageBack.setOnClickListener(view -> finish());
    }

//    private void showPopupCustomer() {
//        LayoutInflater inflaterMain = (LayoutInflater)
//                getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupView = inflaterMain.inflate(R.layout.layout_pop_up_customer, null);
//        int width = LinearLayout.LayoutParams.MATCH_PARENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        boolean focusable = false; // lets taps outside the popup also dismiss it
//        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//        // show the popup window
//        // which view you pass in doesn't matter, it is only used for the window tolken
//        popupWindow.showAtLocation(appbar, Gravity.TOP, 0, 0);
//        RecyclerView recyclerViewNT = popupView.findViewById(R.id.rcl);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerViewNT.setLayoutManager(mLayoutManager);
//        recyclerViewNT.setHasFixedSize(true);
//        recyclerViewNT.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewNT.setAdapter(adapterShow);
//
//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
//                return true;
//            }
//        });
//    }

//    private void showFilter() {
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.dialog_bottom_filter_location, null);
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
//        View bottomSheet = customView.findViewById(R.id.bottom_sheet);
//        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        dialog.setContentView(customView);
//        dialog.show();
//        EditText inputResult = customView.findViewById(R.id.inputResult);
//        EditText etNgayEnd = customView.findViewById(R.id.etNgayEnd);
//        EditText etGioEnd = customView.findViewById(R.id.etGioEnd);
//        EditText etGio = customView.findViewById(R.id.etGio);
//        EditText etNgay = customView.findViewById(R.id.etNgay);
//        Button btnApDung = customView.findViewById(R.id.btnApDung);
//        TextView tvDatLai = customView.findViewById(R.id.tvDatLai);
//        etNgay.setText(AppUtils.formatDateToString(timeIn, "dd/MM/yyyy"));
//        etGio.setText(AppUtils.formatDateToString(timeIn, "HH:mm:ss"));
//        etNgayEnd.setText(AppUtils.formatDateToString(timeEnd, "dd/MM/yyyy"));
//        etGioEnd.setText(AppUtils.formatDateToString(timeEnd, "HH:mm:ss"));
//        inputResult.setText(condition.getMaxResults().toString());
//        etNgay.setOnClickListener(view -> {
//            try {
//                adapterDateDialog = new DateDialogAdapter(view, etNgay.getText().toString());
//                FragmentTransaction ft = com.thienphuc.congviec.ui.mapforadmin.MapAdminActivity.this.getFragmentManager().beginTransaction();
//                adapterDateDialog.show(ft, "DatePicker");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        etNgayEnd.setOnClickListener(view -> {
//            try {
//                adapterDateDialog = new DateDialogAdapter(view, etNgayEnd.getText().toString());
//                FragmentTransaction ft = com.thienphuc.congviec.ui.mapforadmin.MapAdminActivity.this.getFragmentManager().beginTransaction();
//                adapterDateDialog.show(ft, "DatePicker");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        etGio.setOnClickListener(view -> DatetimePickerDialog.getInstance().showConfirmDialog(etNgay.getText().toString()
//                        + " " + (TextUtils.isEmpty(etGio.getText().toString()) ? "12:00" : etGio.getText().toString()),
//                com.thienphuc.congviec.ui.mapforadmin.MapAdminActivity.this, new DatetimePickerDialog.DialogClickInterface() {
//                    @Override
//                    public void onClickPositiveButton(String date) {
//                        etGio.setText(date);
//                    }
//
//                    @Override
//                    public void onClickNegativeButton() {
//                    }
//                }));
//        etGioEnd.setOnClickListener(view -> DatetimePickerDialog.getInstance().showConfirmDialog(etNgayEnd.getText().toString()
//                        + " " + (TextUtils.isEmpty(etGioEnd.getText().toString()) ? "12:00" : etGioEnd.getText().toString()),
//                com.thienphuc.congviec.ui.mapforadmin.MapAdminActivity.this, new DatetimePickerDialog.DialogClickInterface() {
//                    @Override
//                    public void onClickPositiveButton(String date) {
//                        etGioEnd.setText(date);
//                    }
//
//                    @Override
//                    public void onClickNegativeButton() {
//                    }
//                }));
//        btnApDung.setOnClickListener(view -> {
//            if (etNgay.getText().length() > 0 && etGio.getText().length() > 0) {
//                timeIn = AppUtils.formatStringToDateUtil(etNgay.getText().toString()
//                        + " " + etGio.getText().toString(), "dd/MM/yyyy HH:mm");
//            }
//            if (etNgayEnd.getText().length() > 0 && etGioEnd.getText().length() > 0) {
//                timeEnd = AppUtils.formatStringToDateUtil(etNgayEnd.getText().toString()
//                        + " " + etGioEnd.getText().toString(), "dd/MM/yyyy HH:mm");
//            }
////            if (inputResult.getText().length() == 0) {
////                CommonUtils.showMessageError(MapAdminActivity.this, "Bạn chưa chọn tổng kết quả");
////            } else
//            if (timeIn == null) {
//                CommonUtils.showMessageError(com.thienphuc.congviec.ui.mapforadmin.MapAdminActivity.this, "Bạn chưa chọn Ngày giờ bắt đầu");
//            } else if (timeEnd == null) {
//                CommonUtils.showMessageError(com.thienphuc.congviec.ui.mapforadmin.MapAdminActivity.this, "Bạn chưa chọn Ngày giờ kết thúc");
//            } else {
//                condition.setStartTime(AppUtils.formatDateToString(timeIn, "yyyy-MM-dd'T'HH:mm:ss"));
//                condition.setEndTime(AppUtils.formatDateToString(timeEnd, "yyyy-MM-dd'T'HH:mm:ss"));
//                condition.setMaxResults(1);
//                presenterLocation.GetLisLocationKT(condition);
//                dialog.cancel();
//            }
//
//        });
//        tvDatLai.setOnClickListener(view -> {
//            timeIn = null;
//            timeEnd = null;
//            etGio.setText("");
//            etGioEnd.setText("");
//            etNgay.setText("");
//            etNgayEnd.setText("");
//            inputResult.setText("");
//        });
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFindPath:
                sendRequest();
                break;
            case R.id.btnGoogleMap:
                if (!CreateAddress()) return;
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationFinal + "");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;
            case R.id.btnClear:
                etDestination.setText("");
                break;
            case R.id.btnClear1:
                etOrigin.setText("");
                break;
            default:
                break;
        }

    }

    public void onShowSearch(final boolean isShow) {
        layoutSearch.setVisibility(isShow ? View.VISIBLE : View.GONE);
        layoutMain.setVisibility(isShow ? View.GONE : View.VISIBLE);
        adapterKT.clear();
        adapterKT.addAll(listNhanVien);
    }

    private void setDiaChiXem(String address) {
//        Neu kho co toa do thi lay dia chi theo toa do
//        Nguoc lai lay theo dia chi trong kho
//        Neu kho ko co toa do va dia chi thi lay dia chi hien tai
//        region Origin
//        if (khachHangInfo != null) {
////             = khachHangInfo.getAddress() == null ? "" : "";
////            startLatitude = khachHangInfo.getX() == null ? "" : khachHangInfo.getX();
////            startLongitude = khachHangInfo.getY() == null ? "" : khachHangInfo.getY();
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
            GPSTracker tracker = new GPSTracker(getApplicationContext());
            if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {
                latitude = tracker.getLatitude();
                longitude = tracker.getLongitude();
            }
            startAddress = getAddressByLocation(latitude, longitude);
            origin = startAddress;

        }

        etOrigin.setText(startAddress);
        if (!startAddress.isEmpty()) {
            btnClear1.setVisibility(View.VISIBLE);
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
        etDestination.setText(endAddress);
        if (!endAddress.isEmpty()) {
            btnClear.setVisibility(View.VISIBLE);
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
        Location gg = AppUtils.getLocationWithCheckNetworkAndGPS(getApplicationContext());

        String originA = etOrigin.getText().toString();
        originA = getAddressByLocation(gg.getLatitude(), gg.getLongitude());
        if (!startLatitude.isEmpty() && !startLongitude.isEmpty() && startAddress.equals(originA)) {
            originA = startLatitude + "," + startLongitude;
        }
        String destinationB = etDestination.getText().toString();
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

    @Override
    public void onDowloadDataMapSuccess(List<RouteInfo> routes, String link) {
        linkGoogleMap = link;
        showProgressBar(false);
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (RouteInfo route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getStartLocation(), 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.getDuration().text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.getDistance().text);

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
        showToast(error);
    }


    //Vi tri bat dau
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GPSTracker tracker = new GPSTracker(getApplicationContext());
        if (!CommonUtils.checkLocation(getApplicationContext())) {
            tracker.showSettingsAlert();
            return;
        }
        Location gg = AppUtils.getLocationWithCheckNetworkAndGPS(getApplicationContext());
        if (gg == null) return;
        LatLng locationCty = new LatLng(gg.getLatitude(), gg.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCty, 18));

        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title(getAddressByLocation(gg.getLatitude(), gg.getLongitude()))
                .position(locationCty)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

//        mMap.setOnMapLongClickListener(latLng -> {
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//
//            markerOptions.title(getAddressByLocation(latLng.latitude, latLng.longitude));
//            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
//                    latLng, 15);
//            mMap.animateCamera(location);
//            mMap.addMarker(markerOptions);
//
//        });

        mMap.setOnMarkerClickListener(marker -> {
            if (!isClick) {
                marker.showInfoWindow();
                isClick = true;
            } else {
                marker.hideInfoWindow();
                isClick = false;
            }
            return true;
        });
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

    private String getAddress(LatLng latLng) {
        if (latLng == null) {
            return "No Address Found";
        }

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

//    @Override
//    public void onGetListCVKHAddressSuccess(List<CongViecInfo> list) {
//        Collections.sort(list, (o1, o2) -> o2.getTinhChatCongViec().compareTo(o1.getTinhChatCongViec()));
//        adapterShow.clear();
//        adapterShow.addAll(list);
//        for (int i = 0; i < list.size(); i++) {
//            CongViecInfo item = list.get(i);
//            LatLng latLng = null;
//            latLng = getLocationFromAddress(item.getDiachi());
//            if (latLng != null) {
//                mMap.addMarker(new MarkerOptions()
//                        .icon(CommonUtils.bitmapDescriptorFromVector(this, R.drawable.ic_map_location_customer))
//                        .title(item.getMaKhachHang())
//                        .snippet(item.getDiachi())
//                        .position(new LatLng(latLng.latitude, latLng.longitude))).showInfoWindow();
//            }
//        }
//    }
//
//    @Override
//    public void onGetListCVKHAddressError(String error) {
//
//    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS:
                //Kiem tra tat ca quyen can cap
                boolean allgranted = false;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        allgranted = true;
                    } else {
                        allgranted = false;
                        break;
                    }
                }
                if (!allgranted && (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])

                )) {
                    showMessagePermissions();
                } else {
                    // loadData();
                }
                break;
            default:
                break;
        }
    }

    //Hien thi thong bao ly do tai sao phai can cap nhung quyen nay cho ung dung
    private void showMessagePermissions() {
        new AlertDialog.Builder(this)
                .setTitle("QUYỀN ỨNG DỤNG")
                .setMessage("Ứng dụng cần được cấp nhiều quyền hơn.")
                .setPositiveButton("CẤP QUYỀN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MapAdminActivity.this, permissionsRequired, REQUEST_MULTIPLE_PERMISSIONS);
                    }
                })
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                })
                .show();
    }

    private BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                if (!CommonUtils.checkLocation(getApplicationContext())) {
                    buildAlertMessageNoGps();
                }
            }
        }
    };

    @Override
    public void onGetListLocationSuccess(List<InsertLocationInfo> list) {
        if (list.size() > 0) {
            Collections.sort(list, (t1, t2) -> t1.getCreateDate().compareTo(t2.getCreateDate()));

            for (int i = 0; i < list.size(); i++) {
                InsertLocationInfo item = list.get(i);
                endLatitude = item.getLatitude();
                endLongitude = item.getLongitude();
                LatLng locationCty = new LatLng(Double.parseDouble(endLatitude), Double.parseDouble(endLongitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationCty, 16));
                mMap.addMarker(new MarkerOptions()
                        .icon(CommonUtils.bitmapDescriptorFromVector(this, R.drawable.ic_location_kt))
                        .title(getAddressByLocation(Double.parseDouble(endLatitude), Double.parseDouble(endLongitude)))
                        .position(new LatLng(
                                Double.parseDouble(endLatitude),
                                Double.parseDouble(endLongitude)
                        ))
                ).showInfoWindow();


            }
        } else {
            showToast("Chưa có dữ liệu thông kê");
        }
    }

    @Override
    public void onGetListLocationError(String error) {
        showMessage(error);
    }
}
