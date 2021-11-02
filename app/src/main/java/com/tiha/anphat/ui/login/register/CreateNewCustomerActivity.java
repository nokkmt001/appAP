package com.tiha.anphat.ui.login.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.databinding.ActivityCreateNewCustomerBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.PublicVariables;

import java.util.Date;
import java.util.Objects;

public class CreateNewCustomerActivity extends BaseActivity implements CreateNewContract.View {
    ActivityCreateNewCustomerBinding binding;
    CreateNewPresenter presenter;
    Date date = new Date(System.currentTimeMillis());

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_new_customer;
    }

    @Override
    protected void initView() {
        binding = ActivityCreateNewCustomerBinding.inflate(getLayoutInflater());
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
        binding.inputUserName.addTextChangedListener(imm);
        binding.inputSdt.addTextChangedListener(imm);
        binding.inputPassword.addTextChangedListener(imm);
        binding.inputPin.addTextChangedListener(imm);
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewCustomer item = new NewCustomer();
                item.setHoTen(Objects.requireNonNull(binding.inputUserName.getText()).toString());
                item.setSoDienThoai(Objects.requireNonNull(binding.inputSdt.getText()).toString());
                item.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd HH:mm:ss"));
                item.setMaPIN(Integer.valueOf(Objects.requireNonNull(binding.inputPin.getText()).toString()));
                item.setPassword(Objects.requireNonNull(binding.inputPassword.getText()).toString());
                item.setNgayGio(AppUtils.formatDateToString(date, "yyyy-MM-dd HH:mm:ss"));
                item.setDiaChi("");
                presenter = new CreateNewPresenter(CreateNewCustomerActivity.this);
                presenter.InsertNewCustomer(item);
            }
        });
    }

    public void checkValidate() {
        if (Objects.requireNonNull(binding.inputUserName.getText()).toString().length() != 0 && Objects.requireNonNull(binding.inputSdt.getText()).toString().length() != 0 &&
                Objects.requireNonNull(binding.inputPassword.getText()).toString().length() != 0 && Objects.requireNonNull(binding.inputPin.getText()).toString().length() != 0) {
            AppUtils.enableButton(true, binding.buttonLogin);
        } else AppUtils.enableButton(false, binding.buttonLogin);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInsertNewCustomerSuccess(NewCustomer info) {
        PublicVariables.UserInfo = info;
    }

    @Override
    public void onInsertNewCustomerError(String error) {
        CommonUtils.showMessageError(this, error);
    }
}
