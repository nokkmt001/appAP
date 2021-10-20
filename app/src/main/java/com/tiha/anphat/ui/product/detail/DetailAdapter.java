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
import com.tiha.anphat.ui.base.BaseEventClick;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    List<ProductInfo> listAllData;
    Context mContext;
    BaseEventClick.OnClickListener clickListener;
    String category = "";

    public DetailAdapter(Context context, List<ProductInfo> list, String category) {
        this.listAllData = list;
        this.mContext = context;
        this.category = category;
    }

    public void setClickListener(BaseEventClick.OnClickListener listener) {
        this.clickListener = listener;
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public ProductInfo getItem(int position) {
        return listAllData.get(position);
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
        holder.tvPrice.setText(String.valueOf(info.getGiaBanLe()));
        holder.tvTitle.setText(info.getProduct_Name() == null ? "" : info.getProduct_Name());
        switch (category) {
            case "PHUKIEN":
                holder.imageView.setImageResource(R.drawable.phu_kien);
                break;
            case "BRN_2715GN":
                holder.imageView.setImageResource(R.drawable.gas_stoves);
                break;
            case "PCCC":
                holder.imageView.setImageResource(R.drawable.pccc);
                break;
            case "VO":
                holder.imageView.setImageResource(R.drawable.rice);
                break;
            case "BBINH":
                holder.imageView.setImageResource(R.drawable.rice);
                break;
            case "GAO":
                holder.imageView.setImageResource(R.drawable.rice);
                break;
            case "GAS":
                holder.imageView.setImageResource(R.drawable.gas_cylen);
                break;
            case "LUA":
                holder.imageView.setImageResource(R.drawable.rice);
                break;
            case "NEP":
                holder.imageView.setImageResource(R.drawable.gao_nep_bac);
                break;
            case "TAM":
                holder.imageView.setImageResource(R.drawable.gao_tam_tai_nguyen);
                break;
            case "TA":
                holder.imageView.setImageResource(R.drawable.unnamed);
                break;
            case "KHUYENMAI":
                holder.imageView.setImageResource(R.drawable.khuyen_mai);
                break;
            default:
                break;
        }
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
