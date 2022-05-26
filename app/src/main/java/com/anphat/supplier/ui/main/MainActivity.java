package com.anphat.supplier.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.NotificationCondition;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.category.DetailCategoryFragment;
import com.anphat.supplier.ui.home.ShowFragment;
import com.anphat.supplier.ui.login.forgetpass.ChangePassActivity;
import com.anphat.supplier.ui.pay.history.HistoryFragment;
import com.anphat.supplier.ui.pay.pending.PendingFragment;
import com.anphat.supplier.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.databinding.ActivityMainBinding;
import com.anphat.supplier.ui.account.AccountFragment;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.home.CommonFM;
import com.anphat.supplier.ui.home.HomeFragment;
import com.anphat.supplier.ui.introduce.IntroduceActivity;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.ui.notification.NotificationActivity;
import com.anphat.supplier.ui.sms.newsfeed.NewsFeedFragment;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.List;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding, MainViewModel> {
    BottomNavigationView bottomNavigationView;
    FragmentManager fmManager;
    Fragment fmMain, fmHistory, fmPay, fmSms, fmAccount, fmActive;
    String[] permissionsRequired = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };

    public static final int FROM_MAIN = 0;
    TestReceiver testReceiver;
    HomeFragment fragmentMain;
    NewsFeedFragment fragmentNews;
    PendingFragment pendingFragment;
    HistoryFragment historyFragment;
    AccountFragment accountFragment;

    @Override
    protected Class<MainViewModel> getClassVM() {
        return MainViewModel.class;
    }

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        hideKeyboard();
        testReceiver = new TestReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestConstants.ACTION_MAIN_ACTIVITY);
        registerReceiver(testReceiver, intentFilter);

        viewModel.GetListAllCart();
        viewModel.checkBooking();
        viewModel.GetListKho();
        NotificationCondition condition = new NotificationCondition();
        condition.Begin = 1;
        condition.End = 100;
        condition.LoaiThongBao = "TatCa";
        condition.NguoiDungMobileID = PublicVariables.UserInfo.getNguoiDungMobileID();
        condition.TrangThai = "ChuaXem";
        viewModel.getListNotification(condition);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String gg = bundle.getString("KEYMAIN");
        if (gg != null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_pay);
        }
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.getItem().observe(this, result -> {
            showProgressDialog(false);
            if (result != null) {
                if (result.Status == 0) {
                    PublicVariables.listBooking = result.Data;
                    if (result.Data != null && result.Data.size() > 0) {
                        binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.VISIBLE);
                        binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(result.Data.size()));
                    } else binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getmItemCheckBooking().observe(this, result -> {
            showProgressDialog(false);
            if (result != null) {
                if (result.Status == 0) {
                    PublicVariables.itemBooking = result.Data;
                } else {
                    PublicVariables.itemBooking = null;
                }
            } else {
                PublicVariables.itemBooking = null;
            }
        });

        viewModel.getmItemDaHang().observe(this, result -> {
            showProgressDialog(false);
            if (result != null) {
                if (result.Data != null) {
                    showCart();
                } else {
                    for (CategoryNew item : AppPreference.getCategory()) {
                        if (item.slug.equals("gas")) {
                            StartDetailCategory(item);
                            binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
                        }
                    }
                }
            } else {
                for (CategoryNew item : AppPreference.getCategory()) {
                    if (item.slug.equals("gas")) {
                        StartDetailCategory(item);
                        binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
                    }
                }
            }
        });

        viewModel.itemListSuccess.observe(this, result -> {
            if (result != null) {
                if (result.List.size() > 0) {
                    binding.layoutHeader.layoutNotifications.textNumberCart.setVisibility(View.VISIBLE);
                    binding.layoutHeader.layoutNotifications.textNumberCart.setText(String.valueOf(result.List.size()));
                } else {
                    binding.layoutHeader.layoutNotifications.textNumberCart.setVisibility(View.GONE);
                }
            } else {
                binding.layoutHeader.layoutNotifications.textNumberCart.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initView() {
        checkSelfPermission(permissionsRequired);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        binding.imageCall.setOnClickListener(v -> alertDialog("ĐẶT GAS", "Bạn có chắc muốn đặt gas?", "OK", null, (dialogInterface, i) -> {
            showProgressDialog(true);
            viewModel.CheckDaHang();
        }));
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

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view13 -> showCart());

        binding.layoutHeader.layoutNotifications.layoutClick.setOnClickListener(view12 -> {
        });

        binding.layoutIntroduce.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IntroduceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.layout.main.setOnClickListener(view14 -> linkWed());

        binding.layoutHeader.layoutNotifications.layoutClick.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.imageChat.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            String url = "https://zalo.me/0988351352";
            myIntent.setData(Uri.parse(url));
            startActivity(myIntent);
        });

        binding.layoutHeader.textTitle.setOnClickListener(view -> {
                    binding.layoutHeader.layoutGGG.setVisibility(View.GONE);
                    StartFullProduct(AppPreference.getProductFull());
                }
        );
        binding.layoutChangePass.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChangePassActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("OBJECT", PublicVariables.UserInfo);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        fragmentMain = new HomeFragment();
        fragmentNews = new NewsFeedFragment();
        pendingFragment = new PendingFragment();
        historyFragment = new HistoryFragment();
        accountFragment = new AccountFragment();

    }

    private void StartFullProduct(List<ProductNew> list) {
        ShowFragment nextFrag = new ShowFragment(list);
        CommonFM.fragmentFour = nextFrag;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag, "three")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
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
                            fmMain = fragmentMain;
                            CommonFM.fragment = fragmentMain;
                            return true;
                        case R.id.navigation_history:
                            setBottomNavigationView(fmHistory, fragmentNews, "2");
                            fmHistory = fragmentNews;
                            return true;
                        case R.id.navigation_pay:
                            setBottomNavigationView(fmPay, pendingFragment, "3");
                            fmPay = pendingFragment;
                            return true;
                        case R.id.navigation_report:
                            setBottomNavigationView(fmSms, historyFragment, "4");
                            fmSms = historyFragment;
                            return true;
                        case R.id.navigation_account:
                            setBottomNavigationView(fmAccount, accountFragment, "5");
                            fmAccount = accountFragment;
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
        CommonFM.fragmentWait = fragment;
      /*  if (fmMain == null) {
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
                if (CommonFM.fragmentFour != null) {
                    fmManager.beginTransaction().hide(CommonFM.fragmentFour).commit();
                    CommonFM.fragmentFour = null;
                }
                fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).hide(fmActive).commit();
            } else {

                fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).commit();
//                    CommonFM.fragmentTwo = null;
//                } else {
//                    fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).commit();
//                }
            }
        } else {
            if (!fmMain.isAdded()) {
                fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).commit();
                if (fmActive != null) {
                    fmManager.beginTransaction().hide(fmActive).commit();
                }
                fmActive = fmMain;
                return;
            }
            fmManager.beginTransaction().hide(fmActive).show(fmMain).commit();

        }
        fmActive = fmMain;*/
        fmManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onLoadCartListener() {
        viewModel.GetListAllCart();
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
            viewModel = null;
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
                    CommonFM.fragment = null;
                    CommonFM.fragmentTwo = null;
                    CommonFM.fragmentThree = null;
                    CommonFM.fragmentFour = null;
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
                .replace(R.id.frame_container, nextFrag, "three")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

}
