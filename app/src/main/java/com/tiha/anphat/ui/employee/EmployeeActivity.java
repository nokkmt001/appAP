package com.tiha.anphat.ui.employee;

import android.view.View;
import android.widget.PopupMenu;

import com.tiha.anphat.data.entities.ChiNhanhInfo;
import com.tiha.anphat.data.entities.EmployeeInfo;
import com.tiha.anphat.databinding.ActivityEmployeeBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends BaseTestActivity<ActivityEmployeeBinding> implements EmployeeContract.View {
    ActivityEmployeeBinding bd;
    EmployeeAdapter adapter;
    String ChiNhanhID = "";
    EmployeePresenter presenter;
    List<ChiNhanhInfo> listChiNhanh ;

    @Override
    public ActivityEmployeeBinding getViewBinding() {
        return bd = ActivityEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        listChiNhanh = new ArrayList<>();
        listChiNhanh =PublicVariables.userLoginInfo.getListChiNhanh();
        bd.layoutHeader.imageBack.setOnClickListener(v -> finish());
        bd.layoutHeader.textTitle.setText("Danh sách nhân viên");
        adapter = new EmployeeAdapter();
        bd.rcl.setAdapter(adapter);
        if (listChiNhanh.size()>0){
            bd.textChiNhanh.setText(listChiNhanh.get(0).TenChiNhanh);
        }
        bd.textChiNhanh.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(EmployeeActivity.this, bd.textChiNhanh);
            if (PublicVariables.userLoginInfo.getListChiNhanh() != null) {
                for (ChiNhanhInfo item : listChiNhanh) {
                    menu.getMenu().add(item.TenChiNhanh);
                }

            }
            menu.setOnMenuItemClickListener(item -> {
                bd.textChiNhanh.setText(item.getTitle().toString());
                for (ChiNhanhInfo itemCheck : PublicVariables.userLoginInfo.getListChiNhanh()) {
                    if (itemCheck.TenChiNhanh.equals(item.getTitle().toString())) {
                        ChiNhanhID = itemCheck.ChiNhanhID;
                        presenter.GetListEmployee(ChiNhanhID);
                    }
                }
                return true;
            });
            menu.show();

        });
    }

    @Override
    protected void initData() {
        presenter = new EmployeePresenter(this);
        if (listChiNhanh.size()>0)
        {
            presenter.GetListEmployee(listChiNhanh.get(0).ChiNhanhID);
        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListEmployeeSuccess(List<EmployeeInfo> list) {
        adapter.clearData();
        adapter.addData(list);
    }

    @Override
    public void onGetListEmployeeError(String error) {
        showMessage(error);
    }
}
