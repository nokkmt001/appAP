package com.tiha.anphat.ui.login.inputotp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.databinding.ActivityOtpBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.tiha.anphat.utils.AppConstants;

import java.util.Timer;
import java.util.TimerTask;

public class InputOtpActivity extends BaseActivity implements ResendOtpContract.View {
    ActivityOtpBinding binding;
    private Timer timer;
    NewCustomer info;
    ResendOtpPresenter presenter;
    String textID;
    AppPreference preference;

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
        binding.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onCheckCode(editable.toString());
                    }
                }, AppConstants.DELAY_FIND_DATA);
            }
        });
        binding.buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textID.equals(info.getMaPIN().toString())) {
                    Intent intent = new Intent(InputOtpActivity.this, CheckLoginByIDPassActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Object", info);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        binding.textResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info != null) {
                    presenter.ResendOtp(info.getNguoiDungMobileID());
                }
            }
        });
        binding.layoutHeader.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.setOtp(false);
                finish();
            }
        });
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
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        info = (NewCustomer) bundle.getSerializable("Object");
        showInfo(info.getMaPIN().toString());
    }

    @Override
    public void onClick(View view) {}

    @Override
    public void onResendOtpSuccess(NewCustomer info) {
        this.info = info;
    }

    @Override
    public void onResendOtpError(String error) {
        showMessage(error);
    }
}
