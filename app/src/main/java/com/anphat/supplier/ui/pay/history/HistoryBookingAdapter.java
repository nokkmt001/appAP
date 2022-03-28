package com.anphat.supplier.ui.pay.history;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.HistoryBooking;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.ChiTietDonInfo;
import com.anphat.supplier.data.network.booking.BookingModel;
import com.anphat.supplier.data.network.booking.IBookingModel;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryBookingAdapter extends RecyclerView.Adapter<HistoryBookingAdapter.ItemHolder> {
    BaseEventClick.OnClickListener  onClick;
    BookingModel model;
    List<HistoryBooking> listAllData = new ArrayList<>();

    public HistoryBookingAdapter(List<HistoryBooking> list){
        this.model = new BookingModel();
        this.listAllData = list;
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
        model.GetBooking(item.SoPhieuVietTay, new IBookingModel.IGetBookingFinish() {
            @Override
            public void onSuccess(BookingInfo info) {
                String gg= "";
                holder.textTT.setText(info.getTenTrangThai());
                if (!info.getMaTrangThai().equals("HOANTHANH")){
                    holder.buttonVote.setVisibility(View.GONE);
                    holder.buttonBooking.setVisibility(View.GONE);
                }

                holder.textNV.setText(info.TenNguoiGiao == null?"":"Nhân viên:  "+info.TenNguoiGiao);
                for (ChiTietDonInfo item:info.getListChiTietDonHang()){
                    gg+=item.getProduct_Name() +" x "+item.getSL()+", ";
                }
                if (gg.length()>0){
                    holder.textTitle.setText(gg.substring(0,gg.length()-2));
                }
            }
            @Override
            public void onError(String error) {

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
        public ItemHolder(@NonNull View view) {
            super(view);
            textNV = bind(view,R.id.textNV);
            textAddress = bind(view,R.id.textAddress);
            textTT = bind(view,R.id.textTT);
            textTitle = bind(view, R.id.textTitle);
            textPrice = bind(view, R.id.textPrice);
            buttonVote = bind(view, R.id.buttonVote);
            buttonBooking = bind(view, R.id.buttonBooking);

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
