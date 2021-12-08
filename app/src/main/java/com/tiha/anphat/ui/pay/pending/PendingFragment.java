package com.tiha.anphat.ui.pay.pending;

import android.content.Context;
import android.content.DialogInterface;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.data.entities.condition.CancelOrderCondition;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.entities.order.ChiTietDonInfo;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.booking.BookingContract;
import com.tiha.anphat.ui.booking.BookingPresenter;
import com.tiha.anphat.ui.booking.ProductAdapter;
import com.tiha.anphat.ui.pay.history.HistoryBookingContract;
import com.tiha.anphat.ui.pay.history.HistoryBookingPresenter;
import com.tiha.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends BaseFragment implements BookingContract.View, HistoryBookingContract.View, CancelBookingContract.View {
    BookingPresenter presenter;
    CancelBookingPresenter presenterCancel;
    HistoryBookingPresenter presenterHistory;
    ProductAdapter adapter;
    RecyclerView rcl;
    TextView textAddress, textNameProduct;
    View viewFinish, viewPending, viewProgress, viewEnd, viewStart;
    Button buttonCancel;
    String gg = "";
    List<BookingInfo> listAllData = new ArrayList<>();
    ConstraintLayout layoutMain;
    TextView textNoBooking;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pending;
    }

    @Override
    protected void initView(View view) {
        showProgressDialog(true);
        presenterCancel = new CancelBookingPresenter(this);
        presenterHistory = new HistoryBookingPresenter(this);
        adapter = new ProductAdapter(getActivity());
        rcl = bind(view, R.id.rcl);
        rcl.setAdapter(adapter);
        textNoBooking = bind(view, R.id.textNoBooking);
        textNameProduct = bind(view, R.id.textNameProduct);
        textAddress = bind(view, R.id.textAddress);
        viewPending = bind(view, R.id.viewPending);
        viewFinish = bind(view, R.id.viewFinish);
        viewProgress = bind(view, R.id.status_progress);
        viewEnd = bind(view, R.id.status_end);
        viewStart = bind(view, R.id.status_start);
        buttonCancel = bind(view, R.id.buttonCancel);
        layoutMain = bind(view, R.id.layoutMain);

        buttonCancel.setOnClickListener(v -> {
            gg = "";
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = inflater.inflate(R.layout.dialog_cancel_booking, null);
            final EditText editText = view1.findViewById(R.id.etComment);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false)
                    .setTitle("Bạn chắc chắn hủy đơn hàng này.")
                    .setView(view1)
                    .setPositiveButton("Xác Nhận", (dialog, which) -> {
                        gg = editText.getText().toString();
                        CancelOrderCondition condition = new CancelOrderCondition();
                        condition.setLyDoHuy(gg);
                        condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
                        condition.setSoDonHang("CTY211109001");
                        presenterCancel.CancelBooking(condition);
                        dialog.cancel();
                    })
                    .setNegativeButton("HỦY", (dialog, which) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    protected void initData() {
        presenter = new BookingPresenter(this);
        presenterHistory.GetListHistoryBooking();

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void GetBookingSuccess(BookingInfo info) {
        listAllData.add(info);
        for (BookingInfo item : listAllData) {
            if (!item.getMaTrangThai().equals("HOANTHANH")) {
                String name = "";
                switch (info.getMaTrangThai()) {
                    case "DADAT":
                        viewStart.setBackgroundResource(R.drawable.shape_status_completed);
                        viewPending.setBackgroundResource(R.color.colorLine);
                        viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                        viewFinish.setBackgroundResource(R.color.colorLine);
                        viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                        buttonCancel.setVisibility(View.VISIBLE);
                        break;
                    case "DANGGIAOHANG":
                        viewPending.setBackgroundResource(R.color.Blue);
                        viewProgress.setBackgroundResource(R.drawable.shape_status_completed);
                        viewFinish.setBackgroundResource(R.color.Blue);
                        viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                        buttonCancel.setVisibility(View.GONE);
                        break;
                    case "HOANTHANH":
                        viewPending.setBackgroundResource(R.color.colorLine);
                        viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                        viewFinish.setBackgroundResource(R.color.Blue);
                        viewEnd.setBackgroundResource(R.drawable.shape_status_completed);
                        buttonCancel.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                textAddress.setText(info.getDiachigiaohang());
                if (info != null) {
                    adapter.clear();
                    adapter.addAll(info.getListChiTietDonHang());
                }

                if (info.getListChiTietDonHang().size() > 0) {
                    for (ChiTietDonInfo itemI : info.getListChiTietDonHang()) {
                        name += " " + itemI.getProduct_Name() + " X " + itemI.getSL() + " ,";
                    }
                }
                if (name.length() > 0) {
                    textNameProduct.setText(name.substring(0, name.length() - 1));
                }
            }
        }
        if (textNameProduct.getText().length()==0){
            checkResult(true);
        }
        showProgressDialog(false);
    }

    @Override
    public void GetBookingError(String error) {
        showMessage(error);
    }

    @Override
    public void onCancelBookingSuccess() {

    }

    @Override
    public void onCancelBookingError(String error) {

    }

    @Override
    public void onGetListHistoryBookingSuccess(List<HistoryBooking> list) {
        if (list.size() == 0) {
            showProgressDialog(false);
            checkResult(true);
            return;
        }
        checkResult(false);
        for (HistoryBooking item : list) {
            presenter.GetBooking(item.getSoCt());
        }
    }

    @Override
    public void onGetListHistoryBookingError(String error) {
    }

    private void checkResult(Boolean isShow) {
        if (isShow) {
            layoutMain.setVisibility(View.GONE);
            textNoBooking.setVisibility(View.VISIBLE);
        } else {
            layoutMain.setVisibility(View.VISIBLE);
            textNoBooking.setVisibility(View.GONE);
        }
    }
}
