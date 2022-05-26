package com.anphat.supplier.ui.notification;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.ui.base.BaseLMAdapter;
import com.anphat.supplier.utils.AppUtils;

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
        holder.textDetail.setText(item.NoiDung);
        holder.textTime.setText(AppUtils.formatDateToString(item.NgayGio, "dd/MM/yy HH:mm"));
    }

    public class ItemRow extends RecyclerView.ViewHolder {
        TextView textTitle, textDetail, textTime;
        ConstraintLayout layoutMain;

        public ItemRow(@NonNull View itemView) {
            super(itemView);
            textTitle = bind(itemView, R.id.textTitle);
            textDetail = bind(itemView, R.id.textDetail);
            textTime = bind(itemView, R.id.textTime);
            layoutMain = bind(itemView, R.id.layoutMain);
            layoutMain.setOnClickListener(view -> {
                if (mItemClickListener != null) {
                    int position = getAdapterPosition();
                    mItemClickListener.onItemClick(getItem(position), view, position);
                }
            });
        }
    }
}
