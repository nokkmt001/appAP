package com.anphat.supplier.ui.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.condition.NotificationCondition;
import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.databinding.ActivityNotiBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.base.PageScrollListener;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.viewmodel.NotificationViewModel;

public class NotificationActivity extends BaseMVVMActivity<ActivityNotiBinding, NotificationViewModel> {
    NotificationCondition condition;
    NotificationAdapter adapter;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static final int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    private static final int START_DETAIL = 2;

    @Override
    protected Class<NotificationViewModel> getClassVM() {
        return NotificationViewModel.class;
    }

    @Override
    protected void startInit(Bundle savedInstanceState) {
        super.startInit(savedInstanceState);
        condition = new NotificationCondition();
        adapter = new NotificationAdapter(this);
    }

    @Override
    public ActivityNotiBinding getViewBinding() {
        return ActivityNotiBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.textMainTitle.setText(R.string.title_text_notification);
        binding.imageBack.setOnClickListener(view -> finish());
        binding.textMainTitle.setText(R.string.title_notification);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        binding.rvNotification.setAdapter(adapter);
        binding.rvNotification.addOnScrollListener(new PageScrollListener(llm) {
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
        binding.textAll.setOnClickListener(view -> loadData("TatCa"));
        binding.textNoView.setOnClickListener(view -> loadData("ChuaXem"));

//        adapter.setOnItemClickListener((notificationMain, view, position) -> startDetail(notificationMain));
    }

    @Override
    protected void initData() {
        loadData("TatCa");
    }

    public void loadData(String category) {
        condition.Begin = PAGE_START;
        condition.End = PAGE_RECORD;
        condition.LoaiThongBao = "TatCa";
        condition.NguoiDungMobileID = PublicVariables.UserInfo.getNguoiDungMobileID();
        condition.TrangThai = category;
        viewModel.getListNotification(condition);
    }

    public void startDetail(NotificationMain item) {
        switch (item.LoaiThongBao) {
            case "CaNhan":

                break;
            case "HeThong":

                break;
            case "TinTuc":

                break;
            default:
                break;

        }
        NotificationCondition condition = new NotificationCondition();
        condition.ThongBaoMobileUserID = item.ThongBaoMobileID;
        viewModel.updateView(condition);
        Intent intent = new Intent(this, NotificationDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ITEM", item);
        intent.putExtras(bundle);
        startActivityForResult(intent, START_DETAIL);
    }

    private void loadNextPage() {
        condition.Begin = condition.End + 1;
        condition.End = condition.End + PAGE_RECORD;
        viewModel.getListNotification(condition);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.itemListSuccess.observe(this, result -> {
            if (result != null) {
                if (condition.Begin == 1) {
                    adapter.clear();
                } else {
                    adapter.removeLoadingFooter();
                    isLoading = false;
                }
                double phanDu = Double.parseDouble(String.valueOf(result.Total)) % PAGE_RECORD;
                int phanNguyen = (Integer.parseInt(String.valueOf(result.Total)) / PAGE_RECORD);
                TOTAL_PAGES = (phanDu > 0) ? phanNguyen + 1 : phanNguyen;
                adapter.addAll(result.List);
                if (currentPage < TOTAL_PAGES) adapter.addLoadingFooter(new NotificationMain());
                else isLastPage = true;
            }
        });

        viewModel.iteUpdateViewOne.observe(this, result -> {
            if (result) {
                showToast("Success");
            }
        });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == START_DETAIL) {
            initData();
        }
    }

    @Override
    public void onClick(View v) {
    }

}