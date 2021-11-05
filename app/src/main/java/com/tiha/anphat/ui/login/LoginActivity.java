package com.tiha.anphat.ui.login;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.custom.DateDialogAdapter;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.aes.AESUtils;

public class LoginActivity extends BaseActivity {
    EditText inputUserName, inputPassword;
    Button buttonLogin;
    CheckBox checkboxRememberMe;
    ImageView imgLogo;
    LoginPresenter loginPresenter;
    AppPreference appPreference;
    Dialog progressDialog;
    DateDialogAdapter adapterDateDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        showProgressDialog(true);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        inputUserName = findViewById(R.id.inputUserName);
        inputPassword = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        checkValidate();
        TextWatcher imm = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { checkValidate(); }
        };
        inputUserName.addTextChangedListener(imm);
        inputPassword.addTextChangedListener(imm);
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
        try {
//            etTenDangNhap.setText(aesUtils.decrypt(appPreference.getTenDangNhap()));
        } catch (Exception e) {
        }
//        etServerNameCTy.setText(appPreference.getServerName());


//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        PublicVariables.NgayLamViec = simpleDateFormat.format(c.getTime());
//        etNgayLamViec.setText(PublicVariables.NgayLamViec);
        showProgressDialog(false);
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void showProgressDialog(boolean isShow) {
        super.showProgressDialog(isShow);
    }


}
