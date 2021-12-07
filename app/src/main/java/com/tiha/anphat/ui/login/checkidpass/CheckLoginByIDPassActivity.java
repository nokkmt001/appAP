package com.tiha.anphat.ui.login.checkidpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.tiha.anphat.databinding.ActivityCheckLoginIdPassBinding;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.login.checkphone.CheckPhoneActivity;
import com.tiha.anphat.ui.login.forgetpass.ForgetPassActivity;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.aes.AESUtils;

import java.util.Objects;

public class CheckLoginByIDPassActivity extends BaseActivity implements LoginIDPassContract.View {
    private ActivityCheckLoginIdPassBinding binding;
    LoginIDPassPresenter presenter;
    AppPreference preference;
    NewCustomer info = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_login_id_pass;
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        binding = ActivityCheckLoginIdPassBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.enableButton(false, binding.buttonLogin,this);
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
            presenter = new LoginIDPassPresenter(CheckLoginByIDPassActivity.this);
            presenter.CheckLoginByIDPass(info.getNguoiDungMobileID().toString(), Objects.requireNonNull(binding.inputPassword.getText()).toString());
            showProgressDialog(true);
        });
        binding.textForgetPass.setOnClickListener(view1 -> {
            if (info==null) return;
            Intent intent = new Intent(CheckLoginByIDPassActivity.this, ForgetPassActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Object", info);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.imageLogo.setOnClickListener(view13 -> linkWed());
    }

    private void linkWed(){
        String url =  getResources().getString(R.string.url_ap);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void disableButton(){
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
        info = (NewCustomer) bundle.getSerializable("Object") ;
        if (info!=null){
            onLoadTitle(info);
        }
    }

    @SuppressLint("SetTextI18n")
    private void onLoadTitle(NewCustomer info){
        binding.textUserName.setText(getString(R.string.hello)+info.getHoTen());
        binding.textPhone.setText(info.getSoDienThoai());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckLoginByIDPassSuccess(NewCustomer info) {
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
            passWord = aesUtils.encrypt(Objects.requireNonNull(binding.inputPassword.getText()).toString());
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
    public void onCheckLoginByIDPassError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }
}
