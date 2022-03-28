package com.anphat.supplier.ui.login.checkphone;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.databinding.ActivityCheckPhoneBinding;
import com.anphat.supplier.ui.base.BaseActivity;
import com.anphat.supplier.ui.login.inputotp.InputOtpActivity;
import com.anphat.supplier.ui.login.register.CreateNewCustomerActivity;
import com.anphat.supplier.utils.AppUtils;

import java.util.Objects;

public class CheckPhoneActivity extends BaseActivity implements CheckPhoneContract.View {
    ActivityCheckPhoneBinding binding;
    CheckPhonePresenter presenter;
    NewCustomer info;
    AppPreference preference;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_phone;
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        binding = ActivityCheckPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.enableButton(false, binding.buttonLogin, this);
        binding.inputNumberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (AppUtils.validateNumberPhone(editable.toString())) {
                    AppUtils.enableButton(true, binding.buttonLogin, CheckPhoneActivity.this);
                } else AppUtils.enableButton(false, binding.buttonLogin, CheckPhoneActivity.this);
            }
        });
        binding.layoutHeader.imageBack.setVisibility(View.GONE);
        binding.buttonLogin.setOnClickListener(view1 -> {
            presenter = new CheckPhonePresenter(CheckPhoneActivity.this);
            presenter.CheckPhoneNumber(Objects.requireNonNull(binding.inputNumberPhone.getText()).toString());
            showProgressDialog(true);
        });
        binding.textCreateNew.setOnClickListener(view12 -> {
            Intent intent = new Intent(CheckPhoneActivity.this, CreateNewCustomerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.layoutHeader.textTitle.setText(getResources().getText(R.string.login));
//        binding.layoutHeader.imageBack.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckPhoneNumberSuccess(NewCustomer info) {
        Gson gson = new Gson();
        String json = gson.toJson(info);
        preference.setUser(json);
        showProgressDialog(false);
        Intent intent = new Intent(this, InputOtpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object", info);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        AppUtils.createNotification(this, info.getMaPIN().toString());
    }

    @Override
    public void onCheckPhoneNumberError(String error) {
        showProgressDialog(false);
        Intent intent = new Intent(this, CreateNewCustomerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Phone", binding.inputNumberPhone.getText().toString());
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
