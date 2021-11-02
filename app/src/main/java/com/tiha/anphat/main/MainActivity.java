package com.tiha.anphat.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.kho.KhoInfo;
import com.tiha.anphat.databinding.ActivityMainBinding;
import com.tiha.anphat.ui.account.AccountFragment;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.cart.CartActivity;
import com.tiha.anphat.ui.home.HomeFragment;
import com.tiha.anphat.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.tiha.anphat.ui.pay.PayFragment;
import com.tiha.anphat.ui.product.ProductFragment;
import com.tiha.anphat.ui.sms.SmsFragment;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.PublicVariables;

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
    };
    ActivityMainBinding binding;
    MainPresenter presenter;
    RelativeLayout relativeLayout;
    public static final int FROM_MAIN = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLoadData() {
        presenter = new MainPresenter(this);
        presenter.GetListAllProduct();
        presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
        presenter.GetListKho();
    }

    @Override
    protected void initView() {
        relativeLayout = findViewById(R.id.layoutHeader);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkSelfPermission(permissionsRequired);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        fmManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);

        binding.layoutHeader.imageDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog( "ĐĂNG XUẤT", "Bạn có chắc muốn đăng xuất ứng dụng?", "ĐĂNG XUẤT", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PublicVariables.ClearData();
                        AppPreference appPreference = new AppPreference(MainActivity.this);
                        appPreference.setLogin(false);
                        appPreference.setPassWord("");
                        appPreference.setUserID("");
                        Intent intent = new Intent(MainActivity.this, CheckLoginByIDPassActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, FROM_MAIN);
            }
        });

        binding.layoutHeader.layoutNotifications.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_main:
                            setBottomNavigationView(fmMain, new HomeFragment(), "1");
                            return true;

                        case R.id.navigation_history:
                            setBottomNavigationView(fmHistory, new ProductFragment(), "2");
                            return true;

                        case R.id.navigation_sms:
                            setBottomNavigationView(fmSms, new SmsFragment(), "4");
                            return true;

                        case R.id.navigation_pay:
                            setBottomNavigationView(fmPay, new PayFragment(), "3");
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
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);
                    finishAffinity();
                }
            });

        }

    }

    public void setBottomNavigationView(Fragment fmMain, Fragment fragment, String tab) {
        if (fmMain == null) {
            fmMain = fragment;
            if (fmActive != null)
                fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).hide(fmActive).commit();
            else
                fmManager.beginTransaction().add(R.id.frame_container, fmMain, tab).commit();
        } else {
            fmManager.beginTransaction().hide(fmActive).show(fmMain).commit();
        }
        fmActive = fmMain;
    }

    public void SetTitle(String textView) {
        binding.layoutHeader.textTitle.setText(textView);
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
}