package com.tiha.anphat.ui.pay.history;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.entities.order.ChiTietDonInfo;
import com.tiha.anphat.data.network.booking.BookingModel;
import com.tiha.anphat.data.network.booking.IBookingModel;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppUtils;

import java.util.List;

public class HistoryBookingAdapter extends RecyclerView.Adapter<HistoryBookingAdapter.ItemHolder> {
    BaseEventClick.OnClickListener  onClick;
    BookingModel model;
    List<HistoryBooking> listAllData;

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

    public void addAll(List<HistoryBooking> list) {
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

        model.GetBooking(item.getSoCt(), new IBookingModel.IGetBookingFinish() {
            @Override
            public void onSuccess(BookingInfo info) {
                String gg= "";
                holder.textTT.setText(info.getTenTrangThai());
                holder.textAddress.setText(info.getDiachigiaohang());
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
        TextView textTitle,textPrice, textTT,textAddress;
        Button buttonVote, buttonBooking;
        public ItemHolder(@NonNull View view) {
            super(view);
            textAddress = bind(view,R.id.textAddress);
            textTT = bind(view,R.id.textTT);
            textTitle = bind(view, R.id.textTitle);
            textPrice = bind(view, R.id.textPrice);
            buttonVote = bind(view, R.id.buttonVote);
            buttonBooking = bind(view, R.id.buttonBooking);

            buttonVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClick!=null){
                        onClick.onClick(view,getAdapterPosition());
                    }
                }
            });
            buttonBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClick!=null){
                        onClick.onClick(view,getAdapterPosition());
                    }
                }
            });
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

}
