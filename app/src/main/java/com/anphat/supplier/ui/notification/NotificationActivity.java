package com.anphat.supplier.ui.notification;

import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.condition.NotificationCondition;
import com.anphat.supplier.databinding.ActivityNotiBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.ui.base.PageScrollListener;
import com.anphat.supplier.ui.base.SearchMain;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.viewmodel.NotificationViewModel;

public class NotificationActivity extends BaseMVVMActivity<ActivityNotiBinding, NotificationViewModel> {
    NotificationCondition condition;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static final int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;

    @Override
    protected Class<NotificationViewModel> getClassVM() {
        return NotificationViewModel.class;
    }

    @Override
    protected void startInit(Bundle savedInstanceState) {
        super.startInit(savedInstanceState);
        condition = new NotificationCondition();
    }

    @Override
    public ActivityNotiBinding getViewBinding() {
        return ActivityNotiBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.textMainTitle.setText(R.string.title_text_notification);
        binding.imageBack.setOnClickListener(view -> finish());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        binding.rvNotification.addOnScrollListener(new PageScrollListener(llm ) {
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadNextPage() {
        condition.Begin = condition.End + 1;
        condition.End = condition.End + PAGE_RECORD;
    }

    @Override
    protected void initData() {
        condition.Begin = PAGE_START;
        condition.End = PAGE_RECORD;
        condition.LoaiThongBao = "";
        condition.NguoiDungMobileID = PublicVariables.UserInfo.getNguoiDungMobileID();
        condition.TrangThai = "";
        viewModel.getListNotification(condition);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.itemListSuccess.observe(this, result ->{
            if (result!=null){

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}