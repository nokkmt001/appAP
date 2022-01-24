package com.tiha.admin.anphat.ui.pay.pending;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.admin.anphat.ui.base.BaseFragment;
import com.tiha.admin.anphat.ui.booking.BookingContract;
import com.tiha.admin.anphat.ui.booking.BookingPresenter;
import com.tiha.admin.anphat.ui.booking.ProductAdapter;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.condition.CancelOrderCondition;
import com.tiha.admin.anphat.data.entities.order.BookingInfo;
import com.tiha.admin.anphat.utils.PublicVariables;

public class PendingFragment extends BaseFragment implements BookingContract.View, CancelBookingContract.View {
    BookingPresenter presenter;
    CancelBookingPresenter presenterCancel;
    ProductAdapter adapter;
    RecyclerView rcl;
    TextView textAddress;
    View viewFinish, viewPending, viewProgress, viewEnd, viewStart;
    Button buttonCancel;
    String gg = "";

    @Override

    protected int getLayoutID() {
        return R.layout.fragment_pending;
    }

    @Override
    protected void initView(View view) {
        presenterCancel = new CancelBookingPresenter(this);

        adapter = new ProductAdapter(getActivity());
        rcl = bind(view, R.id.rcl);
        rcl.setAdapter(adapter);

        textAddress = bind(view, R.id.textAddress);
        viewPending = bind(view, R.id.viewPending);
        viewFinish = bind(view, R.id.viewFinish);
        viewProgress = bind(view, R.id.status_progress);
        viewEnd = bind(view, R.id.status_end);
        viewStart = bind(view, R.id.status_start);
        buttonCancel = bind(view, R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gg = "";
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_cancel_booking, null);
                final EditText editText = view.findViewById(R.id.etComment);
                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                builder.setCancelable(false)
                        .setTitle("Bạn chắc chắn từ chối công việc này.")
                        .setView(view)
                        .setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gg = editText.getText().toString();
                                CancelOrderCondition condition = new CancelOrderCondition();
                                condition.setLyDoHuy(gg);
                                condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
                                condition.setSoDonHang("CTY211101001");
                                presenterCancel.CancelBooking(condition);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new BookingPresenter(this);
        presenter.GetBooking("CTY211101001");
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
                buttonCancel.setVisibility(View.VISIBLE);
                break;
            case "DANGGIAOHANG":
                viewPending.setBackgroundResource(R.color.Blue);
                viewProgress.setBackgroundResource(R.drawable.shape_status_completed);
                viewFinish.setBackgroundResource(R.color.Blue);
                viewEnd.setBackgroundResource(R.drawable.shape_status_remaining);
                buttonCancel.setVisibility(View.VISIBLE);
                break;
            case "HOANTHANH":
                viewPending.setBackgroundResource(R.color.colorLine);
                viewProgress.setBackgroundResource(R.drawable.shape_status_remaining);
                viewFinish.setBackgroundResource(R.color.Blue);
                viewEnd.setBackgroundResource(R.drawable.shape_status_completed);
                buttonCancel.setVisibility(View.VISIBLE);
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

    @Override
    public void onCancelBookingSuccess() {

    }

    @Override
    public void onCancelBookingError(String error) {

    }
}
