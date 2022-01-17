package com.tiha.anphat.ui.transport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.databinding.ItemHistoryBookingBinding;
import com.tiha.anphat.ui.base.BaseTestAdapter;

public class TransportAdapter extends BaseTestAdapter<HistoryBooking, ItemHistoryBookingBinding> {
    ItemHistoryBookingBinding bd;
    @Override
    public void setupViews(View itemView, HistoryBooking item, int position) {

    }

    @Override
    public ItemHistoryBookingBinding getViewBinding(ViewGroup parent, int viewType) {
        return bd = ItemHistoryBookingBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
