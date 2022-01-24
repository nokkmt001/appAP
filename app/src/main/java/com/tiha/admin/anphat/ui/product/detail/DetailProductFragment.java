package com.tiha.admin.anphat.ui.product.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.ProductInfo;
import com.tiha.admin.anphat.data.entities.condition.ProductCondition;
import com.tiha.admin.anphat.main.MainActivity;
import com.tiha.admin.anphat.ui.base.BaseEventClick;
import com.tiha.admin.anphat.ui.base.BaseFragment;
import com.tiha.admin.anphat.ui.base.PageScrollListener;
import com.tiha.admin.anphat.ui.product.ProductContract;
import com.tiha.admin.anphat.ui.product.ProductPresenter;
import com.tiha.admin.anphat.ui.product.update.UpdateProductActivity;
import com.tiha.admin.anphat.utils.AppConstants;
import com.tiha.admin.anphat.utils.CommonUtils;
import com.tiha.admin.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("SetTextI18n")
public class DetailProductFragment extends BaseFragment implements ProductContract.View {
    String title = "";
    ProductPresenter presenter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static int PAGE_RECORD = 10;
    private int currentPage = PAGE_START;
    ProductCondition condition = new ProductCondition();
    TextView textError;
    DetailAdapter adapter;
//    TestAdapter adapter;
    RecyclerView rlv;
    EditText inputSearch;
    ImageView imageDelete;
    private Timer timer;
    ProductInfo info;
    MainActivity activity;
    Double inventory = 0.0; // tồn kho
    List<ProductInfo> listData;

    public DetailProductFragment(String textTitle) {
        title = textTitle;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_detail_product;
    }

    @Override
    protected void initView(View view) {
        activity = new MainActivity();
        inputSearch = bind(view, R.id.inputSearch);
        imageDelete = bind(view, R.id.imageDelete);
        adapter = new DetailAdapter(getActivity(), new ArrayList<ProductInfo>(), title);
//        adapter = new TestAdapter(getActivity());
        textError = bind(view, R.id.textError);
        rlv = bind(view, R.id.rlvProduct);
        rlv.setAdapter(adapter);
//        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                isLoading = true;
//                currentPage += 1;
//                loadNextPage();
//            }
//        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rlv.setLayoutManager(llm);

        rlv.addOnScrollListener(new PageScrollListener(llm) {
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

        adapter.setClickListener(new BaseEventClick.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                info = adapter.getItem(position);
                String gg = info.getImageBitMap();
                Intent intent = new Intent(getContext(), UpdateProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putString("ProductID", info.getProduct_ID());
                intent.putExtras(bundle);
                startActivity(intent);

//                presenter.GetProductInventory("CTY", info.getProduct_ID(), AppUtils.formatDateToString(Calendar.getInstance().getTime(), "dd/MM/yyyy"));
            }
        });
//        adapter.setOnClick(new BaseTestAdapter.OnClick() {
//            @Override
//            public void onClick(View view, int position) {
//                info = adapter.getItem(position);
//                String gg = info.getImageBitMap();
//                Intent intent = new Intent(getContext(), UpdateProductActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Bundle bundle = new Bundle();
//                bundle.putString("ProductID", info.getProduct_ID());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        Search();

    }

    public void Search() {
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().isEmpty()) imageDelete.setVisibility(View.VISIBLE);
                else imageDelete.setVisibility(View.GONE);
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
//                                onLoadSearch(editable.toString());
                            }
                        });
                    }

                }, AppConstants.DELAY_FIND_DATA);
            }
        });

        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputSearch.setText("");
            }
        });
    }

    @Override
    protected void initData() {
        isLastPage = false;
        listData =new ArrayList<>();
        adapter.clear();
        presenter = new ProductPresenter(this);
        condition.setBegin(PAGE_START);
        condition.setUserName(PublicVariables.userLoginInfo.UserName);
        condition.setNhomLoaiHang(title);
        if (!TextUtils.isEmpty(inputSearch.getText().toString())) {
            condition.setEnd(100000);
        } else {
            condition.setEnd(PAGE_RECORD);
        }
        condition.setTextSearch(inputSearch.getText().toString());
        presenter.GetListProduct(condition);
//        showProgressDialog(true);
    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenter.GetListProduct(condition);
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
        showError(false);
        if (condition.getBegin() == 1){
            adapter.clear();
            if (list.size()== 0){
                showError(true);
            }
        } else {
            adapter.removeLoadingFooter();
            isLoading = false;
        }
        adapter.addAll(list);

        double phanDu = Double.parseDouble(String.valueOf(total)) % PAGE_RECORD;
        int phanNguyen = (Integer.parseInt(String.valueOf(total)) / PAGE_RECORD);
        TOTAL_PAGES = (phanDu > 0) ? phanNguyen + 1 : phanNguyen;
        if (currentPage < TOTAL_PAGES){
            adapter.addLoadingFooter();
        }
        else isLastPage = true;
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductError(String error) {
        CommonUtils.showMessageError(getActivity(), error);
        showProgressDialog(false);
    }

    @Override
    public void onGetImageByProDuctIDSuccess(String imageBitmap) {

    }

    @Override
    public void onGetImageByProDuctIDError(String error) {

    }

    @Override
    public void onGetProductInventorySuccess(Double result) {
        if (result != null) inventory = result;
    }

    @Override
    public void onGetProductInventoryError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetListProductInventorySuccess(List<ProductInfo> list) {

    }

    @Override
    public void onGetListProductInventoryError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }

}
