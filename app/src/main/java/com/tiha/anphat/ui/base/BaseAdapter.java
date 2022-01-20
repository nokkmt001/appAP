package com.tiha.anphat.ui.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;

    ArrayList<T> listData = new ArrayList<>();
    private boolean isLoadMoreOrRefresh = false;

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void setupViews(View itemView, T item, int position);

    public abstract void setupActions(View itemView, T item, int position);

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOfItem = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        View viewOfLoadMore = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_more, parent, false);
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            return new BaseViewHolder(viewOfLoadMore);
        }
        return new BaseViewHolder(viewOfItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            setupViews(holder.itemView, listData.get(position), position);
            setupActions(holder.itemView, listData.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listData.size() - 1 &&
                isLoadMoreOrRefresh) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }

    public void addLoadingFooter(T o) {
        isLoadMoreOrRefresh = true;
        listData.add(o);
        notifyItemInserted(listData.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadMoreOrRefresh = false;
        int position = listData.size() - 1;
        if (position == -1) return;
        T o = getItem(position);
        if (o != null) {
            listData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<T> data) {
        isLoadMoreOrRefresh = false;
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return listData.get(position);
    }

    public void clearData() {
        listData.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<T> data) {
        isLoadMoreOrRefresh = false;
        listData.addAll(data);
        notifyDataSetChanged();
    }


    public interface OnClick {
        void onClick(View view, int position);
    }

    public interface OnLongClick {
        void onLongClick(View view, int position);
    }

    public interface OnButtonClick {
        void onButtonClick(View view, int position);
    }

    public interface OnSwitchChangeListener {
        void onSwitchChange(View view, int position, boolean isChecked);
    }

    public <t extends View> t bind(View view, int id) {
        return view.findViewById(id);
    }
}
