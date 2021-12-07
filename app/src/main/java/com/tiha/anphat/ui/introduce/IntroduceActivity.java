package com.tiha.anphat.ui.introduce;

import android.view.View;

import com.tiha.anphat.data.entities.IntroducePerInfo;
import com.tiha.anphat.data.entities.presenteruser.InsertPresenterInfo;
import com.tiha.anphat.databinding.FragmentIntroduceBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;

import java.util.ArrayList;
import java.util.List;

public class IntroduceActivity extends BaseTestActivity<FragmentIntroduceBinding> implements IntroduceContract.View {
    protected FragmentIntroduceBinding binding;
    IntroduceAdapter adapter;
    IntroducePresenter presenter;
    List<IntroducePerInfo> listAllData = new ArrayList<>();

    @Override
    public FragmentIntroduceBinding getViewBinding() {
        return binding = FragmentIntroduceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        adapter = new IntroduceAdapter();
        binding.rcl.setAdapter(adapter);
        showProgressDialog(true);
    }

    @Override
    protected void initData() {
        presenter = new IntroducePresenter(this);
        presenter.GetListIntroduce();
        listAllData = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            IntroducePerInfo info = new IntroducePerInfo();
//            info.HoTen = "Bui Tan Phat";
//            info.NguoiDungMobileID = 14;
//            listAllData.add(info);
//        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListIntroduceSuccess(List<IntroducePerInfo> list) {
        adapter.clearData();
        adapter.addData(listAllData);
        showProgressDialog(false);
    }

    @Override
    public void onGetListIntroduceError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }

    @Override
    public void onInsertIntroduceSuccess(InsertPresenterInfo info) {
        showProgressDialog(false);
    }

    @Override
    public void onInsertIntroduceError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }
}
