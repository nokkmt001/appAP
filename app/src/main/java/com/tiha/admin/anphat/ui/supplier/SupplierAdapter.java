package com.tiha.admin.anphat.ui.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.SupplierInfo;
import com.tiha.admin.anphat.ui.base.BaseEventClick;
import com.tiha.admin.anphat.utils.LetterTileProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SupplierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int select_position = -1;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    private boolean isLoadMoreOrRefresh = false;
    List<SupplierInfo> listAllData;
    private BaseEventClick.OnClickListener onClickListener;
    private final LetterTileProvider mLetterTileProvider;

    public SupplierAdapter(Context context) {
        this.mLetterTileProvider = new LetterTileProvider(context);
    }

    public void setOnClickListener(BaseEventClick.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_LOAD_MORE:
                View viewOfLoadMore = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_load_more, viewGroup, false);
                viewHolder = new LoadingRowHolder(viewOfLoadMore);
                break;
            case VIEW_TYPE_ITEM:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_supplier, viewGroup, false);
                viewHolder = new ItemRowHolder(v);
                break;
        }

        return Objects.requireNonNull(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                try {
                    final ItemRowHolder itemHolder = (ItemRowHolder) holder;
                    SupplierInfo info = listAllData.get(position);
                    if (info.getCompany_Name() != null && info.getCompany_Name().length() > 0) {
                        itemHolder.imageMain.setImageBitmap(mLetterTileProvider.getLetterTile(info.getCompany_Name()));
                    }
                    itemHolder.tvSupplierName.setText(info.getCompany_Name());
                    itemHolder.tvSdt.setText(info.getPhone());
                    itemHolder.tvAddress.setText(info.getDiachighiHD());
                    if (select_position == position) {
                        itemHolder.itemView.setBackgroundResource(R.drawable.table_content_cell_no_radius_primary);
                    } else {
                        itemHolder.itemView.setBackgroundResource(R.color.colorTransparent);
                    }
                } catch (Exception ignored) {

                }

                break;
            case VIEW_TYPE_LOAD_MORE:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return (null != listAllData ? listAllData.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listAllData.size() - 1 &&
                isLoadMoreOrRefresh) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }

    public SupplierInfo getItem(int position) {
        return listAllData.get(position);
    }

    public void addAll(List<SupplierInfo> serviceReportResults) {
        isLoadMoreOrRefresh = false;
        listAllData.addAll(serviceReportResults);
        notifyDataSetChanged();
    }

    public void clear() {
        isLoadMoreOrRefresh = false;
        listAllData = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addLoadingFooter() {
        isLoadMoreOrRefresh = true;
        listAllData.add(new SupplierInfo());
        notifyItemInserted(listAllData.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadMoreOrRefresh = false;
        int position = listAllData.size() - 1;
        if (position == -1) return;
        SupplierInfo o = getItem(position);
        if (o != null) {
            listAllData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView tvSupplierName, tvSdt, tvAddress;
        ImageView imageMain;
        LinearLayout layoutSdt, layoutAddress, layoutMain;

        public ItemRowHolder(View view) {
            super(view);
            tvSupplierName = view.findViewById(R.id.tvSupplierName);
            layoutAddress = view.findViewById(R.id.layoutAddress);
            imageMain = view.findViewById(R.id.imageMain);
            tvAddress = view.findViewById(R.id.tvAddress);
            layoutSdt = view.findViewById(R.id.layoutSdt);
            tvSdt = view.findViewById(R.id.tvSdt);
            layoutMain = view.findViewById(R.id.layoutMain);

            layoutAddress.setOnClickListener(v -> {
                if (onClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onClick(v, getAdapterPosition());
                        select_position = position;
                        notifyDataSetChanged();
                    }
                }
            });
            layoutSdt.setOnClickListener(v -> {
                if (onClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onClick(v, getAdapterPosition());
                        select_position = position;
                        notifyDataSetChanged();
                    }
                }
            });
            layoutMain.setOnClickListener(v -> {
                if (onClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onClick(v, getAdapterPosition());
                        select_position = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    protected static class LoadingRowHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingRowHolder(View itemView) {
            super(itemView);
            this.progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}