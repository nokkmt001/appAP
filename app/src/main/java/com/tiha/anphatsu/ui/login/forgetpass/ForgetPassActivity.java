package com.tiha.anphatsu.ui.login.forgetpass;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.databinding.ActivityForgetPassBinding;
import com.tiha.anphatsu.main.MainActivity;
import com.tiha.anphatsu.ui.base.BaseTestActivity;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.aes.AESUtils;

import java.util.Objects;

public class ForgetPassActivity extends BaseTestActivity<ActivityForgetPassBinding> implements ForgetPassContract.View {
    ActivityForgetPassBinding bd;
    ForgetPassPresenter presenter;
    NewCustomer info = null;
    AppPreference preference;

    @Override
    public ActivityForgetPassBinding getViewBinding() {
        return bd = ActivityForgetPassBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        disableButton();
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
        bd.inputPassword.addTextChangedListener(imm);
        bd.inputPasswordAgain.addTextChangedListener(imm);
        bd.buttonLogin.setOnClickListener(v -> {
            if (!Objects.requireNonNull(bd.inputPassword.getText()).toString().equals(Objects.requireNonNull(bd.inputPasswordAgain.getText()).toString())) {
                showMessage("Bạn phải nhập lại mật khẩu đúng!");
            } else {
                showProgressDialog(true);
                info.setPassword(Objects.requireNonNull(bd.inputPassword.getText()).toString());
                presenter.UpdateCustomer(info);
            }
        });
    }

    public void checkValidate() {
        if (!TextUtils.isEmpty(bd.inputPassword.getText()) && !TextUtils.isEmpty(bd.inputPasswordAgain.getText())) {
            bd.buttonLogin.setEnabled(true);
            bd.buttonLogin.setTextColor(getResources().getColor(R.color.White));
            bd.buttonLogin.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        } else {
            disableButton();
        }
    }

    private void disableButton() {
        bd.buttonLogin.setEnabled(false);
        bd.buttonLogin.setTextColor(getResources().getColor(R.color.text_disable));
        bd.buttonLogin.setBackgroundResource(R.drawable.bg_button_light);
    }

    @Override
    protected void initData() {
        presenter = new ForgetPassPresenter(this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        info = (NewCustomer) bundle.getSerializable("Object");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onUpdateCustomerSuccess(NewCustomer info) {
        Gson gson = new Gson();
        String json = gson.toJson(info);
        preference.setUser(json);
        preference.setLogin(true);
        AESUtils aesUtils = new AESUtils();
        String userID = "";
        try {
            userID = aesUtils.encrypt(Objects.requireNonNull(info.getNguoiDungMobileID().toString()));
        } catch (Exception ignored) {
        }
        String passWord = "";
        try {
            passWord = aesUtils.encrypt(Objects.requireNonNull(bd.inputPassword.getText()).toString());
        } catch (Exception ignored) {
        }
        preference.setPassWord(passWord);
        preference.setUserID(userID);
        PublicVariables.UserInfo = info;
        showProgressDialog(false);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onUpdateCustomerError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }
}
