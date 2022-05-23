package com.anphat.supplier.ui.notification;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.ui.base.BaseLMAdapter;

public class NotificationAdapter extends BaseLMAdapter<NotificationMain, NotificationAdapter.ItemRow> {

    public NotificationAdapter(Context context) {
        super(context);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_notification;
    }

    @Override
    protected ItemRow onCreateHolder(View view) {
        return new ItemRow(view);
    }

    @Override
    protected void onBindData(ItemRow holder, NotificationMain item, int position) {
        holder.textTitle.setText(item.TieuDe);
    }

    public class ItemRow extends RecyclerView.ViewHolder {

        TextView textTitle;
        public ItemRow(@NonNull View itemView) {
            super(itemView);
            textTitle = bind(itemView,R.id.textTitle);

        }
    }
}
