package com.tiha.admin.anphat.ui.employee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiha.admin.anphat.data.entities.EmployeeInfo;
import com.tiha.admin.anphat.databinding.ItemEmployeeBinding;
import com.tiha.admin.anphat.ui.base.BaseTestAdapter;

public class EmployeeAdapter extends BaseTestAdapter<EmployeeInfo,ItemEmployeeBinding> {
    ItemEmployeeBinding bd;

    @Override
    public void setupViews(View itemView, EmployeeInfo item, int position) {
        bd.textUserName.setText(item.EmployeeName);
        bd.textSDT.setText(item.DTNoio);
        bd.textPhongBan.setText(item.DepartmentID);
    }

    @Override
    public ItemEmployeeBinding getViewBinding(ViewGroup parent, int viewType) {
        return bd = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
