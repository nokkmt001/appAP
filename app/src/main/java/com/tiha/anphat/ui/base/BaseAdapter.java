package com.tiha.anphat.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {

    public int layout_id;
    protected List<?> dataList = new ArrayList<>();
    Context BASE_CONTEXT;
    public View view;

    public BaseAdapter(Context context) {
        this.BASE_CONTEXT = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout_id, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        onBindViewHold(position, dataList.get(position));
    }

    public void clear(){
        dataList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public abstract void getView(View view);

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public abstract void onBindViewHold(int position, Object itemView);

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            getView(view);
        }

    }

    public <T extends View> T bind(int id) {
        return view.findViewById(id);
    }


}

//public class Adapter extends BaseAdapter {
//
//    TextView tv;
//
//    Adapter(Context context, ArrayList<String> arrayList){
//        super(context);
//        dataList=arrayList;
//        layout_id= R.layout.;
//
//    }
//
//
//    @Override
//    public View getView(View view) {
//        tv = bind(R.id.tv);
//        return view;
//    }
//
//    @Override
//    public void onBindViewHold( final int position, Object itemView) {
//
//        String text=(String) itemView;
//        Log.e("tv",tv.toString());
//        tv.setText(text);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(BASE_CONTEXT, ""+position, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//
//
//}