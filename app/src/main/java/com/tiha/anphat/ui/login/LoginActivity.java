package com.tiha.anphat.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.UserLoginInfo;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.aes.AESUtils;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    EditText inputUserName, inputPassword;
    Button buttonLogin;
    CheckBox checkBox;
    LoginPresenter loginPresenter;
    AppPreference appPreference;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        loginPresenter = new LoginPresenter(this);
        showProgressDialog(true);
        checkBox = findViewById(R.id.checkboxRememberMe);
        inputUserName = findViewById(R.id.inputUserName);
        inputPassword = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        checkValidate();
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
        inputUserName.addTextChangedListener(imm);
        inputPassword.addTextChangedListener(imm);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.CheckLogin(inputUserName.getText().toString(), inputPassword.getText().toString());
                showProgressDialog(true);
            }
        });
    }

    public void checkValidate() {
        if (AppUtils.isValidateUsername(inputUserName.getText().toString()) && AppUtils.isValidateUsername(inputPassword.getText().toString())) {
            buttonLogin.setEnabled(true);
            buttonLogin.setTextColor(getResources().getColor(R.color.White));
            buttonLogin.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        } else {
            buttonLogin.setEnabled(false);
            buttonLogin.setTextColor(getResources().getColor(R.color.text_disable));
            buttonLogin.setBackgroundResource(R.drawable.bg_button_light);
        }
    }

    @Override
    public void initData() {
        appPreference = new AppPreference(this);
        AESUtils aesUtils = new AESUtils();
        String userName = "";
        try {
            userName = aesUtils.decrypt(appPreference.getUserName());
        } catch (Exception ignored) {
        }
        String passWord = "";
        try {
            passWord = aesUtils.decrypt(appPreference.getPassWord());
        } catch (Exception ignored) {
        }
        inputUserName.setText(userName);
        inputPassword.setText(passWord);

        showProgressDialog(false);
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void showProgressDialog(boolean isShow) {
        super.showProgressDialog(isShow);
    }


    @Override
    public void onCheckLoginSuccess(UserLoginInfo info) {
        if (checkBox.isChecked()){
            appPreference.setLogin(true);
        } else {
            appPreference.setLogin(false);
        }
        AESUtils aesUtils = new AESUtils();
        String userName = "";
        try {
            userName = aesUtils.encrypt(info.UserName);
        } catch (Exception ignored) {
        }
        String passWord = "";
        try {
            passWord = aesUtils.encrypt(inputPassword.getText().toString());
        } catch (Exception ignored) {
        }
        appPreference.setPassWord(passWord);
        appPreference.setUserName(userName);
        PublicVariables.userLoginInfo = info;
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        showProgressDialog(false);

    }

    @Override
    public void onCheckLoginError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }
}
