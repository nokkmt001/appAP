package com.tiha.anphat.ui.pay.pending;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.booking.BookingContract;
import com.tiha.anphat.ui.booking.BookingPresenter;
import com.tiha.anphat.ui.booking.ProductAdapter;

public class PendingFragment extends BaseFragment implements BookingContract.View {
    BookingPresenter presenter;
    ProductAdapter adapter;
    RecyclerView rcl;
    TextView textAddress;
    View viewFinish, viewPending, viewProgress, viewEnd, viewStart;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pending;
    }

    @Override
    protected void initView(View view) {
        adapter = new ProductAdapter(getActivity());

        rcl = bind(view, R.id.rcl);
        rcl.setAdapter(adapter);

        textAddress = bind(view, R.id.textAddress);

        viewPending = bind(view, R.id.viewPending);
        viewFinish = bind(view, R.id.viewFinish);
        viewProgress = bind(view, R.id.status_progress);
        viewEnd = bind(view, R.id.status_end);
        viewStart = bind(view, R.id.status_start);
    }

    @Override
    protected void initData() {
        presenter = new BookingPresenter(this);
        presenter.GetBooking("CTY211109001");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void GetBookingSuccess(BookingInfo info) {
        switch (info.getMaTrangThai()) {
            case "DADAT":
                viewPending.setBackgroundResource(R.color.colorLine);
                viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                viewFinish.setBackgroundResource(R.color.colorLine);
                viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                break;
            case "DANGGIAOHANG":
                viewPending.setBackgroundResource(R.color.Blue);
                viewProgress.setBackgroundResource(R.drawable.shape_status_completed);
                viewFinish.setBackgroundResource(R.color.Blue);
                viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                break;
            case "HOANTHANH":
                viewPending.setBackgroundResource(R.color.colorLine);
                viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                viewFinish.setBackgroundResource(R.color.Blue);
                viewEnd.setBackgroundResource(R.drawable.shape_status_completed);
                break;
            default:
                break;
        }
        textAddress.setText(info.getDiachigiaohang());
        if (info != null) {
            adapter.clear();
            adapter.addAll(info.getListChiTietDonHang());
        }
    }

    @Override
    public void GetBookingError(String error) {
        showMessage(error);
    }
}
