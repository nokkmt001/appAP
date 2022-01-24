package com.tiha.admin.anphat.ui.product.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.tiha.admin.anphat.data.entities.ProductInfo;
import com.tiha.admin.anphat.databinding.ItemProductBinding;
import com.tiha.admin.anphat.ui.base.BaseTestAdapter;
import com.tiha.admin.anphat.utils.AppUtils;

public class TestAdapter extends BaseTestAdapter<ProductInfo, ItemProductBinding> {
    ItemProductBinding bd;
    Context mContext;

    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public TestAdapter(Context context){
        mContext = context;
    }
    @Override
    public void setupViews(View itemView, ProductInfo info, final int position) {
        bd.tvCategory.setText(info.getCategory_ID() == null ? "" : "Loại hàng: " + info.getCategory_ID());
        bd.tvCodeProduct.setText(info.getProduct_ID() == null ? "" : "Mã sản phẩm: " + info.getProduct_ID());
        bd.tvTitle.setText(info.getProduct_Name() == null ? "" : info.getProduct_Name());
        bd.textPrice.setText("Giá: " + AppUtils.formatNumber("NO").format(info.getGiaBanLe()));
        String gg = "http://crm.tiha.vn/Resources/Images/Users/Thien-Nguyen-thien.png";
        Glide.with(mContext)
                .load(gg)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
//                    .apply(new RequestOptions()
//                            .placeholder(R.drawable.img_no_image).error(R.drawable.img_no_image))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        bd.imageView.setImageBitmap(AppUtils.drawableToBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        itemView.setOnClickListener(v -> {
            if (onClick!=null){
                onClick.onClick(v,position);
            }
        });
    }

    @Override
    public ItemProductBinding getViewBinding(ViewGroup parent, int viewType) {
        return bd = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
