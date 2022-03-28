package com.anphat.supplier.ui.introduce;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.data.entities.presenteruser.InsertPresenterInfo;
import com.anphat.supplier.databinding.FragmentIntroduceBinding;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IntroduceActivity extends BaseTestActivity<FragmentIntroduceBinding> implements IntroduceContract.View {
    protected FragmentIntroduceBinding bd;
    IntroduceAdapter adapter;
    IntroducePresenter presenter;
    List<IntroducePerInfo> listAllData = new ArrayList<>();
    private Timer timer;

    @Override
    public FragmentIntroduceBinding getViewBinding() {
        return bd = FragmentIntroduceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        adapter = new IntroduceAdapter(this);
        bd.rcl.setAdapter(adapter);
        bd.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (s.length() != 0) {
                            if (AppUtils.validateNumberPhone(s.toString())) {
                                presenter.InsertIntroduce(s.toString());
                            }
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
        presenter = new IntroducePresenter(this);
        presenter.GetListIntroduce();
        listAllData = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListIntroduceSuccess(List<IntroducePerInfo> list) {
        adapter.clearData();
        adapter.addData(list);
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
        showToast(getString(R.string.title_add_presenter));
        initData();
        bd.etSearch.setText("");
    }

    @Override
    public void onInsertIntroduceError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }
}
