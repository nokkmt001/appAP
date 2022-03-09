package com.tiha.anphatsu.ui.pay.pending;

import com.tiha.anphatsu.data.entities.condition.CancelOrderCondition;
import com.tiha.anphatsu.data.entities.order.BookingInfo;

public interface CancelBookingContract {

    interface View{

        void onCancelBookingSuccess();

        void onCancelBookingError(String error);

        void GetBookingSuccess(BookingInfo info);

        void GetBookingError(String error);
    }

    interface Presenter{
        void CancelBooking(CancelOrderCondition condition);

        void GetBooking(String soCT);
    }

}
