package com.tiha.anphat.ui.login.checkphone;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.databinding.ActivityCheckPhoneBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.login.inputotp.InputOtpActivity;
import com.tiha.anphat.ui.login.register.CreateNewCustomerActivity;
import com.tiha.anphat.utils.AppUtils;

import java.util.Objects;

public class CheckPhoneActivity extends BaseActivity implements CheckPhoneContract.View {
    ActivityCheckPhoneBinding binding;
    CheckPhonePresenter presenter;
    NewCustomer info;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_phone;
    }

    @Override
    protected void initView() {
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
                if (AppUtils.isValidPhoneNumber(editable)) {
                    AppUtils.enableButton(true, binding.buttonLogin, CheckPhoneActivity.this);
                } else AppUtils.enableButton(false, binding.buttonLogin, CheckPhoneActivity.this);
            }
        });
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter = new CheckPhonePresenter(CheckPhoneActivity.this);
                presenter.CheckPhoneNumber(Objects.requireNonNull(binding.inputNumberPhone.getText()).toString());
                showProgressDialog(true);
            }
        });
        binding.textCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckPhoneActivity.this, CreateNewCustomerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//        binding.layoutHeader..setText(getResources().getText(R.string.login));
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
        showProgressDialog(false);
        Intent intent = new Intent(this, InputOtpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object", info);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onCheckPhoneNumberError(String error) {
        showProgressDialog(false);
        Intent intent = new Intent(this, CreateNewCustomerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Phone",binding.inputNumberPhone.getText().toString());
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
