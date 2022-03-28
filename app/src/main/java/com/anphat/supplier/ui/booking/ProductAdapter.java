package com.anphat.supplier.ui.booking;

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
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.network.product.IProductModel;
import com.anphat.supplier.data.network.product.ProductModel;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    ProductModel model;
    BaseEventClick.OnClickListener clickListener;
    List<CartInfo> listAllData = new ArrayList<>();
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
        holder.textName.setText(item.getProductName());
        holder.textCount.setText("Số lượng: "+item.getSoLuong());
        holder.textMoney.setText("Tổng tiền: " +(item.getSoLuong() * item.getDonGia()));
        model.GetImageFromProductID(item.getProductID(), new IProductModel.IGetImageFromProductIDFinishListener() {
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
