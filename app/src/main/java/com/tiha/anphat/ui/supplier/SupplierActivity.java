package com.tiha.anphat.ui.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.SupplierInfo;
import com.tiha.anphat.data.entities.condition.SupplierCondition;
import com.tiha.anphat.databinding.ActivitySupplierBinding;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.ui.base.PageScrollListener;

import java.util.List;

public class SupplierActivity extends BaseTestActivity<ActivitySupplierBinding> implements SupplierContract.View {
    ActivitySupplierBinding binding;
    SupplierAdapter adapter;
    SupplierCondition condition;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static final int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    SupplierPresenter presenter;

    @Override
    public ActivitySupplierBinding getViewBinding() {
        return binding = ActivitySupplierBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.layoutHeader.textTitle.setText("Danh sách khách hàng");
        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());

        adapter = new SupplierAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        binding.rcl.setLayoutManager(llm);
        binding.rcl.addOnScrollListener(new PageScrollListener(llm) {
            @Override
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
        binding.rcl.setAdapter(adapter);
        adapter.setOnClickListener((view, position) -> {
            SupplierInfo gg = adapter.getItem(position);
            switch (view.getId()) {
                case R.id.layoutMain:
                    Intent intent = new Intent(SupplierActivity.this, SupplierDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("supplierID", gg.getSupplier_ID());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        isLastPage = false;
        presenter = new SupplierPresenter(this);
        condition = new SupplierCondition();
        condition.setBegin(PAGE_START);
        condition.setEnd(PAGE_RECORD);
        presenter.GetListSupplier(condition);
    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenter.GetListSupplier(condition);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onGetListSupplierSuccess(List<SupplierInfo> list, long total) {
        if (condition.getBegin() == 1) {
            adapter.clear();
            if (list.size() == 0) {
                showNoResult();
            }
        } else {
            adapter.removeLoadingFooter();
            isLoading = false;
        }
        adapter.addAll(list);

        double phanDu = Double.parseDouble(String.valueOf(total)) % PAGE_RECORD;
        int phanNguyen = (Integer.parseInt(String.valueOf(total)) / PAGE_RECORD);
        TOTAL_PAGES = (phanDu > 0) ? phanNguyen + 1 : phanNguyen;
        if (currentPage < TOTAL_PAGES) {
            adapter.addLoadingFooter();
        } else isLastPage = true;
        showProgressDialog(false);
    }

    @Override
    public void onGetListSupplierError(String error) {
        showMessage(error);
    }
}
