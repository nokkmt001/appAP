package com.anphat.supplier.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.ui.category.DetailCategoryFragment;
import com.anphat.supplier.ui.pay.history.HistoryFragment;
import com.anphat.supplier.ui.pay.pending.PendingFragment;
import com.anphat.supplier.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.databinding.ActivityMainBinding;
import com.anphat.supplier.ui.account.AccountFragment;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.home.CommonFM;
import com.anphat.supplier.ui.home.HomeFragment;
import com.anphat.supplier.ui.introduce.IntroduceActivity;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.ui.notification.NotificationActivity;
import com.anphat.supplier.ui.pay.PayFragment;
import com.anphat.supplier.ui.sms.SmsFragment;
import com.anphat.supplier.ui.sms.newsfeed.NewsFeedFragment;
import com.anphat.supplier.ui.update.UpdateActivity;
import com.anphat.supplier.utils.CommonUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseTestActivity<ActivityMainBinding> implements MainContract.View {
    BottomNavigationView bottomNavigationView;
    FragmentManager fmManager;
    Fragment fmMain, fmHistory, fmPay, fmSms, fmAccount, fmActive;
    String[] permissionsRequired = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };

    MainPresenter presenter;
    public static final int FROM_MAIN = 0;
    TestReceiver testReceiver;
    HomeFragment fragmentMain;

    @Override
    protected void initData() {
        hideKeyboard();
        testReceiver = new TestReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestConstants.ACTION_MAIN_ACTIVITY);
        registerReceiver(testReceiver, intentFilter);

        presenter = new MainPresenter(this);
        if (AppPreference.getAllProduct()==null){
            presenter.GetListProductNew("api/products");
        }
        presenter.checkBooking();
        presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
//        presenter.GetListKho();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String gg = bundle.getString("KEYMAIN");
        if (gg != null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_pay);
        }
    }

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        presenter = new MainPresenter(this);
        checkSelfPermission(permissionsRequired);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        binding.imageCall.setOnClickListener(v -> alertDialog("ĐẶT GAS", "Bạn có chắc muốn đặt gas?", "OK", null, (dialogInterface, i) -> {
            showProgressDialog(true);
            presenter.CheckDaHang();
        }));
        binding.layoutHeader.textTitle.setOnClickListener(view -> onCallHotline());

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

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view13 -> showCart());

        binding.layoutHeader.layoutNotifications.layoutClick.setOnClickListener(view12 -> {
        });

        binding.layoutIntroduce.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IntroduceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.layout.main.setOnClickListener(view14 -> linkWed());
        binding.layoutHeader.textTitle.setText(R.string.hotline);

        /*if (!CommonUtils.checkLocation(this)) {
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

//        this.serviceIntent = new Intent(this, DownLoadService.class);
//        startService(this.serviceIntent);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);

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

    private void showCart() {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, FROM_MAIN);
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
                            fragmentMain.setClick((view, position) -> binding.layoutHeader.layoutGGG.setVisibility(View.GONE));
                            setBottomNavigationView(fmMain, fragmentMain, "1");
                            CommonFM.fragment = fragmentMain;
                            return true;
                        case R.id.navigation_history:
                            setBottomNavigationView(fmHistory, new NewsFeedFragment(), "2");
                            return true;
                        case R.id.navigation_pay:
                            setBottomNavigationView(fmPay, new PendingFragment(), "3");
                            return true;
                        case R.id.navigation_report:
                            setBottomNavigationView(fmSms, new HistoryFragment(), "4");
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
                        PublicVariables.ClearData();
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

    }

    @Override
    public void onGetCategoryError(String error) {
        showMessage(error);
    }

    @Override
    public void onCheckBookingSuccess(BookingInfo info) {
        PublicVariables.itemBooking = info;
    }

    @Override
    public void onCheckBookingError(String error) {
        PublicVariables.itemBooking = null;
    }

    @Override
    public void onCheckDaHangSuccess(List<CartInfo> info) {
        showCart();
        showProgressDialog(false);
    }

    @Override
    public void onCheckDatHangError(String error) {
        showProgressDialog(false);
        for (CategoryNew item : AppPreference.getCategory()) {
            if (item.slug.equals("gas")) {
                StartDetailCategory(item);
                binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSendBookingSuccess(CallInfo item) {
        showToast("Gửi yêu cầu thành công");
        AppUtils.createSound(this, "Đặt hàng thành công", "Vui lòng đợi phản hồi từ quản trị viên.");
        showProgressDialog(false);
    }

    @Override
    public void onSendBookingError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductNewSuccess(List<ProductNew> list) {
        AppPreference.saveProduct(list);
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductNewError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }

    public void onLoadCartListener() {
        presenter = new MainPresenter(this);
        presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
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
            presenter = null;
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
            switch (eventName) {
                case TestConstants.RECEIVE_ThayDoiGioHang:
                    onLoadCartListener();
                    break;
                case "kk":
                    binding.layoutHeader.layoutGGG.setVisibility(View.VISIBLE);
                    break;
                case "hh":
                    binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
                    break;
                case "booking":
                    Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                    intent1.putExtra("KEYMAIN", "reload");
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    }

    private void StartDetailCategory(CategoryNew info) {
        DetailCategoryFragment nextFrag = new DetailCategoryFragment(info);
        CommonFM.fragmentThree = nextFrag;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag, "three")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

}
