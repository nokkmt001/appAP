package com.tiha.anphat.ui.product.detail;

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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.network.product.ProductModel;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppUtils;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>  {
    List<ProductInfo> listAllData;
    Context mContext;
    BaseEventClick.OnClickListener clickListener;
    String category;
    String biMap = null;
    ProductModel model;

    public DetailAdapter(Context context, List<ProductInfo> list, String category) {
        this.listAllData = list;
        this.mContext = context;
        this.category = category;
        this.model = new ProductModel();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ProductInfo info = listAllData.get(position);
        holder.tvPrice.setText("Giá: "+AppUtils.formatNumber("NO").format(info.getGiaBanLe()));
        holder.tvTitle.setText(info.getProduct_Name() == null ? "" : info.getProduct_Name());
        holder.tvCodeProduct.setText(info.getProduct_ID()==null?"":"Mã sản phẩm: "+info.getProduct_ID());
        holder.tvCategory.setText(info.getCategory_ID()==null?"":"Loại hàng: "+info.getCategory_ID());
        String url = "https://i.ibb.co/ZTVvwRc/gas-test.png";

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.imageView.setImageBitmap(bitmap);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        Picasso.with(mContext).load(url).into(target);
    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice,tvCodeProduct,tvCategory;
        ImageView imageView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvCategory = bind(view,R.id.tvCategory);
            tvCodeProduct = bind(view,R.id.tvCodeProduct);
            tvTitle = bind(view, R.id.tvTitle);
            tvPrice = bind(view, R.id.textPrice);
            imageView = bind(view, R.id.imageView);
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
