package com.anphat.supplier.ui.pay.pending;

import com.anphat.supplier.data.entities.condition.CancelOrderCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;

public interface CancelBookingContract {

    interface View{

        void onCancelBookingSuccess();

        void onCancelBookingError(String error);

        void GetBookingSuccess(BookingInfo info);

        void GetBookingError(String error);

        void onCheckBookingSuccess(BookingInfo info);

        void onCheckBookingError(String error);
    }

    interface Presenter{
        void CancelBooking(CancelOrderCondition condition);

        void GetBooking(String soCT);

        void checkBooking();
    }

}
