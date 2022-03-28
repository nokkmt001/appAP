package com.anphat.supplier.ui.pay.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;
import com.anphat.supplier.databinding.FragmentHistoryBinding;
import com.anphat.supplier.ui.base.BaseMainFragment;
import com.anphat.supplier.ui.pay.history.vote.VoteEmployeeActivity;
import com.anphat.supplier.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends BaseMainFragment<FragmentHistoryBinding> implements HistoryBookingContract.View {
    HistoryBookingPresenter presenter;
    HistoryBookingAdapter adapter;
    BookingModel model;

    @Override
    public FragmentHistoryBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentHistoryBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        adapter = new HistoryBookingAdapter(new ArrayList<>());
        binding.rcl.setAdapter(adapter);
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
                    model.GetBooking(info.SoPhieuVietTay, new IBookingModel.IGetBookingFinish() {
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
            binding.rcl.setVisibility(View.GONE);
            binding.textNoBooking.setVisibility(View.VISIBLE);
        } else {
            binding.rcl.setVisibility(View.VISIBLE);
            binding.textNoBooking.setVisibility(View.GONE);
        }
    }
}
