package com.anphat.supplier.ui.login.register;

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
import com.anphat.supplier.databinding.ActivityCreateNewCustomerBinding;
import com.anphat.supplier.ui.login.inputotp.InputOtpActivity;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.aes.AESUtils;

import java.util.Date;
import java.util.Objects;

public class CreateNewCustomerActivity extends BaseMVVMActivity<ActivityCreateNewCustomerBinding, LoginViewModel>  {
    Date date = new Date(System.currentTimeMillis());
    AppPreference preference;
    String sdt = "";

    @Override
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivityCreateNewCustomerBinding getViewBinding() {
        return ActivityCreateNewCustomerBinding.inflate(getLayoutInflater());
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
        binding.layoutHeaderCreate.imageBack.setOnClickListener(view12 -> finish());
        binding.layoutHeaderCreate.textTitle.setText(R.string.create_user_title);
        binding.inputUserName.addTextChangedListener(imm);
        binding.inputSdt.addTextChangedListener(imm);
        binding.inputAddress.addTextChangedListener(imm);
        binding.inputPassword.addTextChangedListener(imm);
        binding.inputConfirm.addTextChangedListener(imm);
        binding.buttonLogin.setOnClickListener(view1 -> {
            if (binding.inputConfirm.getText().toString().equals(binding.inputPassword.getText().toString())) {
                NewCustomer item = new NewCustomer();
                item.setHoTen(Objects.requireNonNull(binding.inputUserName.getText()).toString());
                item.setSoDienThoai(Objects.requireNonNull(binding.inputSdt.getText()).toString());
                item.setDiaChi(Objects.requireNonNull(binding.inputAddress.getText()).toString());
                item.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd HH:mm:ss"));
                item.setPassword(Objects.requireNonNull(binding.inputPassword.getText()).toString());
                item.setNgayGio(AppUtils.formatDateToString(date, "yyyy-MM-dd HH:mm:ss"));
                viewModel.InsertNewCustomer(item);
                showProgressDialog(true);
            } else {
                showMessage("Vui lòng nhập đúng mật khẩu");
            }
        });
    }

    public void checkValidate() {
        if (Objects.requireNonNull(binding.inputUserName.getText()).toString().length() != 0 &&
                Objects.requireNonNull(binding.inputSdt.getText()).toString().length() != 0 &&
                Objects.requireNonNull(binding.inputPassword.getText()).toString().length() != 0 &&
                Objects.requireNonNull(binding.inputConfirm.getText()).toString().length() != 0) {
            AppUtils.enableButton(true, binding.buttonLogin, this);
        } else AppUtils.enableButton(false, binding.buttonLogin, this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        sdt = bundle.getString("Phone");
        binding.inputSdt.setText(sdt);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemInsert.observe(this,info -> {
            if (info!=null) {
                Gson gson = new Gson();
                String json = gson.toJson(info);
                preference.setUser(json);
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
                Intent intent = new Intent(this, InputOtpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Object", info);
                bundle.putString("FromCreate", "newCustomer");
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                showToast(info.getMaPIN().toString());
                AppUtils.createNotification(this, info.getMaPIN().toString());
            }
            showProgressDialog(false);

        });
    }

    @Override
    public void onClick(View view) {

    }
}
