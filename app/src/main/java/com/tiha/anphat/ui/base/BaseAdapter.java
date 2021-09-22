package com.tiha.anphat.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> dataList;
    Context BASE_CONTEXT;
    public View view;
    OnClickListener clickListener;
    OnLongClickListener longClickListener;

    public BaseAdapter(Context context) {
        this.BASE_CONTEXT = context;
    }

    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayout_id(), viewGroup, false);
        return new BaseViewHolder(view) {
            @Override
            public void getView(View view) {

            }
        };
    }

    public void clear() {
        dataList = new ArrayList<>();
        notifyDataSetChanged();
    }
    public Object getItem(int position){
        return dataList.get(position);
    }

    public abstract int getLayout_id() ;


    public abstract void getView(View view);

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public abstract void onBindViewHold(int position, Object itemView);

    public interface OnClickListener {
        void onClick(View view, int position);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int position);
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