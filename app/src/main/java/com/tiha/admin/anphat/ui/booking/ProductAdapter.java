package com.tiha.admin.anphat.ui.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.order.ChiTietDonInfo;
import com.tiha.admin.anphat.data.network.product.IProductModel;
import com.tiha.admin.anphat.data.network.product.ProductModel;
import com.tiha.admin.anphat.ui.base.BaseEventClick;
import com.tiha.admin.anphat.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    ProductModel model;
    BaseEventClick.OnClickListener clickListener;
    List<ChiTietDonInfo> listAllData = new ArrayList<>();
    Context mContext;

    public void setClickListener(BaseEventClick.OnClickListener listener) {
        this.clickListener = listener;
    }

    public ProductAdapter(Context context) {
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
        holder.textCount.setText("S??? l?????ng: "+item.getSL());
        holder.textMoney.setText("T???ng ti???n: "+item.getThanh_Tien().toString());
        model.GetImageFromProductID(item.getProduct_ID(), new IProductModel.IGetImageFromProductIDFinishListener() {
            @Override
            public void onSuccess(String imageBitmap) {
                if (imageBitmap != null) {
                    Glide.with(mContext).asBitmap()
                            .load(AppUtils.formatStringToBitMap(imageBitmap))
                            .apply(new RequestOptions().override(10, 10))
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable
                                        Transition<? super Bitmap> transition) {
                                    holder.imageView.setImageBitmap(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                } else {
                    holder.imageView.setImageResource(R.drawable.img_no_image);
                }
            }

            @Override
            public void onError(String error) {
                holder.imageView.setImageResource(R.drawable.img_no_image);
            }
        });
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
