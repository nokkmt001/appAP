package com.anphat.supplier.ui.login.checkphone;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.viewmodel.LoginViewModel;
import com.google.gson.Gson;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.databinding.ActivityCheckPhoneBinding;
import com.anphat.supplier.ui.login.inputotp.InputOtpActivity;
import com.anphat.supplier.ui.login.register.CreateNewCustomerActivity;
import com.anphat.supplier.utils.AppUtils;

import java.util.Objects;

public class CheckPhoneActivity extends BaseMVVMActivity<ActivityCheckPhoneBinding, LoginViewModel> {
    NewCustomer info;
    AppPreference preference;

    @Override
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivityCheckPhoneBinding getViewBinding() {
        return ActivityCheckPhoneBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
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
            viewModel.CheckPhone(Objects.requireNonNull(binding.inputNumberPhone.getText()).toString());
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
    protected void onObserver() {
        super.onObserver();

        viewModel.mItemGetPhone.observe(this,info -> {
            if (info.Status==0){
                this.info = info.Data;
                Gson gson = new Gson();
                String json = gson.toJson(this.info);
                preference.setUser(json);
                Intent intent = new Intent(this, InputOtpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Object", this.info);
                bundle.putString("FromCreate","checkPhone");
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                showToast(this.info.getMaPIN().toString());
                AppUtils.createNotification(this, this.info.getMaPIN().toString());
            } else {
                Intent intent = new Intent(this, CreateNewCustomerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Phone", binding.inputNumberPhone.getText().toString());
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            showProgressDialog(false);
            finish();
        });
    }

}
