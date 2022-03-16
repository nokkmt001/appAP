package com.tiha.anphatsu.ui.pay.pending;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.HistoryBooking;
import com.tiha.anphatsu.data.entities.condition.CancelOrderCondition;
import com.tiha.anphatsu.data.entities.order.BookingInfo;
import com.tiha.anphatsu.data.entities.order.ChiTietDonInfo;
import com.tiha.anphatsu.ui.base.BaseFragment;
import com.tiha.anphatsu.ui.booking.ProductAfterAdapter;
import com.tiha.anphatsu.ui.pay.history.HistoryBookingContract;
import com.tiha.anphatsu.ui.pay.history.HistoryBookingPresenter;
import com.tiha.anphatsu.utils.PublicVariables;

import java.util.List;

public class PendingFragment extends BaseFragment implements HistoryBookingContract.View, CancelBookingContract.View {
    CancelBookingPresenter presenterCancel;
    HistoryBookingPresenter presenterHistory;
    ProductAfterAdapter adapter;
    RecyclerView rcl;
    TextView textAddress, textNameProduct,textEmployee,textNoBooking;
    View viewFinish, viewPending, viewProgress, viewEnd, viewStart;
    Button buttonCancel;
    String gg = "";
    ConstraintLayout layoutMain;
    AppPreference preference;
    BookingInfo itemMain = null;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pending;
    }

    @Override
    protected void initView(View view) {
        showProgressDialog(true);
        presenterCancel = new CancelBookingPresenter(this);
        presenterHistory = new HistoryBookingPresenter(this);
        adapter = new ProductAfterAdapter(getActivity());
        rcl = bind(view, R.id.rcl);
        rcl.setAdapter(adapter);
        textNoBooking = bind(view, R.id.textNoBooking);
        textEmployee = bind(view,R.id.textEmployee);
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
                        condition.setSoDonHang(itemMain.getSoCt());
                        presenterCancel.CancelBooking(condition);
                        preference.setBooking(null);
                        dialog.cancel();
                    })
                    .setNegativeButton("HỦY", (dialog, which) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    protected void initData() {
        preference = new AppPreference(getContext());
//        if (preference.getBooking() != null) {
//            presenterCancel.GetBooking(preference.getBooking());
//        } else {
            checkResult(true);
            showProgressDialog(false);
            presenterHistory.GetListHistoryBooking();
//        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void GetBookingSuccess(BookingInfo info) {
        if (!info.getMaTrangThai().equals("HOANTHANH")) {
            String name = "";
            itemMain = info;
            textEmployee.setText(info.TenNguoiGiao== null?"":info.TenNguoiGiao);
            switch (info.getMaTrangThai()) {
                case "DADAT":
                    viewStart.setBackgroundResource(R.drawable.shape_status_completed);
                    viewPending.setBackgroundResource(R.color.colorLine);
                    viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                    viewFinish.setBackgroundResource(R.color.colorLine);
                    viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                    buttonCancel.setVisibility(View.VISIBLE);
//                    break;
                case "DANGGIAOHANG":
                    viewPending.setBackgroundResource(R.color.Blue);
                    viewProgress.setBackgroundResource(R.drawable.shape_status_completed);
                    viewFinish.setBackgroundResource(R.color.colorLine);
                    viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                    buttonCancel.setVisibility(View.GONE);
                    break;
//                case "HOANTHANH":
//                    viewPending.setBackgroundResource(R.color.colorLine);
//                    viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
//                    viewFinish.setBackgroundResource(R.color.Blue);
//                    viewEnd.setBackgroundResource(R.drawable.shape_status_completed);
//                    buttonCancel.setVisibility(View.GONE);
//                    break;
                default:
                    break;
            }
            textAddress.setText(info.getDiachigiaohang());
            adapter.clear();
            adapter.addAll(info.getListChiTietDonHang());

            if (info.getListChiTietDonHang().size() > 0) {
                for (ChiTietDonInfo itemI : info.getListChiTietDonHang()) {
                    name += " " + itemI.getProduct_Name() + " X " + itemI.getSL() + " ,";
                }
            }
            if (name.length() > 0) {
                textNameProduct.setText(name.substring(0, name.length() - 1));
            }
        }

        if (textNameProduct.getText().length() == 0) {
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
        initData();
    }

    @Override
    public void onCancelBookingError(String error) {
    }

    @Override
    public void onGetListHistoryBookingSuccess(List<HistoryBooking> list) {
        if (list.size() == 0) {
            checkResult(true);
            return;
        }
        checkResult(false);
        for (HistoryBooking item : list) {
            presenterCancel.GetBooking(item.SoPhieuVietTay);
        }
        showProgressDialog(false);
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
