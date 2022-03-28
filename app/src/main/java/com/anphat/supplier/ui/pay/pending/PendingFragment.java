package com.anphat.supplier.ui.pay.pending;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.condition.CancelOrderCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.ChiTietDonInfo;
import com.anphat.supplier.main.MainActivity;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.booking.ProductAfterAdapter;
import com.anphat.supplier.ui.pay.history.HistoryBookingContract;
import com.anphat.supplier.ui.pay.history.HistoryBookingPresenter;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.List;

public class PendingFragment extends BaseFragment implements HistoryBookingContract.View, CancelBookingContract.View {
    CancelBookingPresenter presenterCancel;
    HistoryBookingPresenter presenterHistory;
    ProductAfterAdapter adapter;
    RecyclerView rcl;
    TextView textAddress, textNameProduct, textEmployee, textNoBooking, textTotalPrice;
    View viewFinish, viewTwo, viewPending, viewProgress, viewEnd, viewStart, status_book, viewBooking;
    Button buttonCancel;
    String gg = "";
    ConstraintLayout layoutMain;
    AppPreference preference;
    BookingInfo itemMain = null;
    TestReceiver testReceiver;
    LinearLayout layoutTwo;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pending;
    }

    @Override
    protected void initView(View view) {
        testReceiver = new TestReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestConstants.ACTION_MAIN_PENDING);
        getContext().registerReceiver(testReceiver, intentFilter);

        showProgressDialog(true);
        presenterCancel = new CancelBookingPresenter(this);
        presenterHistory = new HistoryBookingPresenter(this);
        adapter = new ProductAfterAdapter(getActivity());
        rcl = bind(view, R.id.rcl);
        rcl.setAdapter(adapter);
        viewTwo = bind(view, R.id.viewTwo);
        layoutTwo = bind(view, R.id.layoutTwo);
        textTotalPrice = bind(view,R.id.textTotalPrice);
        textNoBooking = bind(view, R.id.textNoBooking);
        textEmployee = bind(view, R.id.textEmployee);
        textNameProduct = bind(view, R.id.textNameProduct);
        textAddress = bind(view, R.id.textAddress);
        viewPending = bind(view, R.id.viewPending);
        viewFinish = bind(view, R.id.viewFinish);
        viewProgress = bind(view, R.id.status_progress);
        viewEnd = bind(view, R.id.status_end);

        status_book = bind(view, R.id.status_book);
        viewBooking = bind(view, R.id.viewBooking);

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
                        if (itemMain.getSoCt() == null) {
                            dialog.cancel();
                            return;
                        }

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
        presenterCancel.checkBooking();
        showProgressDialog(false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void GetBookingSuccess(BookingInfo info) {
        adapter.clear();
        adapter.addAll(info.getListChiTietDonHang());
        Integer gg = 0;
        if (info.getListChiTietDonHang() != null) {
            for (ChiTietDonInfo item : info.getListChiTietDonHang()) {
                gg += item.Thanh_Tien;
            }
        }
        textTotalPrice.setText(gg.toString());

        String name = "";
        itemMain = info;
        textEmployee.setText(info.TenNguoiGiao == null ? "" : info.TenNguoiGiao);
        switch (info.getMaTrangThai()) {
            case "CHUAGIAO":
            case "CHOXACNHAN":
                status_book.setBackgroundResource(R.drawable.shape_status_completed);
                viewBooking.setBackgroundResource(R.color.colorLine);
                viewStart.setBackgroundResource(R.drawable.shape_status_remaining);
                viewPending.setBackgroundResource(R.color.colorLine);
                viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                viewFinish.setBackgroundResource(R.color.colorLine);
                viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                buttonCancel.setVisibility(View.VISIBLE);
                break;
            case "DADAT":
                status_book.setBackgroundResource(R.drawable.shape_status_remaining);
                viewBooking.setBackgroundResource(R.color.Blue);
                viewStart.setBackgroundResource(R.drawable.shape_status_completed);
                viewPending.setBackgroundResource(R.color.colorLine);
                viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                viewFinish.setBackgroundResource(R.color.colorLine);
                viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                buttonCancel.setVisibility(View.VISIBLE);
                break;
            case "DANGGIAOHANG":
                status_book.setBackgroundResource(R.drawable.shape_status_remaining);
                viewBooking.setBackgroundResource(R.color.colorLine);
                viewPending.setBackgroundResource(R.color.Blue);
                viewProgress.setBackgroundResource(R.drawable.shape_status_completed);
                viewFinish.setBackgroundResource(R.color.colorLine);
                viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                buttonCancel.setVisibility(View.GONE);
                break;
            case "HOANTHANH":
                status_book.setBackgroundResource(R.drawable.shape_status_remaining);
                viewBooking.setBackgroundResource(R.color.colorLine);
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

        if (info.getListChiTietDonHang().size() > 0) {
            for (ChiTietDonInfo itemI : info.getListChiTietDonHang()) {
                name += " " + itemI.getProduct_Name() + " X " + itemI.getSL() + " ,";
            }
        }
        if (name.length() > 0) {
            textNameProduct.setText(name.substring(0, name.length() - 1));
        }

        if (textNameProduct.getText().length() == 0) {
            viewTwo.setVisibility(View.GONE);
            layoutTwo.setVisibility(View.GONE);
        }

        showProgressDialog(false);

    }

    @Override
    public void GetBookingError(String error) {
        showMessage(error);
    }

    @Override
    public void onCheckBookingSuccess(BookingInfo info) {
        presenterCancel.GetBooking(info.SoPhieuVietTay);
    }

    @Override
    public void onCheckBookingError(String error) {
//        showNoResult();
        checkResult(true);
    }

    @Override
    public void onCancelBookingSuccess() {
        Toast.makeText(getContext(), "Hủy đơn thành công", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void onCancelBookingError(String error) {
        showMessage(error);
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

    private class TestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            String eventName = bundle.getString("change");
            if (eventName.equals("change")) {
                initData();
            }
        }
    }
}
