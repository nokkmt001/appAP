package com.anphat.supplier.ui.pay.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.ChiTietDonInfo;
import com.anphat.supplier.data.network.api.ApiResponseSbke;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.RetrofitWsbke;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.utils.AppUtils;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryBookingAdapter extends RecyclerView.Adapter<HistoryBookingAdapter.ItemHolder> {
    BaseEventClick.OnClickListener  onClick;
    List<HistoryBooking> listAllData;
    String url = "";
    Context mContext;
    API server = RetrofitWsbke.createService(API.class);

    public HistoryBookingAdapter(List<HistoryBooking> list, Context context){
        this.listAllData = list;
        this.mContext = context;
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public HistoryBooking getItem(int position) {
        return listAllData.get(position);
    }

    @SuppressLint("NewApi")
    public void addAll(List<HistoryBooking> list) {
        Collections.sort(list, Comparator.comparing(HistoryBooking::getNgay));
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_booking, parent, false);
        return new ItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        HistoryBooking item = listAllData.get(position);
        holder.textPrice.setText("Tổng tiền:  "+AppUtils.formatNumber("NO").format(item.getThanhTien()));
        holder.buttonBooking.setVisibility(View.GONE);
        List<ProductNew> list = AppPreference.getAllProduct();

        server.GetDetailHistoryDefault(item.SoPhieuVietTay).enqueue(new Callback<ApiResponseSbke<BookingInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseSbke<BookingInfo>> call, @NonNull Response<ApiResponseSbke<BookingInfo>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    BookingInfo info = response.body().Data;
                    String gg= "";
                    holder.textTT.setText(info.getTenTrangThai());
                    if (!info.getMaTrangThai().equals("HOANTHANH")){
                        holder.buttonVote.setVisibility(View.GONE);
                        holder.buttonBooking.setVisibility(View.GONE);
                    }

                    holder.textNV.setText(info.TenNguoiGiao == null?"":"Nhân viên:  "+info.TenNguoiGiao);
                    for (ChiTietDonInfo item:info.getListChiTietDonHang()){
                        for (ProductNew infoImage: list){
                            if (infoImage.code.equals(item.getProduct_ID())){
                                url = "https://gasanphat.com/" + infoImage.photo;
                                Glide.with(mContext)
                                        .load(url)
                                        .error(R.drawable.img_no_image)
                                        .override(500,500)
                                        .into(holder.imageMain);
                            }
                        }
                        gg+=item.getProduct_Name() +" x "+item.getSL()+", ";
                    }
                    if (gg.length()>0){
                        holder.textTitle.setText(gg.substring(0,gg.length()-2));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseSbke<BookingInfo>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public void setOnClick(BaseEventClick.OnClickListener onClick) {
        this.onClick = onClick;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView textTitle,textPrice, textTT,textAddress, textNV;
        Button buttonVote, buttonBooking;
        ImageView imageMain;
        public ItemHolder(@NonNull View view) {
            super(view);
            textNV = bind(view,R.id.textNV);
            textAddress = bind(view,R.id.textAddress);
            textTT = bind(view,R.id.textTT);
            textTitle = bind(view, R.id.textTitle);
            textPrice = bind(view, R.id.textPrice);
            buttonVote = bind(view, R.id.buttonVote);
            buttonBooking = bind(view, R.id.buttonBooking);
            imageMain = bind(view,R.id.imageMain);
            buttonVote.setOnClickListener(view1 -> {
                if (onClick!=null){
                    onClick.onClick(view1,getAdapterPosition());
                }
            });
            buttonBooking.setOnClickListener(view12 -> {
                if (onClick!=null){
                    onClick.onClick(view12,getAdapterPosition());
                }
            });
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
