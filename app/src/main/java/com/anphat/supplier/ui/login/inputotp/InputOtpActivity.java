package com.anphat.supplier.ui.login.inputotp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.base.SearchMain;
import com.anphat.supplier.ui.login.forgetpass.ForgetPassActivity;
import com.anphat.supplier.viewmodel.LoginViewModel;
import com.google.gson.Gson;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.databinding.ActivityOtpBinding;
import com.anphat.supplier.ui.main.MainActivity;
import com.anphat.supplier.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.utils.AppUtils;

public class InputOtpActivity extends BaseMVVMActivity<ActivityOtpBinding, LoginViewModel> {
    NewCustomer info;
    String textID = "";
    AppPreference preference;
    String toMain = "";

    @Override
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivityOtpBinding getViewBinding() {
        return ActivityOtpBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        AppUtils.enableButton(false, binding.buttonEnd, this);
        binding.etOtp.addTextChangedListener(new SearchMain() {
            @Override
            protected void onAfterChanged(String text) {
                checkEnableButton(text);
                onCheckCode(text);
            }
        });

        binding.buttonEnd.setOnClickListener(view13 -> {
            if (textID.equals(info.getMaPIN().toString())) {
                showAction();
                this.finish();
            } else {
                showMessage("Mã pin sai");
            }
        });
        binding.textResendOtp.setOnClickListener(view1 -> {
            if (info != null) {
                viewModel.ReSendPINcode(info.getNguoiDungMobileID().toString());
            }
        });
        binding.layoutHeader.imageBack.setOnClickListener(view12 -> {
            preference.setOtp(false);
            finish();
        });

        binding.textChangPhone.setOnClickListener(v -> {
            preference.setOtp(false);
            preference.setUser(null);
            Intent intent = new Intent(InputOtpActivity.this, CheckPhoneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void checkEnableButton(String gg) {
        AppUtils.enableButton(gg.length() == 4, binding.buttonEnd, this);
    }

    private void onCheckCode(String gg) {
        textID = gg;
        if (gg.equals(info.getMaPIN().toString())) {
            showAction();
            this.finish();
        }
    }

    public void showAction(){
        Intent intent;
        switch (toMain) {
            case "checkPhone":
                preference.setOtp(true);
                Gson gson = new Gson();
                String json = gson.toJson(info);
                preference.setUser(json);
                intent = new Intent(this, CheckLoginByIDPassActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Object", info);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case "newCustomer":
                preference.setLogin(true);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case "forgetPass":
                intent = new Intent(this, ForgetPassActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        info = (NewCustomer) bundle.getSerializable("Object");
        toMain = bundle.getString("FromCreate");
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemResendPin.observe(this, newCustomer -> {
            this.info = newCustomer;
            if (newCustomer != null) {
                showToast(info.getMaPIN().toString());
                AppUtils.createNotification(this, info.getMaPIN().toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

}
