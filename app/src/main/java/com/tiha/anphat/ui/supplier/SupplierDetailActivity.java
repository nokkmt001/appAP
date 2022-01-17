package com.tiha.anphat.ui.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tiha.anphat.data.entities.SupplierInfo;
import com.tiha.anphat.data.network.supplier.ISupplierModel;
import com.tiha.anphat.data.network.supplier.SupplierModel;
import com.tiha.anphat.databinding.ActivityDetailSupplierBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;

public class SupplierDetailActivity extends BaseTestActivity<ActivityDetailSupplierBinding> {
    ActivityDetailSupplierBinding bd;
    SupplierModel model;

    @Override
    public ActivityDetailSupplierBinding getViewBinding() {
        return bd = ActivityDetailSupplierBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        bd.imageBack.setOnClickListener(v -> finish());
        bd.imageEdit.setOnClickListener(v -> {
            Intent intent = new Intent(SupplierDetailActivity.this, UpdateSupplierActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
//        bd.
    }

    @Override
    protected void initData() {
        model = new SupplierModel();
        Bundle bundle = getIntent().getExtras();
        String supplierID = bundle.getString("supplierID");

        model.GetSupplier(supplierID, new ISupplierModel.ISupplierFinish() {
            @Override
            public void onSuccess(SupplierInfo info) {
                setData(info);
            }

            @Override
            public void onError(String error) {
                showMessage(error);
            }
        });
    }

    private void setData(SupplierInfo item) {
        bd.tvHoTen.setText(item.getCompany_Name());
        bd.tvSoDienThoai.setText(item.getPhone());
        bd.tvEmail.setText(item.getEmailGD());
        bd.tvDiaChi.setText(item.getDiachighiHD());
//        bd.tvGioiTinh.setText(item.GioiTinh ? "Nam" : "Nữ");
        bd.tvLienHe.setText(item.getContact_Name());
    }

    @Override
    public void onClick(View v) {

    }
}
