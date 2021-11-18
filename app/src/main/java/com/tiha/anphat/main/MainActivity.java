package com.tiha.anphat.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.databinding.ActivityMainBinding;
import com.tiha.anphat.ui.account.AccountFragment;
import com.tiha.anphat.ui.base.BaseActivity;
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
    };
    ActivityMainBinding binding;
    MainPresenter presenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLoadData() {
        presenter = new MainPresenter(this);
        presenter.GetListAllProduct();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onInit() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        checkSelfPermission(permissionsRequired);
        fmManager = getSupportFragmentManager();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);

        binding.layoutHeader.imageDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);

            }
        });

//        binding.layoutHeader.imageCart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                BadgeDrawable drawable = BadgeDrawable.create(MainActivity.this);
//                drawable.setNumber(1);
//                drawable.setBackgroundColor(Color.RED);
//                binding.layoutHeader.imageCart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });

        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ĐĂNG XUẤT")
                        .setMessage("Bạn có chắc muốn đăng xuất ứng dụng?")
                        .setCancelable(false)
                        .setPositiveButton("ĐĂNG XUẤT", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                PublicVariables.ClearData();
                                AppPreference appPreference = new AppPreference(MainActivity.this);
                                appPreference.setLogin(false);
                                appPreference.setPassWord("");
                                appPreference.setUserID("");

                                Intent intent = new Intent(MainActivity.this, CheckLoginByIDPassActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("HỦY BỎ", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });

        binding.layoutHeader.imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.layoutHeader.imageSms.setOnClickListener(new View.OnClickListener() {
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
                            setBottomNavigationView(fmMain,new HomeFragment(),"1");
                            return true;

                        case R.id.navigation_history:
                            setBottomNavigationView(fmHistory,new ProductFragment(),"2");
                            return true;

                        case R.id.navigation_sms:
                            setBottomNavigationView(fmSms,new SmsFragment(),"3");
                            return true;

                        case R.id.navigation_pay:
                            setBottomNavigationView(fmPay,new PayFragment(),"4");
                            return true;

                        case R.id.navigation_account:
                            setBottomNavigationView(fmAccount,new AccountFragment(),"5");
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("THOÁT ỨNG DỤNG")
                    .setMessage("Bạn có chắc muốn thoát ứng dụng?")
                    .setCancelable(false)
                    .setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            moveTaskToBack(true);
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void setBottomNavigationView(Fragment fmMain, Fragment fragment,String tab) {
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
    public void onGetListAllCartSuccess(List<CartCondition> list) {

    }

    @Override
    public void onGetListAllCartError(String error) {

    }
}