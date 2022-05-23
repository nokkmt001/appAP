package com.anphat.supplier.ui.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.ProductNew;
import com.bumptech.glide.Glide;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.ui.base.BaseEventClick;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    BaseEventClick.OnClickListener clickListener;
    List<CartInfo> listAllData = new ArrayList<>();
    Context mContext;

    public void setClickListener(BaseEventClick.OnClickListener listener) {
        this.clickListener = listener;
    }

    public ProductAdapter(Context context) {
        this.mContext = context;
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public void addData(CartInfo info) {
        listAllData.add(info);
        notifyDataSetChanged();
    }

    public CartInfo getItem(int position) {
        return listAllData.get(position);
    }

    public void addAll(List<CartInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public List<CartInfo> getListAllData() {
        return listAllData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_booking, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        CartInfo item = listAllData.get(position);
        for (ProductNew productNew : AppPreference.getAllProduct()) {
            if (productNew.code.equals(item.ProductID)) {
                String url = "https://gasanphat.com/" + productNew.photo;
                Glide.with(mContext)
                        .load(url)
                        .error(R.drawable.img_no_image)
                        .placeholder(R.drawable.img_no_image)
                        .override(300, 300)
                        .into(holder.imageView);
            }
        }
        holder.textName.setText(item.getProductName());
        holder.textCount.setText("Số lượng: " + item.getSoLuong());
        holder.textMoney.setText("Tổng tiền: " + (item.getSoLuong() * item.getDonGia()));

    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textCount, textMoney;
        ImageView imageView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textName = bind(view, R.id.textName);
            textCount = bind(view, R.id.textCount);
            textMoney = bind(view, R.id.textMoney);
            imageView = bind(view, R.id.imageMain);
            view.setOnClickListener(view1 -> {
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
