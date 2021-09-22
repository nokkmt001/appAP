package com.tiha.anphat.ui.product.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;

import java.util.List;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    List<ProductInfo> listAllData;
    Context mContext;

    public DetailAdapter(Context context, List<ProductInfo> list) {
        this.listAllData = list;
        this.mContext = context;
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ProductInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(ProductInfo item) {
        listAllData.add(item);
        notifyItemInserted(listAllData.size() + 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductInfo info = listAllData.get(position);
        holder.tvPrice.setText(info.getGiaBanLe().toString());
        holder.imageView.setImageResource(R.drawable.img_no_image);
        holder.tvTitle.setText(info.getProduct_Name()== null?"":info.getProduct_Name());
    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        ImageView imageView;
        public MyViewHolder(@NonNull View view) {
            super(view);
            tvTitle = bind(view, R.id.tvTitle);
            tvPrice = bind(view, R.id.textPrice);
            imageView = bind(view, R.id.imageView);
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
