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

import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.network.product.IProductModel;
import com.bumptech.glide.Glide;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.order.ChiTietDonInfo;
import com.anphat.supplier.data.network.product.ProductModel;
import com.anphat.supplier.ui.base.BaseEventClick;

import java.util.ArrayList;
import java.util.List;

public class ProductAfterAdapter extends RecyclerView.Adapter<ProductAfterAdapter.MyViewHolder> {
    ProductModel model;
    BaseEventClick.OnClickListener clickListener;
    List<ChiTietDonInfo> listAllData = new ArrayList<>();
    Context mContext;
    String url = "";

    public void setClickListener(BaseEventClick.OnClickListener listener) {
        this.clickListener = listener;
    }

    public ProductAfterAdapter(Context context) {
        this.mContext = context;
        this.model = new ProductModel();
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public ChiTietDonInfo getItem(int position) {
        return listAllData.get(position);
    }

    public void addAll(List<ChiTietDonInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
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
        ChiTietDonInfo item = listAllData.get(position);
        holder.textName.setText(item.getProduct_Name());
        holder.textCount.setText("Số lượng:  " + item.getSL());
        holder.textPrice.setText("Đơn giá:  " + item.dongia);
        holder.textDisCount.setText("Giảm giá:  " + item.GiatriDiscount);
        holder.textMoney.setText("Thành tiền:  " + (item.getSL() * item.getDongia()));
        model.GetProductNew(item.getProduct_ID(), new IProductModel.IGetProductNewFinish() {
            @Override
            public void onSuccess(ProductNew info) {
                url = "https://gasanphat.com/" + info.photo;
                Glide.with(mContext)
                        .load(url)
                        .error(R.drawable.img_no_image)
                        .into(holder.imageView);
            }

            @Override
            public void onError(String error) {
                Glide.with(mContext)
                        .load(url)
                        .error(R.drawable.img_no_image)
                        .into(holder.imageView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textCount, textMoney, textPrice, textDisCount;
        ImageView imageView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textDisCount = bind(view, R.id.textDisCount);
            textPrice = bind(view, R.id.textPrice);
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
