package com.anphat.supplier.ui.category;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.ui.base.BaseEventClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetailCAdapter extends RecyclerView.Adapter<DetailCAdapter.ItemRow> {
    List<CategoryNew> listAllData = new ArrayList<>();
    int select_position = -1;
    BaseEventClick.OnClickListener onClickListener;

    public void setSelect_position(int select_position) {
        this.select_position = select_position;
    }

    public void setOnClickListener(BaseEventClick.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<CategoryNew> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add(CategoryNew item, int position) {
        listAllData.add(position, item);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void remove(int position) {
        listAllData.remove(position);
        notifyDataSetChanged();
    }

    public CategoryNew getItem(int position) {
        return listAllData.get(position);
    }

    @NotNull
    @Override
    public ItemRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_test, parent, false);
        return new ItemRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRow holder, int position) {
        CategoryNew info = listAllData.get(position);
        holder.tvTitle.setText(info.title);
        if (select_position == position) {
            holder.layoutColor.setVisibility(View.VISIBLE);
        } else {
            holder.layoutColor.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != listAllData ? listAllData.size() : 0);
    }

    public class ItemRow extends RecyclerView.ViewHolder {
        TextView tvTitle;
        View layoutColor;

        @SuppressLint("NotifyDataSetChanged")
        public ItemRow(@NonNull View view) {
            super(view);
            tvTitle = bind(view, R.id.textCategory);
            layoutColor = bind(view, R.id.layoutColor);
            view.setOnClickListener(v -> {
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