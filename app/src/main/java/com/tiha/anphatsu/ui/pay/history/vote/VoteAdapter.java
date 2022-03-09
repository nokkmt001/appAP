package com.tiha.anphatsu.ui.pay.history.vote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.entities.ReasonEvaluate;

import java.util.ArrayList;
import java.util.List;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ItemRH> {
    List<ReasonEvaluate> listChoose = new ArrayList<>();
    List<ReasonEvaluate> listAllData = new ArrayList<>();

    public void addAll(List<ReasonEvaluate> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        listAllData.clear();
        notifyDataSetChanged();
    }

    public List<ReasonEvaluate> getListChoose(){
        return listChoose;
    }

    @NonNull
    @Override
    public ItemRH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vote, parent, false);
        return new ItemRH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRH holder, int position) {
        final ReasonEvaluate info = listAllData.get(position);
        holder.text.setText(info.getTenLyDo());
        holder.layoutColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!info.getCheck()) {
                    info.setCheck(true);
                    listChoose.add(info);
                    holder.layoutColor.setBackgroundResource(R.drawable.table_content_cell_no_radius_primary);
                    holder.text.setBackgroundResource(R.color.colorTransparent);
                } else {
                    if(listChoose.size()>0){
                        for (int i = 0; i < listChoose.size(); i++) {
                            if (info.getLyDoDanhGiaSaoID().equals(listChoose.get(i).getLyDoDanhGiaSaoID())) {
                                listChoose.remove(i);
                            }
                        }
                    }

                    info.setCheck(false);
                    holder.layoutColor.setBackgroundResource(R.color.colorTransparent);
                    holder.text.setBackgroundResource(R.color.back_ground_main);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != listAllData ? listAllData.size() : 0);
    }

    public class ItemRH extends RecyclerView.ViewHolder {
        LinearLayout layoutColor;
        TextView text;

        public ItemRH(@NonNull View itemView) {
            super(itemView);
            layoutColor = itemView.findViewById(R.id.layoutColor);
            text = itemView.findViewById(R.id.textVote);
        }
    }
}
