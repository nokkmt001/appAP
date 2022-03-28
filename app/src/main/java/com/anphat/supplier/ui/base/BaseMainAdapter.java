package com.anphat.supplier.ui.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMainAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    private final int PER_PAGE = 10;
    private @LayoutRes
    int layoutId;
    ArrayList<T> listData = new ArrayList<>();
    private boolean isLimitedData = false;
    private boolean isLoadMoreOrRefresh = false;
    private boolean isLoadingAdded = false;

    private int currentPage = 1;
    private Callback callback;

    public BaseMainAdapter(@LayoutRes int layoutId, final Callback callback){
        this.layoutId = layoutId;
        this.callback = callback;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOfItem = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        View viewOfLoadMore = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_more, parent, false);
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            return new BaseViewHolder(viewOfLoadMore);
        }
        return new BaseViewHolder(viewOfItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
            currentPage++;
            callback.onLoadMoreListener(currentPage);
            isLoadMoreOrRefresh = true;
        } else {
            bindView(holder.itemView);
            setupViews(holder.itemView, listData.get(position));
            setupActions(holder.itemView, listData.get(position), position);
        }
    }

    public abstract void bindView(View view);

    public abstract void setupViews(View itemView, T item);

    public abstract void setupActions(View itemView, T item, int position);

    @Override
    public int getItemViewType(int position) {
//        if (position == listData.size()) {
//            return VIEW_TYPE_LOAD_MORE;
//        }
//        return VIEW_TYPE_ITEM;

        return (position == listData.size() - 1) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;

    }

    @Override
    public int getItemCount() {
        if (listData==null){
            return 0;
        }
        return listData.size();
    }

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

    public interface Callback {
        void onLoadMoreListener(int newPage);
    }
}
