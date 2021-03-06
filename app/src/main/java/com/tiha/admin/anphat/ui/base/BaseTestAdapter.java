package com.tiha.admin.anphat.ui.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.tiha.admin.anphat.databinding.RowLoadMoreBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTestAdapter<T , B extends ViewBinding> extends RecyclerView.Adapter<BindingViewHolder> {
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    protected B binding;
    ArrayList<T> listData = new ArrayList<>();
    private boolean isLoadMoreOrRefresh = false;

    public abstract void setupViews(View itemView, T item, int position);

    public abstract B getViewBinding( ViewGroup parent, int viewType);
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE){
            return new BindingViewHolder(RowLoadMoreBinding.inflate(LayoutInflater.from(parent.getContext())));
        }
        return new BindingViewHolder(getViewBinding(parent,viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder  holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            setupViews(holder.itemView, listData.get(position), position);
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
