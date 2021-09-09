package com.tiha.anphat.ui.base;

import android.view.View;

public abstract class BaseRecyclerViewEvent {
    public interface OnClickListener {
        void onClick(View view, int position);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int position);
    }

    public interface OnSwitchChangeListener {
        void onSwitchChange(View view, int position, boolean isChecked);
    }

    public interface OnDataSetChangedListener {
        void onDataSetChanged();
    }
}
