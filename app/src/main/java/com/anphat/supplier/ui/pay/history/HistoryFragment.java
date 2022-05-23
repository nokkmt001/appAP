package com.anphat.supplier.ui.pay.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.databinding.FragmentHistoryBinding;
import com.anphat.supplier.ui.base.BaseMainFragment;
import com.anphat.supplier.ui.pay.history.vote.VoteEmployeeActivity;
import com.anphat.supplier.viewmodel.HistoryViewModel;
import com.anphat.supplier.utils.PublicVariables;

import java.util.ArrayList;

public class HistoryFragment extends BaseMainFragment<FragmentHistoryBinding> {
    HistoryBookingAdapter adapter;
    HistoryViewModel viewModel;

    @Override
    public FragmentHistoryBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentHistoryBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        showProgressDialog(true);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        adapter = new HistoryBookingAdapter(new ArrayList<>(), getContext());
        binding.rcl.setAdapter(adapter);
        clickAdapter();
        checkResult(false);
    }

    @SuppressLint("NonConstantResourceId")
    private void clickAdapter() {
        adapter.setOnClick((view1, position) -> {
            HistoryBooking info = adapter.getItem(position);
            switch (view1.getId()) {
                case R.id.buttonVote:
                    showProgressDialog(true);
                    viewModel.GetDetailHistory(info.SoPhieuVietTay);
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
        viewModel.getHistoryBooking();
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.ItemList.observe(this, result -> {
            if (result!=null){
                if (result.size() == 0) {
                    checkResult(true);
                } else {
                    adapter.clear();
                    adapter.addAll(result);
                }
                checkResult(false);
            } else {
                checkResult(true);
            }

            showProgressDialog(false);
        });

        viewModel.ItemDetail.observe(this, item -> {
            showProgressDialog(false);

            if (item!=null){
                if (item.getMaTrangThai().equals("HOANTHANH")) {
                    PublicVariables.infoBooking = item;
                    startVote();
                } else {
                    showMessage(getString(R.string.error_booking));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

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
