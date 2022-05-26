package com.anphat.supplier.ui.login.forgetpass;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.condition.ForGetPassCondition;
import com.anphat.supplier.databinding.ActivityChangePassBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.base.SearchMain;
import com.anphat.supplier.ui.main.MainActivity;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.aes.AESUtils;
import com.anphat.supplier.viewmodel.LoginViewModel;
import com.google.gson.Gson;

import java.util.Objects;

public class ChangePassActivity extends BaseMVVMActivity<ActivityChangePassBinding, LoginViewModel> {
    NewCustomer info = null;
    AppPreference preference;

    @Override
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivityChangePassBinding getViewBinding() {
        return ActivityChangePassBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        disableButton();
        TextWatcher imm = new SearchMain() {
            @Override
            protected void onAfterChanged(String text) {
                checkValidate();
            }
        };

        binding.inputPassword.addTextChangedListener(imm);
        binding.inputPasswordNew.addTextChangedListener(imm);
        binding.inputPasswordNewAgain.addTextChangedListener(imm);

        binding.buttonLogin.setOnClickListener(v -> {
            if (!Objects.requireNonNull(binding.inputPasswordNew.getText()).toString().equals(Objects.requireNonNull(binding.inputPasswordNewAgain.getText()).toString())) {
                showMessage("Bạn phải nhập lại mật khẩu mới đúng!");
                return;
            }

            if (binding.inputPasswordNew.getText().toString() == binding.inputPassword.getText().toString()) {
                showMessage("Mật khẩu mới của bạn không được trùng với mật khẩu cũ!");
                return;
            }

            showProgressDialog(true);
            info.setPassword(Objects.requireNonNull(binding.inputPassword.getText()).toString());
            viewModel.changePassWord(getItem());

        });

        binding.layoutHeader.textTitle.setText(R.string.title_change_pass);
    }

    public ForGetPassCondition getItem() {
        ForGetPassCondition item = new ForGetPassCondition();
        item.NguoiDungMobielID = info.getNguoiDungMobileID();
        item.MatKhauCu = Objects.requireNonNull(binding.inputPassword.getText()).toString();
        item.MatKhauMoi = Objects.requireNonNull(binding.inputPasswordNew.getText()).toString();
        item.Loai = "DoiMatKhau";
        return item;
    }

    public void checkValidate() {
        if (!TextUtils.isEmpty(binding.inputPassword.getText()) && !TextUtils.isEmpty(binding.inputPasswordNew.getText()) && !TextUtils.isEmpty(binding.inputPasswordNewAgain.getText())) {
            binding.buttonLogin.setEnabled(true);
            binding.buttonLogin.setTextColor(getResources().getColor(R.color.White));
            binding.buttonLogin.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        } else {
            disableButton();
        }
    }

    private void disableButton() {
        binding.buttonLogin.setEnabled(false);
        binding.buttonLogin.setTextColor(getResources().getColor(R.color.text_disable));
        binding.buttonLogin.setBackgroundResource(R.drawable.bg_button_light);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        info = (NewCustomer) bundle.getSerializable("OBJECT");
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemChangePass.observe(this, result -> {
            if (result) {
                showToast("Cập nhật mật khẩu thành công.");
                onUpdateCustomerSuccess(info);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    //    @Override
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
            passWord = aesUtils.encrypt(Objects.requireNonNull(binding.inputPasswordNew.getText()).toString());
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

}
