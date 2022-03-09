package com.tiha.anphatsu.ui.product.detail;

import static com.tiha.anphatsu.utils.PublicVariables.listAllCategoryMain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.entities.CategoryNew;
import com.tiha.anphatsu.data.entities.ProductInfo;
import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.network.product.ProductModel;
import com.tiha.anphatsu.ui.base.BaseEventClick;
import com.tiha.anphatsu.utils.AppConstants;
import com.tiha.anphatsu.utils.AppUtils;
import com.tiha.anphatsu.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> implements Filterable {
    List<ProductNew> listAllData;
    Context mContext;
    BaseEventClick.OnClickListener clickListener;
    String category;
    String biMap = null;
    ProductModel model;
    int select_position = -1;

    public void setSelect_position(int select_position) {
        this.select_position = select_position;
    }

    public DetailAdapter(Context context, List<ProductNew> list, String category) {
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

    public ProductNew getItem(int position) {
        return listAllData.get(position);
    }

    public void addAll(List<ProductNew> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(ProductNew item) {
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
        final ProductNew info = listAllData.get(position);
        List<CategoryNew> list = listAllCategoryMain;
        holder.tvPrice.setText("Giá: "+AppUtils.formatNumber("NO").format(info.price*(100-info.discount)/100));
        holder.tvTitle.setText(info.title);
        String url = "https://gasanphat.com/" + info.photo;

        Glide.with(mContext)
                .load(url)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageView.setImageBitmap(AppUtils.drawableToBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        if (select_position == position){
            holder.layoutColor.setBackgroundResource(R.drawable.table_content_cell_radius_5_primary);
        } else {
            holder.layoutColor.setBackgroundResource(R.drawable.table_content_cell_radius_5_white);
        }

    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listAllData = PublicVariables.listKM;
                } else {
                    List<ProductNew> filteredList = new ArrayList<>();
                    for (ProductNew userInfo : listAllData) {
                        String one = AppUtils.chuyenCoDauThanhKhongDau(userInfo.title.toLowerCase());
                        String two = AppUtils.chuyenCoDauThanhKhongDau(userInfo.code.toLowerCase());
                        String three = AppUtils.chuyenCoDauThanhKhongDau(charString.toLowerCase());
                        if (one.contains(three)||two.contains(three)){
                            filteredList.add(userInfo);
                        }
                    }
                    listAllData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listAllData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listAllData = (ArrayList<ProductNew>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice,tvCodeProduct,tvCategory;
        ImageView imageView;
        RelativeLayout layoutColor;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvCategory = bind(view,R.id.tvCategory);
            tvCodeProduct = bind(view,R.id.tvCodeProduct);
            tvTitle = bind(view, R.id.tvTitle);
            tvPrice = bind(view, R.id.textPrice);
            imageView = bind(view, R.id.imageView);
             layoutColor = bind(view, R.id.layoutColor);
            view.setOnClickListener(view1 -> {
                if (clickListener != null) {
                    clickListener.onClick(view1, getAdapterPosition());
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
