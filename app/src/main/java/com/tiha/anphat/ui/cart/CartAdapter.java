package com.tiha.anphat.ui.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppUtils;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    List<CartInfo> listAllData;
    Context mContext;
    BaseEventClick.OnClickListener clickListener;

    public CartAdapter(Context context, List<CartInfo> list) {
        this.listAllData = list;
        this.mContext = context;
    }

    public void setClickListener(BaseEventClick.OnClickListener listener) {
        this.clickListener = listener;
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public CartInfo getItem(int position) {
        return listAllData.get(position);
    }

    public void addAll(List<CartInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public void remoteItem(int position) {
        listAllData.remove(position);
        notifyDataSetChanged();
    }

    public void add(CartInfo item, int position) {
        listAllData.add(position, item);
        notifyDataSetChanged();
    }

    public List<CartInfo> getAllData() {
        return listAllData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartInfo info = listAllData.get(position);
        holder.tvPrice.setText(info.getDonGia() == null ? "" : AppUtils.formatNumber("NO").format(info.getDonGia()));
        holder.tvTitle.setText(info.getProductName() == null ? "" : info.getProductName());
        holder.textCount.setText(info.getSoLuong() == null ? "" : info.getSoLuong().toString());

    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, textCount;
        ImageView imageMinus, imageAdd;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvTitle = bind(view, R.id.textTitle);
            tvPrice = bind(view, R.id.textPrice);
            imageMinus = bind(view, R.id.imageMinus);
            imageAdd = bind(view, R.id.imageAdd);
            textCount = bind(view, R.id.textCount);

            imageMinus.setOnClickListener(view12 -> {
                if (clickListener != null) {
                    clickListener.onClick(view12, getAdapterPosition());
                }
            });
            imageAdd.setOnClickListener(view1 -> {
                if (clickListener != null) {
                    clickListener.onClick(view1, getAdapterPosition());
                }
            });
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
