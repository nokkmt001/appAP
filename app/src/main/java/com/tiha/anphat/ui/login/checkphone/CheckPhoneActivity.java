package com.tiha.anphat.ui.login.checkphone;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.databinding.ActivityCheckPhoneBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.login.register.CreateNewCustomerActivity;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.CommonUtils;

import java.util.Objects;

public class CheckPhoneActivity extends BaseActivity implements CheckPhoneContract.View {
    ActivityCheckPhoneBinding binding;
    CheckPhonePresenter presenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_check_phone;
    }

    @Override
    protected void onInit() {
        binding = ActivityCheckPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.enableButton(false,binding.buttonLogin);
        binding.inputNumberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (AppUtils.isValidPhoneNumber(editable)){
                    AppUtils.enableButton(true,binding.buttonLogin);
                } else AppUtils.enableButton(false,binding.buttonLogin);
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
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckPhoneNumberSuccess() {
        showProgressDialog(false);
    }

    @Override
    public void onCheckPhoneNumberError(String error) {
        showProgressDialog(false);
        CommonUtils.showMessageError(this,error);
    }
}
