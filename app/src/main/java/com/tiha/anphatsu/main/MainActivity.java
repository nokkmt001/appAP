package com.tiha.anphatsu.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.CartInfo;
import com.tiha.anphatsu.data.entities.CategoryInfo;
import com.tiha.anphatsu.data.entities.ProductInfo;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.data.entities.kho.KhoInfo;
import com.tiha.anphatsu.databinding.ActivityMainBinding;
import com.tiha.anphatsu.service.DownLoadService;
import com.tiha.anphatsu.ui.account.AccountFragment;
import com.tiha.anphatsu.ui.base.BaseActivity;
import com.tiha.anphatsu.ui.cart.CartActivity;
import com.tiha.anphatsu.ui.home.CommonFM;
import com.tiha.anphatsu.ui.home.HomeFragment;
import com.tiha.anphatsu.ui.introduce.IntroduceActivity;
import com.tiha.anphatsu.ui.login.checkphone.CheckPhoneActivity;
import com.tiha.anphatsu.ui.notification.NotificationActivity;
import com.tiha.anphatsu.ui.pay.PayFragment;
import com.tiha.anphatsu.ui.sms.SmsFragment;
import com.tiha.anphatsu.ui.sms.newsfeed.NewsFeedFragment;
import com.tiha.anphatsu.ui.update.UpdateActivity;
import com.tiha.anphatsu.utils.CommonUtils;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.TestConstants;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {
    BottomNavigationView bottomNavigationView;
    FragmentManager fmManager;
    Fragment fmMain, fmHistory, fmPay, fmSms, fmAccount, fmActive;
    String[] permissionsRequired = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };

    ActivityMainBinding binding;
    MainPresenter presenter;
    RelativeLayout relativeLayout;
    public static final int FROM_MAIN = 0;
    TestReceiver testReceiver;
    HomeFragment fragmentMain;

    Fragment fmTwo;
    Fragment fmThree;
    Fragment fmFour;
    Fragment fmFive;

    private Intent serviceIntent;

    private ResponseReceiver receiver = new ResponseReceiver();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        testReceiver = new TestReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestConstants.ACTION_MAIN_ACTIVITY);
        registerReceiver(testReceiver, intentFilter);

        presenter = new MainPresenter(this);
//        presenter.GetListAllProduct();
        presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
        presenter.GetListKho();
//        presenter.GetCategory();
    }

    @Override
    protected void initView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkSelfPermission(permissionsRequired);
        binding.imageCall.setOnClickListener(v -> onCallHotline());
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        fmManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        binding.layoutHeader.imageDrawer.setOnClickListener(view16 -> binding.drawerLayout.openDrawer(GravityCompat.START));

        binding.layoutLogout.setOnClickListener(view15 -> alertDialog("ĐĂNG XUẤT", "Bạn có chắc muốn đăng xuất ứng dụng?", "ĐĂNG XUẤT", null, (dialogInterface, i) -> {
            PublicVariables.ClearData();
            AppPreference appPreference = new AppPreference(MainActivity.this);
            appPreference.setLogin(false);
            appPreference.setPassWord("");
            appPreference.setUserID("");
            appPreference.setBooking(null);
            appPreference.setUser(null);
            appPreference.setOtp(false);
            PublicVariables.ClearData();
            Intent intent = new Intent(MainActivity.this, CheckPhoneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }));

        binding.layoutUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view13 -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, FROM_MAIN);
        });

        binding.layoutHeader.layoutNotifications.layoutClick.setOnClickListener(view12 -> {
        });

        binding.layoutIntroduce.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IntroduceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.layout.main.setOnClickListener(view14 -> linkWed());
        binding.layoutHeader.textTitle.setText(R.string.hotline);

        /**if (!CommonUtils.checkLocation(this)) {
            alertDialog("Thông tin", getString(R.string.title_warning_location), "CÓ", null,
                    (dialogInterface, i) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        }*/

        binding.layoutHeader.layoutNotifications.layoutClick.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);

        this.serviceIntent = new Intent(this, DownLoadService.class);
        startService(this.serviceIntent);
    }

    private void onCallHotline() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            return;
        }
        Intent myIntent = new Intent(Intent.ACTION_CALL);
        String phNum = "tel:0819000203";
        myIntent.setData(Uri.parse(phNum));
        startActivity(myIntent);
    }

    private void linkWed() {
        String url = getResources().getString(R.string.url_ap);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_main:
                            fragmentMain = new HomeFragment();
                            fragmentMain.setClick((view, position) -> {
                                binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
                            });
                            setBottomNavigationView(fmMain, fragmentMain, "1");
                            CommonFM.fragment = fragmentMain;
                            return true;
                        case R.id.navigation_history:
                            setBottomNavigationView(fmHistory, new NewsFeedFragment(), "2");
                            return true;
                        case R.id.navigation_pay:
                            setBottomNavigationView(fmPay, new PayFragment(), "3");
                            return true;
                        case R.id.navigation_report:
                            setBottomNavigationView(fmSms, new SmsFragment(), "4");
                            return true;
                        case R.id.navigation_account:
                            setBottomNavigationView(fmAccount, new AccountFragment(), "5");
                            return true;
                        default:
                            break;
                    }
                    return false;
                }
            };

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            alertDialog("THOÁT ỨNG DỤNG", "Bạn có chắc muốn thoát ứng dụng?", "CÓ", null,
                    (dialogInterface, i) -> {
                        moveTaskToBack(true);
                        finishAffinity();
                    });
        }
    }

    public void setBottomNavigationView(Fragment fmMain, Fragment fragment, String tab) {
        binding.layoutHeader.layoutGGG.setVisibility(View.VISIBLE);
        if (fmMain == null) {
            fmMain = fragment;
            if (fmActive != null) {
                if (CommonFM.fragment != null) {
                    fmManager.beginTransaction().hide(CommonFM.fragment).commit();
                    CommonFM.fragment = null;
                }
                if (CommonFM.fragmentTwo != null) {
                    fmManager.beginTransaction().hide(CommonFM.fragmentTwo).commit();
                    CommonFM.fragmentTwo = null;
                }
                if (CommonFM.fragmentThree != null) {
                    fmManager.beginTransaction().hide(CommonFM.fragmentThree).commit();
                    CommonFM.fragmentThree = null;
                }
                fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).hide(fmActive).commit();
            } else {
                if (CommonFM.fragmentTwo != null) {
                    fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).hide(CommonFM.fragmentTwo).commit();
                    CommonFM.fragmentTwo = null;
                } else {
                    fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).commit();
                }
            }
        } else {
            fmManager.beginTransaction().hide(fmActive).show(fmMain).commit();

        }
        fmActive = fmMain;
    }

    @Override
    public void onGetListAllProductSuccess(List<ProductInfo> list) {
        PublicVariables.listAllProDuct = list;
    }

    @Override
    public void onGetListAllProductError(String error) {
        CommonUtils.showMessageError(this, error);
    }

    @Override
    public void onInSertCartSuccess(CartCondition info) {

    }

    @Override
    public void onInsertCartError(String error) {

    }

    @Override
    public void onUpdateCartSuccess(CartCondition info) {

    }

    @Override
    public void onUpdateConditionError(String error) {

    }

    @Override
    public void onDeleteCartSuccess() {

    }

    @Override
    public void onDeleteCartError(String error) {

    }

    @Override
    public void onGetListAllCartSuccess(List<CartInfo> list) {
        PublicVariables.listBooking = list;
        if (list != null && list.size() > 0) {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.VISIBLE);
            binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(list.size()));
        } else binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.GONE);
    }

    @Override
    public void onGetListAllCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetListKhoSuccess(List<KhoInfo> list) {
        PublicVariables.listKho = list;
    }

    @Override
    public void onGetListKhoError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetCategorySuccess(List<CategoryInfo> list) {
        PublicVariables.listCategory = list;
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);
    }

    @Override
    public void onGetCategoryError(String error) {
        showMessage(error);
    }

    public void onLoadCartListener() {
        presenter = new MainPresenter(this);
//        presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FROM_MAIN) {
                onLoadCartListener();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (testReceiver != null)
                unregisterReceiver(testReceiver);
        } catch (Exception ignored) {
        }
    }

    private class TestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            String eventName = bundle.getString("eventName");
            if (TestConstants.RECEIVE_ThayDoiGioHang.equals(eventName)) {
                presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
            }
            String eventHome = bundle.getString("eventShow");
            if (eventHome==null) {
                return;
            }
            if (eventHome.equals("kk")) {
                binding.layoutHeader.layoutGGG.setVisibility(View.VISIBLE);
            } else {
                binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
            }
        }
    }
    // Broadcast component
    public class ResponseReceiver extends BroadcastReceiver {

        // On broadcast received
        @Override
        public void onReceive(Context context, Intent intent) {
            // Check action name.
            if(intent.getAction().equals(DownLoadService.ACTION_1)) {
                int value = intent.getIntExtra(DownLoadService.PARAM_PERCENT, 0);

                new ShowProgressBarTask().execute(value);
            }
        }
    }

    class ShowProgressBarTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... args) {

            return args[0];
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

//            progressBar.setProgress(result);
//
            binding.textMain.setText(result + " % Loaded");

            if (result == 100) {
                binding.textMain.setText("Completed");
//                buttonStart.setEnabled(true);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(receiver, new IntentFilter(
                DownLoadService.ACTION_1));
    }
}