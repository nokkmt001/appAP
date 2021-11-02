package com.tiha.anphat.ui.booking;

import com.tiha.anphat.data.entities.order.BookingInfo;

public interface BookingContract {

    interface View{
        void GetBookingSuccess(BookingInfo info);

        void GetBookingError(String error);
    }

    interface Presenter{
        void GetBooking(String soCT);
    }
}
