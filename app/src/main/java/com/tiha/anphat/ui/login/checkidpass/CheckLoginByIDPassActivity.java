package com.tiha.anphat.ui.login.checkidpass;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.databinding.ActivityCheckLoginIdPassBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.login.checkphone.CheckPhoneActivity;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.aes.AESUtils;

import java.util.Objects;

public class CheckLoginByIDPassActivity extends BaseActivity implements LoginIDPassContract.View {
    private ActivityCheckLoginIdPassBinding binding;
    LoginIDPassPresenter presenter;
    AppPreference preference;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_check_login_id_pass;
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        binding = ActivityCheckLoginIdPassBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppUtils.enableButton(false, binding.buttonLogin);
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
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter = new LoginIDPassPresenter(CheckLoginByIDPassActivity.this);
                presenter.CheckLoginByIDPass(Objects.requireNonNull(binding.inputID.getText()).toString(), Objects.requireNonNull(binding.inputPassword.getText()).toString());
                showProgressDialog(true);
            }
        });
        binding.textForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckLoginByIDPassActivity.this, CheckPhoneActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void checkValidate() {
        if (AppUtils.isValidateUsername(Objects.requireNonNull(binding.inputID.getText()).toString()) && AppUtils.isValidateUsername(Objects.requireNonNull(binding.inputPassword.getText()).toString())) {
            binding.buttonLogin.setEnabled(true);
            binding.buttonLogin.setTextColor(getResources().getColor(R.color.White));
            binding.buttonLogin.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        } else {
            binding.buttonLogin.setEnabled(false);
            binding.buttonLogin.setTextColor(getResources().getColor(R.color.text_disable));
            binding.buttonLogin.setBackgroundResource(R.drawable.bg_button_light);
        }
    }

    @Override
    protected void onLoadData() {
        AESUtils aesUtils = new AESUtils();
        try {
            binding.inputID.setText(aesUtils.decrypt(preference.getUserID()));
            binding.inputPassword.setText(aesUtils.decrypt(preference.getPassWord()));
        } catch (Exception ignored) {
        }

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
            userID = aesUtils.encrypt(Objects.requireNonNull(binding.inputID.getText()).toString());
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
        CommonUtils.showMessageError(this, error);
    }
}
