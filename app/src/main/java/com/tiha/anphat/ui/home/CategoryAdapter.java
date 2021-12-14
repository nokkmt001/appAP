package com.tiha.anphat.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.databinding.ItemCategoryBinding;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.ui.base.BaseTestAdapter;
import com.tiha.anphat.ui.product.detail.DetailAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemRow> {
    List<CategoryInfo> listAllData;
    int select_position= -1;
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

    public CategoryInfo getItem(int position){
        return listAllData.get(position);
    }

    @NotNull
    @Override
    public ItemRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ItemRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRow holder, int position) {
        CategoryInfo info = listAllData.get(position);
        holder.tvTitle.setText(info.getLoaihang());

        if (select_position == position){
            holder.layoutColor.setBackgroundResource(R.drawable.table_content_cell_bg_default);
        } else {
            holder.layoutColor.setBackgroundResource(R.color.colorTransparent);

        }
    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class ItemRow extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ConstraintLayout layoutColor;

        public ItemRow(@NonNull View view) {
            super(view);
            tvTitle = bind(view, R.id.textCategory);
            layoutColor = bind(view, R.id.layoutColor);
            view.setOnClickListener(v -> {
                if (getAdapterPosition()!=RecyclerView.NO_POSITION){
                    if (onClickListener!=null){
                        onClickListener.onClick(v,getAdapterPosition());
                        select_position = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
