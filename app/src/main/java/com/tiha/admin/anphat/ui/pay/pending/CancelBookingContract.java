package com.tiha.admin.anphat.ui.pay.pending;

import com.tiha.admin.anphat.data.entities.condition.CancelOrderCondition;

public interface CancelBookingContract {
    interface View{

        void onCancelBookingSuccess();


        void onCancelBookingError(String error);
    }

    interface Presenter{
        void CancelBooking(CancelOrderCondition condition);
    }
}
