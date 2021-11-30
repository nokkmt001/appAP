package com.tiha.anphat.ui.product.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ProductInfo> listAllData;
    Context mContext;
    BaseEventClick.OnClickListener clickListener;
    String category = "";
    String biMap = null;
    Boolean isLive = false;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    private boolean isLoadMoreOrRefresh = false;

    public DetailAdapter(Context context, List<ProductInfo> list, String category) {
        this.listAllData = list;
        this.mContext = context;
        this.category = category;
    }

    public void setClickListener(BaseEventClick.OnClickListener listener) {
        this.clickListener = listener;
    }

    public void clear() {
        isLoadMoreOrRefresh = false;
        listAllData.clear();
        notifyDataSetChanged();
    }

    public ProductInfo getItem(int position) {
        return listAllData.get(position);
    }

    public void addAll(List<ProductInfo> list) {
        isLoadMoreOrRefresh = false;
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(ProductInfo item) {
        isLoadMoreOrRefresh = false;
        listAllData.add(item);
        notifyItemInserted(listAllData.size() + 1);
    }

    public void addLoadingFooter() {
        isLoadMoreOrRefresh = true;
        listAllData.add(new ProductInfo());
        notifyItemInserted(listAllData.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadMoreOrRefresh = false;
        int position = listAllData.size() - 1;
        if (position == -1) return;
        ProductInfo o = getItem(position);
        if (o != null) {
            listAllData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            View viewOfLoadMore = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_more, parent, false);
            viewHolder = new LoadingRowHolder(viewOfLoadMore);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            viewHolder = new MyViewHolder(v);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder itemRowHolder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            final MyViewHolder holder = (MyViewHolder) itemRowHolder;
            final ProductInfo info = listAllData.get(position);
            holder.tvPrice.setText("Giá: " + AppUtils.formatNumber("NO").format(info.getGiaBanLe()));
            holder.tvTitle.setText(info.getProduct_Name() == null ? "" : info.getProduct_Name());
            holder.tvCodeProduct.setText(info.getProduct_ID() == null ? "" : "Mã sản phẩm: " + info.getProduct_ID());
            holder.tvCategory.setText(info.getCategory_ID() == null ? "" : "Loại hàng: " + info.getCategory_ID());
//            String gg = "http://crm.tiha.vn/Resources/Images/Users/Thien-Nguyen-thien.png";
            String gg = "http://i.ibb.co/HqX8tMM/people.png";

/**        if (info.getDiachifileanh() == null) {
//            holder.imageView.setImageResource(R.drawable.img_no_image);
//        } else { **/
            Glide.with(mContext)
                    .load(gg)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.imageView.setImageBitmap(AppUtils.drawableToBitmap(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });

        }


    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listAllData.size() - 1 &&
                isLoadMoreOrRefresh) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvCodeProduct, tvCategory;
        ImageView imageView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvCategory = bind(view, R.id.tvCategory);
            tvCodeProduct = bind(view, R.id.tvCodeProduct);
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

    protected class LoadingRowHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingRowHolder(View itemView) {
            super(itemView);
            this.progressBar = itemView.findViewById(R.id.progressBar);

        }

    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }
}
