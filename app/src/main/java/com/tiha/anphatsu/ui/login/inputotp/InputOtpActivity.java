package com.tiha.anphatsu.ui.login.inputotp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.databinding.ActivityOtpBinding;
import com.tiha.anphatsu.main.MainActivity;
import com.tiha.anphatsu.ui.base.BaseActivity;
import com.tiha.anphatsu.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.tiha.anphatsu.ui.login.checkphone.CheckPhoneActivity;
import com.tiha.anphatsu.utils.AppUtils;

public class InputOtpActivity extends BaseActivity implements ResendOtpContract.View {
    ActivityOtpBinding binding;
    NewCustomer info;
    ResendOtpPresenter presenter;
    String textID = "";
    AppPreference preference;
    String toMain = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    protected void initView() {
        presenter = new ResendOtpPresenter(this);
        preference = new AppPreference(this);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.enableButton(false,binding.buttonEnd,this);
        binding.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(final Editable editable) {
                checkEnableButton(editable.toString());
                onCheckCode(editable.toString());
            }
        });

        binding.buttonEnd.setOnClickListener(view13 -> {
            if (textID.equals(info.getMaPIN().toString())) {
                if (toMain != null && toMain.length() > 0) {
                    preference.setLogin(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, CheckLoginByIDPassActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Object", info);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                this.finish();
            } else {
                showMessage("Mã pin sai");
            }
        });
        binding.textResendOtp.setOnClickListener(view1 -> {
            if (info != null) {
                presenter.ResendOtp(info.getNguoiDungMobileID());
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

    private void checkEnableButton(String gg){
        AppUtils.enableButton(gg.length() == 4,binding.buttonEnd,this);
    }

    private void onCheckCode(String gg) {
        textID = gg;
        if (gg.equals(info.getMaPIN().toString())) {
            preference.setOtp(true);
            Gson gson = new Gson();
            String json = gson.toJson(info);
            preference.setUser(json);
            Intent intent = new Intent(this, CheckLoginByIDPassActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Object", info);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        info = (NewCustomer) bundle.getSerializable("Object");
        toMain = bundle.getString("FromCreate");

    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onResendOtpSuccess(NewCustomer info) {
        this.info = info;
        AppUtils.createNotification(this, info.getMaPIN().toString());
    }

    @Override
    public void onResendOtpError(String error) {
        showMessage(error);
    }
}
