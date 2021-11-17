package com.tiha.anphat.ui.pay.history;

import com.tiha.anphat.data.entities.HistoryBooking;

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
