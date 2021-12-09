package com.tiha.anphat.ui.pay.pending;

import com.tiha.anphat.data.entities.condition.CancelOrderCondition;
import com.tiha.anphat.data.entities.order.BookingInfo;

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
