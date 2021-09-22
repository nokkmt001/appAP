package com.tiha.anphat.ui.product.detail;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.product.ProductContract;
import com.tiha.anphat.ui.product.ProductPresenter;
import com.tiha.anphat.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailProductFragment extends BaseFragment implements ProductContract.View {
    String title = "";
    ProductPresenter presenter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    ProductCondition condition = new ProductCondition();
    TextView textError;
    DetailAdapter adapter;
    RecyclerView rlv;

    public DetailProductFragment(String textTitle) {
        title = textTitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_product;
    }

    @Override
    protected void onInit(View view) {
        showProgressDialog(true);
        PAGE_RECORD = 20;
        adapter = new DetailAdapter(getActivity(), new ArrayList<ProductInfo>());
        textError = bind(view, R.id.textError);
        rlv = bind(view, R.id.rlvProduct);
        rlv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rlv.setHasFixedSize(true);
        rlv.setItemAnimator(new DefaultItemAnimator());
        rlv.setAdapter(adapter);
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isLoading = true;
                currentPage += 1;
                LoadNextPage();
            }
        });
    }

    @Override
    protected void onLoadData() {
        presenter = new ProductPresenter(this);
        condition.setBegin(PAGE_START);
        condition.setEnd(PAGE_RECORD);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(title);
        condition.setTextSearch("");
        presenter.GetListProduct(condition);
    }

    public void LoadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenter.GetListProduct(condition);
    }

    @Override
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    public void showError(final boolean isShow) {
        textError.setVisibility(isShow ? View.VISIBLE : View.GONE);
        rlv.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {
        showProgressDialog(false);
        adapter.addAll(list);
        if (list.size() == 0) {
            showError(true);
        } else {
            double phanDu = Double.parseDouble(String.valueOf(total)) % PAGE_RECORD;
            int phanNguyen = (Integer.parseInt(String.valueOf(total)) / PAGE_RECORD);
            TOTAL_PAGES = (phanDu > 0) ? phanNguyen + 1 : phanNguyen;
            if (currentPage < TOTAL_PAGES){

            } else isLastPage = true;
        }

    }

    @Override
    public void onGetListProductError(String error) {
        CommonUtils.showMessageError(getActivity(), error);
        showProgressDialog(false);
    }

    @Override
    public void onGetImageByProDuctIDSuccess() {

    }

    @Override
    public void onGetImageByProDuctIDError(String error) {
        CommonUtils.showMessageError(getActivity(), error);

    }
}
