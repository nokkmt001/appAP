package com.tiha.anphat.ui.transport;

import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.pay.history.HistoryBookingContract;
import com.tiha.anphat.ui.pay.history.HistoryBookingPresenter;

import java.util.List;

class TransportFragment extends BaseFragment implements HistoryBookingContract.View {
    HistoryBookingPresenter presenter;
    TransportAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View view) {
        adapter = new TransportAdapter();
    }

    @Override
    protected void initData() {
        presenter = new HistoryBookingPresenter(this);
        presenter.GetListHistoryBooking();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListHistoryBookingSuccess(List<HistoryBooking> list) {
        adapter.clearData();
        ;
        adapter.addData(list);
    }

    @Override
    public void onGetListHistoryBookingError(String error) {
        showMessage(error);
    }
}
