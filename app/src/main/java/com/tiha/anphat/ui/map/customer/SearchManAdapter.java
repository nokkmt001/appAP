package com.tiha.anphat.ui.map.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.EmployeeInfo;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchManAdapter extends RecyclerView.Adapter<SearchManAdapter.ItemRowHolder> implements Filterable {
    List<EmployeeInfo> listAllData;
    Context mContext;
    private BaseEventClick.OnClickListener clickListener;
    private int select_position = -1;

    public SearchManAdapter(Context context, List<EmployeeInfo> list) {
        this.mContext = context;
        this.listAllData = list;
    }

    public void setOnClickListener(BaseEventClick.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void clear() {
        listAllData = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addAll(List<EmployeeInfo> serviceReportResults) {
        listAllData.addAll(serviceReportResults);
        notifyDataSetChanged();
    }

    public void setPosition(int position){
        select_position = position;
    }

    public EmployeeInfo getItem(int position) {
        return listAllData.get(position);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ki_thuat, viewGroup, false);
        return new ItemRowHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull ItemRowHolder itemRowHolder, final int position) {
        EmployeeInfo info = listAllData.get(position);
        itemRowHolder.textKiThuat.setText(info.EmployeeName +" - "+info.EmployeeID);
        if (select_position == position) {
            itemRowHolder.itemView.setBackgroundResource(R.drawable.table_content_cell_radius_5_blue);
        } else {
            itemRowHolder.itemView.setBackgroundResource(R.drawable.table_content_cell_radius_5);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != listAllData ? listAllData.size() : 0);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listAllData = PublicVariables.listEmployee;
                } else {
                    List<EmployeeInfo> filteredList = new ArrayList<>();
                    for (EmployeeInfo Info : listAllData) {
                        String one = AppUtils.chuyenCoDauThanhKhongDau(Info.EmployeeName.toLowerCase());
                        String three = AppUtils.chuyenCoDauThanhKhongDau(Info.EmployeeID.toLowerCase());
                        String two =  AppUtils.chuyenCoDauThanhKhongDau(charString.toLowerCase());
                        if (one.contains(two)||three.contains(two)){
                            filteredList.add(Info);
                        }
                    }
                    listAllData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listAllData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listAllData = (List<EmployeeInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView textKiThuat;

        public ItemRowHolder(View view) {
            super(view);
            this.textKiThuat = view.findViewById(R.id.textMan);

            view.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onClick(v, position);
                        select_position = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}