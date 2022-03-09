package com.tiha.anphatsu.ui.login.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.databinding.ActivityCreateNewCustomerBinding;
import com.tiha.anphatsu.ui.base.BaseActivity;
import com.tiha.anphatsu.ui.login.inputotp.InputOtpActivity;
import com.tiha.anphatsu.utils.AppUtils;
import com.tiha.anphatsu.utils.CommonUtils;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.aes.AESUtils;

import java.util.Date;
import java.util.Objects;

public class CreateNewCustomerActivity extends BaseActivity implements CreateNewContract.View {
    ActivityCreateNewCustomerBinding binding;
    CreateNewPresenter presenter;
    Date date = new Date(System.currentTimeMillis());
    AppPreference preference;
    String sdt = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_new_customer;
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        binding = ActivityCreateNewCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
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
                presenter = new CreateNewPresenter(CreateNewCustomerActivity.this);
                presenter.InsertNewCustomer(item);
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
    public void onClick(View view) {

    }

    @Override
    public void onInsertNewCustomerSuccess(NewCustomer info) {
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
        showProgressDialog(false);
        Intent intent = new Intent(this, InputOtpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object", info);
        bundle.putString("FromCreate","gg");
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        AppUtils.createNotification(this,info.getMaPIN().toString());
    }

    @Override
    public void onInsertNewCustomerError(String error) {
        CommonUtils.showMessageError(this, error);
        showProgressDialog(false);
    }
}
