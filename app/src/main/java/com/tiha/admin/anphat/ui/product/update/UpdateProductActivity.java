package com.tiha.admin.anphat.ui.product.update;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.product.FullProductInfo;
import com.tiha.admin.anphat.databinding.ActivityUpdateProductBinding;
import com.tiha.admin.anphat.ui.base.BaseTestActivity;
import com.tiha.admin.anphat.utils.AppUtils;

public class UpdateProductActivity extends BaseTestActivity<ActivityUpdateProductBinding> implements UpdateProductContract.View {
    ActivityUpdateProductBinding bd;
    UpdateProductPresenter presenter;
    String id = "";
    FullProductInfo itemUpdate = new FullProductInfo();
    String imageBitMap = "";

    @Override
    public ActivityUpdateProductBinding getViewBinding() {
        return bd = ActivityUpdateProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        checkValidate(false);

        TextWatcher imm = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkValidate(true);
            }
        };

        bd.inputPrice.addTextChangedListener(imm);
        bd.inputNameProduct.addTextChangedListener(imm);
        bd.inputDescription.addTextChangedListener(imm);
        bd.inputShip.addTextChangedListener(imm);

        bd.butonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();
            }
        });

        bd.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseFile();
            }
        });
        bd.textProductID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(bd.textProductID.getText().toString());
            }
        });
    }

    private void checkUpdate() {
        if (TextUtils.isEmpty(bd.inputPrice.getText())) {
            showMessage(getString(R.string.title_empty_product_price));
            return;
        }
        if (TextUtils.isEmpty(bd.inputNameProduct.getText())) {
            showMessage(getString(R.string.title_empty_product_name));
            return;
        }
        itemUpdate.ChiPhiVanChuyen = bd.inputShip.getText().toString().equals("") ? 0 : Integer.parseInt(bd.inputShip.getText().toString());
        itemUpdate.Product_Name = bd.inputNameProduct.getText().toString();
        itemUpdate.Description = bd.inputDescription.getText().toString();
        itemUpdate.GiaBanLe = Integer.parseInt(bd.inputPrice.getText().toString());
        presenter.UpdateProduct(itemUpdate, imageBitMap);

    }

    public void checkValidate(Boolean isCheck) {
        if (isCheck) {
            bd.butonSave.setEnabled(true);
            bd.butonSave.setTextColor(getColor(R.color.White));
            bd.butonSave.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        } else {
            bd.butonSave.setEnabled(false);
            bd.butonSave.setTextColor(getColor(R.color.text_disable));
            bd.butonSave.setBackgroundResource(R.drawable.bg_button_light);
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("ProductID");
        if (bundle == null) return;
        if (id.equals("")) return;

        presenter = new UpdateProductPresenter(this);
        presenter.GetProduct(id);
    }

    @SuppressLint("SetTextI18n")
    public void setData(FullProductInfo info) {
        bd.textProductID.setText(info.Product_ID);
        bd.textUnit.setText(info.DonVitinh);
        bd.inputPrice.setText(info.GiaBanLe.toString());
        bd.inputNameProduct.setText(info.Product_Name);
        bd.inputDescription.setText(info.Description == null ? "" : info.Description);
        bd.inputShip.setText(info.ChiPhiVanChuyen == null ? "" : info.ChiPhiVanChuyen.toString());
    }

    private void clearData() {
        bd.textProductID.setText("");
        bd.textUnit.setText("");
        bd.inputPrice.setText("");
        bd.inputNameProduct.setText("");
        bd.inputDescription.setText("");
        bd.inputShip.setText("");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetProductSuccess(FullProductInfo info) {
        setData(info);
        itemUpdate = info;
    }

    @Override
    public void onGetProductError(String error) {
        showMessage(error);
    }

    @Override
    public void onUpdateProductSuccess(FullProductInfo info) {
        showInfo("Cập nhật sản phẩm thành công!");
    }

    @Override
    public void onUpdateProductError(String error) {
        showMessage(error);
    }

    public void ResultImageBitMap(Bitmap bitmap) {
        bd.imageView.setImageBitmap(bitmap);
        imageBitMap = AppUtils.formatBitMapToString(bitmap);
    }
}
