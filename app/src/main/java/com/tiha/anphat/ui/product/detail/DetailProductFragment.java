package com.tiha.anphat.ui.product.detail;

import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.product.ProductContract;
import com.tiha.anphat.ui.product.ProductPresenter;

import java.util.List;

public class DetailProductFragment extends BaseFragment implements ProductContract.View {
    String title = "";
    ProductPresenter presenter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static int PAGE_RECORD = 20;
    public DetailProductFragment(String textTitle) {
        title = textTitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_product;
    }

    @Override
    protected void onInit(View view) {

    }

    @Override
    protected void onLoadData() {
        presenter = new ProductPresenter(this);
        ProductCondition condition = new ProductCondition();
        condition.setBegin(0);
        condition.setEnd(PAGE_RECORD);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(title);
        condition.setTextSearch("");
        presenter.GetListProduct(condition);
    }

    @Override
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {

    }

    @Override
    public void onGetListProductError(String error) {

    }
}
