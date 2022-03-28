package com.anphat.supplier.ui.pay.history;

import com.anphat.supplier.data.entities.HistoryBooking;

import java.util.List;

public interface HistoryBookingContract {
    interface View {
        void onGetListHistoryBookingSuccess(List<HistoryBooking> list);

        void onGetListHistoryBookingError(String error);
    }

    interface Presenter {
        void GetListHistoryBooking();
    }
}
