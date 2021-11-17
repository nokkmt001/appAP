package com.tiha.anphat.ui.product.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;
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
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> implements LoadImageContract.View {
    List<ProductInfo> listAllData;
    Context mContext;
    BaseEventClick.OnClickListener clickListener;
    String category = "";
    LoadImagePresenter presenter;
    String biMap = null;
    ProductModel model;

    public DetailAdapter(Context context, List<ProductInfo> list, String category) {
        this.listAllData = list;
        this.mContext = context;
        this.category = category;
        this.presenter = new LoadImagePresenter(this);
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

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ProductInfo info = listAllData.get(position);
        holder.tvPrice.setText("Giá: "+AppUtils.formatNumber("NO").format(info.getGiaBanLe()));
        holder.tvTitle.setText(info.getProduct_Name() == null ? "" : info.getProduct_Name());
        holder.tvCodeProduct.setText(info.getProduct_ID()==null?"":"Mã sản phẩm: "+info.getProduct_ID());
        holder.tvCategory.setText(info.getCategory_ID()==null?"":"Loại hàng: "+info.getCategory_ID());
        model.GetImageFromProductID(info.getProduct_ID(), new IProductModel.IGetImageFromProductIDFinishListener() {
            @Override
            public void onSuccess(String imageBitmap) {
                if (imageBitmap != null) {
                    info.setImageBitMap(imageBitmap);
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

    @Override
    public void onLoadImageSuccess(String bitMap) {
        this.biMap = bitMap;
    }

    @Override
    public void onLoadImageError(String error) {

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
