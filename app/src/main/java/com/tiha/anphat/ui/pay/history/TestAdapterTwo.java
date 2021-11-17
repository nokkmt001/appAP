package com.tiha.anphat.ui.pay.history;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.network.booking.BookingModel;
import com.tiha.anphat.data.network.booking.IBookingModel;
import com.tiha.anphat.ui.base.BaseTestAdapter;
import com.tiha.anphat.utils.AppUtils;

import java.util.logging.Handler;

public class TestAdapterTwo extends BaseTestAdapter<HistoryBooking> {

    TextView textTitle, textPrice, textTT, textAddress;
    Button buttonVote, buttonBooking;
    BaseTestAdapter.OnClick onClick;
    BookingModel model;
    Context mContext;

    public TestAdapterTwo(Context context) {
        this.model = new BookingModel();
        this.mContext = context;
    }

    public void setOnClick(BaseTestAdapter.OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_history_booking;
    }

    @Override
    public void setupViews(View view, HistoryBooking item,final int position) {
        textAddress = bind(view, R.id.textAddress);
        textTT = bind(view, R.id.textTT);
        textTitle = bind(view, R.id.textTitle);
        textPrice = bind(view, R.id.textPrice);
        buttonVote = bind(view, R.id.buttonVote);
        buttonBooking = bind(view, R.id.buttonBooking);

        view.setBackgroundResource(R.color.colorLine);
        synchronized (mContext){
            textTitle.setText(item.getSoCt());
            textPrice.setText(AppUtils.formatNumber("NO").format(item.getThanhTien()));
            model.GetBooking(item.getSoCt(), new IBookingModel.IGetBookingFinish() {
                @Override
                public void onSuccess(BookingInfo info) {
                    textTT.setText(info.getTenTrangThai());
                    textAddress.setText(info.getDiachigiaohang());
                }

                @Override
                public void onError(String error) {

                }
            });
        }

        buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    onClick.onClick(view, position);
                    notifyDataSetChanged();
                }
            }
        });

    }

}
