package com.tiha.anphat.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.tiha.anphat.MainActivity;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.NguoiDungInfo;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.custom.DateDialogAdapter;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.aes.AESUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LoginActivity extends BaseActivity implements LoginContract.View {
    EditText inputUserName, inputPassword;
    Button buttonLogin;
    CheckBox checkboxRememberMe;
    ImageView imgLogo;
    LoginPresenter loginPresenter;
    AppPreference appPreference;
    Dialog progressDialog;
    DateDialogAdapter adapterDateDialog;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public void onInit() {
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
    public void onConfigToolbar() {

    }

    @Override
    public void onLoadData() {
        appPreference = new AppPreference(this);
        loginPresenter = new LoginPresenter(LoginActivity.this);
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

    private NguoiDungInfo getNguoiDung() {
        NguoiDungInfo nguoiDungInfo = new NguoiDungInfo();
//        nguoiDungInfo.setUserName(etTenDangNhap.getText().toString());
//        nguoiDungInfo.setPassword(etMatKhau.getText().toString());
        return nguoiDungInfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                NguoiDungInfo nguoiDungInfo = getNguoiDung();
//                //Set server name
//                AppConstants.SERVER_NAME = etServerNameCTy.getText().toString();
//                AppConstants.URL_SERVER = "http://" + AppConstants.SERVER_NAME;
////                loginPresenter.CheckLogin(nguoiDungInfo.getUserName(), nguoiDungInfo.getPassword(), AppConstants.SERVER_NAME);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
//            case R.id.imgLogo:
////                String url = "http://" + getResources().getString(R.string.app_website_customer);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
////                intent.setData(Uri.parse(url));
//                startActivity(intent);
//                break;
//            case R.id.etNgayLamViec:
//                try {
//                    adapterDateDialog = new DateDialogAdapter(v, etNgayLamViec.getText().toString());
//                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    adapterDateDialog.show(ft, "DatePicker");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
            default:
                break;
        }
    }

    @Override
    public void onLoginSuccess(NguoiDungInfo nguoiDungInfo) {
//        PublicVariables.nguoiDungInfo = nguoiDungInfo;
//        AESUtils aesUtils = new AESUtils();
//        String userName = "";
//        try {
//            userName = aesUtils.encrypt(etTenDangNhap.getText().toString());
//        } catch (Exception e) {
//        }
//        String passWord = "";
//        try {
//            passWord = aesUtils.encrypt(etMatKhau.getText().toString());
//        } catch (Exception e) {
//        }
//        appPreference.setLogin(true);
//        appPreference.setTenDangNhap(userName);
//        appPreference.setMatKhau(passWord);
////        appPreference.setChiNhanh(nguoiDungInfo.getChiNhanh());
//        appPreference.setServerName(etServerNameCTy.getText().toString());
//        PublicVariables.NgayLamViec = etNgayLamViec.getText().toString();
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onLoginError(String error) {
        error = error.isEmpty() ? "Đăng nhập thất bại, hãy thử lại!" : error;
        showMessage("LỖI", error);
    }

    @Override
    public void showProgressDialog(boolean isShow) {
        super.showProgressDialog(isShow);
    }


}
