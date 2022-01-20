package com.tiha.anphat.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.ui.base.BaseEventClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryAddAdapter extends RecyclerView.Adapter<CategoryAddAdapter.ItemRow> {
    List<CategoryInfo> listAllData;
    int select_position = -1;
    BaseEventClick.OnClickListener onClickListener;

    public void setSelect_position(int select_position) {
        this.select_position = select_position;
    }

    public void setOnClickListener(BaseEventClick.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void clear() {
        listAllData = new ArrayList<>();
        listAllData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<CategoryInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(CategoryInfo item, int position) {
        listAllData.add(position, item);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        listAllData.remove(position);
        notifyDataSetChanged();
    }

    public CategoryInfo getItem(int position) {
        return listAllData.get(position);
    }

    public List<CategoryInfo> getListAllData() {
        return listAllData;
    }

    @NotNull
    @Override
    public ItemRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_main, parent, false);
        return new ItemRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRow holder, int position) {
        CategoryInfo info = listAllData.get(position);
        holder.tvTitle.setText(info.getLoaihang());
        holder.imageAdd.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class ItemRow extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imageAdd;

        public ItemRow(@NonNull View view) {
            super(view);
            tvTitle = bind(view, R.id.textMain);
            imageAdd = bind(view, R.id.imageClose);

            view.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, getAdapterPosition());
                        select_position = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });
            imageAdd.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onClick(v, getAdapterPosition());
                    select_position = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
