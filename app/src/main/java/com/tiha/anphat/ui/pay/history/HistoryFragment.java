package com.tiha.anphat.ui.pay.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.network.booking.BookingModel;
import com.tiha.anphat.data.network.booking.IBookingModel;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.pay.history.vote.VoteEmployeeActivity;
import com.tiha.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends BaseFragment implements HistoryBookingContract.View {
    HistoryBookingPresenter presenter;
    HistoryBookingAdapter adapter;
    RecyclerView rcl;
    BookingModel model;
    TextView textNoBooking;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View view) {
        textNoBooking = bind(view, R.id.textNoBooking);
        adapter = new HistoryBookingAdapter(new ArrayList());
        rcl = bind(view, R.id.rcl);
        rcl.setAdapter(adapter);
        clickAdapter();

        checkResult(false);
    }

    @SuppressLint("NonConstantResourceId")
    private void clickAdapter(){
        adapter.setOnClick((view1, position) -> {
            HistoryBooking info = adapter.getItem(position);
            switch (view1.getId()) {
                case R.id.buttonVote:
                    showProgressDialog(true);
                    model.GetBooking(info.getSoCt(), new IBookingModel.IGetBookingFinish() {
                        @Override
                        public void onSuccess(BookingInfo info) {
                            showProgressDialog(false);
                            if (info.getMaTrangThai().equals("HOANTHANH")) {
                                PublicVariables.infoBooking = info;
                                startVote();
                            }else {
                                showMessage(getString(R.string.error_booking));
                            }
                        }
                        @Override
                        public void onError(String error) {
                            showProgressDialog(false);
                            showMessage(error);
                        }
                    });
                    break;
                case R.id.buttonBooking:
//                    showProgressDialog(true);
//                    model.GetBooking(info.getSoCt(), new IBookingModel.IGetBookingFinish() {
//                        @Override
//                        public void onSuccess(BookingInfo info) {
//                            showProgressDialog(false);
//                            List<ChiTietDonInfo> list = info.getListChiTietDonHang();
//                            model.InsertOrder();
//                        }
//                        @Override
//                        public void onError(String error) {
//                            showProgressDialog(false);
//                            showMessage(error);
//                        }
//                    });
                    break;
                default:
                    break;
            }
        });
    }

    private void startVote() {
        Intent intent = new Intent(getActivity(), VoteEmployeeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        model = new BookingModel();
        presenter = new HistoryBookingPresenter(this);
        presenter.GetListHistoryBooking();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onGetListHistoryBookingSuccess(List<HistoryBooking> list) {
        if (list.size() == 0) {
            checkResult(true);
        } else {
            adapter.clear();
            adapter.addAll(list);
        }
        checkResult(false);
    }

    @Override
    public void onGetListHistoryBookingError(String error) {
        showMessage(error);
        checkResult(true);
    }

    private void checkResult(Boolean isShow) {
        if (isShow) {
            rcl.setVisibility(View.GONE);
            textNoBooking.setVisibility(View.VISIBLE);
        } else {
            rcl.setVisibility(View.VISIBLE);
            textNoBooking.setVisibility(View.GONE);
        }
    }
}
