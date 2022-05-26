package com.anphat.supplier.ui.introduce;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.databinding.FragmentIntroduceBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.base.SearchMain;
import com.anphat.supplier.viewmodel.IntroduceViewModel;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IntroduceActivity extends BaseMVVMActivity<FragmentIntroduceBinding, IntroduceViewModel> {
    protected FragmentIntroduceBinding bd;
    IntroduceAdapter adapter;
    List<IntroducePerInfo> listAllData = new ArrayList<>();

    @Override
    protected Class<IntroduceViewModel> getClassVM() {
        return IntroduceViewModel.class;
    }

    @Override
    public FragmentIntroduceBinding getViewBinding() {
        return bd = FragmentIntroduceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        adapter = new IntroduceAdapter(this);
        bd.rcl.setAdapter(adapter);
        bd.etSearch.addTextChangedListener(new SearchMain() {
            @Override
            protected void onAfterChanged(String s) {
                new Handler().postDelayed(() -> {
                    if (s.length() != 0) {
                        if (AppUtils.validateNumberPhone(s)) {
                            viewModel.InsertIntroduce(s);
                        }
                    }
                }, AppConstants.DELAY_FIND_DATA_SEARCH);
            }
        });

        showProgressDialog(true);
        bd.imageBack.setOnClickListener(v -> finish());

    }

    @Override
    protected void initData() {
        viewModel.GetListIntroduce();
        listAllData = new ArrayList<>();
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.itemList.observe(this, result -> {
            if (result != null) {
                adapter.clearData();
                adapter.addData(result);

            }
            showProgressDialog(false);
        });

        viewModel.itemMain.observe(this, result -> {
            if (result!=null){
                if (result.Status==0) {
                    showToast(getString(R.string.title_add_presenter));
                    initData();
                    bd.etSearch.setText("");
                } else {
                    showMessage(result.Message);
                }
            }

            showProgressDialog(false);
        });
    }

    @Override
    public void onClick(View view) {

    }
}
