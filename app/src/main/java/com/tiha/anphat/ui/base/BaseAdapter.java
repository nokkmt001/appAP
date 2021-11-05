package com.tiha.anphat.ui.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tiha.anphat.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    private final int PER_PAGE = 10;
    private @LayoutRes
    int layoutId;

    ArrayList<T> listData = new ArrayList<>();
    private Callback callback;
    private SwipeRefreshLayout refreshLayout;
    private boolean isLimitedData = false;
    private boolean isLoadMoreOrRefresh = false;
    private int currentPage = 1;

    public BaseAdapter(
            @LayoutRes int layoutId,
            final Callback callback,
            SwipeRefreshLayout refreshLayout
    ) {
        this.layoutId = layoutId;
        this.callback = callback;
        this.refreshLayout = refreshLayout;
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (!isLoadMoreOrRefresh) {
                        isLoadMoreOrRefresh = true;
                        currentPage = 1;
                        callback.onRefreshListener();
                    }
                }
            });
        }
    }

    public abstract void bindView(View view);

    public abstract void setupViews(View itemView, T item);

    public abstract void setupActions(View itemView, T item, int position);

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
        if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE
                && !isLoadMoreOrRefresh
        ) {
            currentPage++;
            callback.onLoadMoreListener(currentPage);
            isLoadMoreOrRefresh = true;
        } else {
            bindView(holder.itemView);
            setupViews(holder.itemView, listData.get(position));
            setupActions(holder.itemView, listData.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_LOAD_MORE;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (isLimitedData || listData.isEmpty() || listData.size() % PER_PAGE != 0) {
            return listData.size();
        }
        return listData.size() + 1;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<T> data) {
        isLoadMoreOrRefresh = false;
        refreshLayout.setRefreshing(false);

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
    public void addNull(){}

    /**
     * Callback
     */
    public interface Callback {
        void onRefreshListener();

        void onLoadMoreListener(int newPage);
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
