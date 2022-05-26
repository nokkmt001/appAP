package com.anphat.supplier.ui.login.checkidpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.data.entities.condition.CustomerLoginCondition;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.login.inputotp.InputOtpActivity;
import com.anphat.supplier.viewmodel.LoginViewModel;
import com.google.gson.Gson;
import com.anphat.supplier.databinding.ActivityCheckLoginIdPassBinding;
import com.anphat.supplier.ui.main.MainActivity;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.aes.AESUtils;

import java.util.Objects;

public class CheckLoginByIDPassActivity extends BaseMVVMActivity<ActivityCheckLoginIdPassBinding, LoginViewModel> {
    AppPreference preference;
    NewCustomer info = null;

    @Override
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivityCheckLoginIdPassBinding getViewBinding() {
        return ActivityCheckLoginIdPassBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        AppUtils.enableButton(false, binding.buttonLogin, this);
        TextWatcher imm = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkValidate();
            }
        };
        binding.inputID.addTextChangedListener(imm);
        binding.inputPassword.addTextChangedListener(imm);
        binding.buttonLogin.setOnClickListener(view12 -> {
            showProgressDialog(true);
            CustomerLoginCondition condition = new CustomerLoginCondition();
            condition.NguoiDungMobielID = info.getNguoiDungMobileID().toString();
            condition.Password = Objects.requireNonNull(binding.inputPassword.getText()).toString();
            viewModel.GetLoginByIDPassWord(condition);
        });
        binding.textForgetPass.setOnClickListener(view1 -> {
            if (info == null) return;
            Intent intent = new Intent(CheckLoginByIDPassActivity.this, InputOtpActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Object", info);
            bundle.putString("FromCreate", "forgetPass");
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.imageLogo.setOnClickListener(view13 -> linkWed());
        binding.textChangPhone.setOnClickListener(v -> {
            preference.setOtp(false);
            preference.setUser(null);
            Intent intent = new Intent(CheckLoginByIDPassActivity.this, CheckPhoneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void linkWed() {
        String url = getResources().getString(R.string.url_ap);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void disableButton() {
        binding.buttonLogin.setEnabled(false);
        binding.buttonLogin.setTextColor(getResources().getColor(R.color.text_disable));
        binding.buttonLogin.setBackgroundResource(R.drawable.bg_button_light);
    }

    public void checkValidate() {
        if (AppUtils.isValidateUsername(Objects.requireNonNull(binding.inputPassword.getText()).toString())) {
            binding.buttonLogin.setEnabled(true);
            binding.buttonLogin.setTextColor(getResources().getColor(R.color.White));
            binding.buttonLogin.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        } else {
            disableButton();
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        info = (NewCustomer) bundle.getSerializable("Object");
        if (info != null) {
            onLoadTitle(info);
        }
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemCheckIDPass.observe(this, info -> {
            if (info != null) {
                this.info = info;
                insertFCM();
                Gson gson = new Gson();
                String json = gson.toJson(info);
                preference.setUser(json);
                preference.setLogin(true);
                AppPreference.saveUser(info);
                AESUtils aesUtils = new AESUtils();
                String userID = "";
                try {
                    userID = aesUtils.encrypt(Objects.requireNonNull(info.getNguoiDungMobileID().toString()));
                } catch (Exception ignored) {
                }
                String passWord = "";
                try {
                    passWord = aesUtils.encrypt(Objects.requireNonNull(binding.inputPassword.getText()).toString());
                } catch (Exception ignored) {
                }
                preference.setPassWord(passWord);
                preference.setUserID(userID);
                PublicVariables.UserInfo = info;
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            showProgressDialog(false);

        });

        viewModel.mItemInsertFCM.observe(this, fcmMobileInfo -> {
            if (fcmMobileInfo != null) {
                showToast("insert fcm success");
            }
        });

    }

    public void insertFCM() {
        FCMMobileInfo fcmMobileInfo = new FCMMobileInfo();
        fcmMobileInfo.HeDieuHanh = "ANDROID";
        fcmMobileInfo.SoDienThoai = info.getSoDienThoai();
        fcmMobileInfo.Token = PublicVariables.token;
        viewModel.insertFCM(fcmMobileInfo);
    }

    @SuppressLint("SetTextI18n")
    private void onLoadTitle(NewCustomer info) {
        binding.textUserName.setText(getString(R.string.hello) + "  " + info.getHoTen());
        binding.textPhone.setText(info.getSoDienThoai());
    }

    @Override
    public void onClick(View view) {
    }

}
