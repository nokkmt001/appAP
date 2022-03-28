package com.anphat.supplier.ui.login.checkidpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.ui.splash.FcmContract;
import com.anphat.supplier.ui.splash.FcmPresenter;
import com.google.gson.Gson;
import com.anphat.supplier.databinding.ActivityCheckLoginIdPassBinding;
import com.anphat.supplier.main.MainActivity;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.ui.base.BaseActivity;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.ui.login.forgetpass.ForgetPassActivity;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.aes.AESUtils;

import java.util.Objects;

public class CheckLoginByIDPassActivity extends BaseActivity implements LoginIDPassContract.View , FcmContract.View {
    private ActivityCheckLoginIdPassBinding binding;
    LoginIDPassPresenter presenter;
    AppPreference preference;
    NewCustomer info = null;
    FcmPresenter presenterFcm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_login_id_pass;
    }

    @Override
    protected void initView() {
        presenterFcm = new FcmPresenter(this);
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
            showProgressDialog(true);
            presenter = new LoginIDPassPresenter(CheckLoginByIDPassActivity.this);
            presenter.CheckLoginByIDPass(info.getNguoiDungMobileID().toString(), Objects.requireNonNull(binding.inputPassword.getText()).toString());
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
        binding.textChangPhone.setOnClickListener(v -> {
            preference.setOtp(false);
            preference.setUser(null);
            Intent intent = new Intent(CheckLoginByIDPassActivity.this, CheckPhoneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
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
        presenterFcm.getFCM(PublicVariables.token);
        Gson gson = new Gson();
        String json = gson.toJson(info);
        preference.setUser(json);
        preference.setLogin(true);
        preference.saveUser(info);
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
        finish();
    }

    @Override
    public void onCheckLoginByIDPassError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }

    @Override
    public void onInsertFCMSuccess(FCMMobileInfo info) {
        Toast.makeText( this,"insert fcm success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertFCMError(String error) {
        Toast.makeText( this,"insert fcm error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFCMSuccess(FCMMobileInfo info) {
        Toast.makeText( this,"get fcm success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFCMError(String error) {
        FCMMobileInfo fcmMobileInfo = new FCMMobileInfo();
        fcmMobileInfo.HeDieuHanh = "ANDROID";
        fcmMobileInfo.SoDienThoai = info.getSoDienThoai();
        fcmMobileInfo.Token = PublicVariables.token;
        presenterFcm.insertFCM(fcmMobileInfo);
    }
}
