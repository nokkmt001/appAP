package com.anphat.supplier.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * user for load more view
 */
public abstract class BaseLMAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected List<E> mList;
    public OnItemClickListener<E> mItemClickListener;
    public OnItemLongClickListener<E> mOnItemLongClickListener;
    public OnButtonClickListener<E> onButtonClickListener;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    private boolean isLoadMoreOrRefresh = false;

    public BaseLMAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_LOAD_MORE:
                View viewLoadMore = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_more, parent, false);
                viewHolder = new BaseViewHolder(viewLoadMore);
                break;
            case VIEW_TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(onBindLayout(), parent, false);
                viewHolder = onCreateHolder(view);
                break;
        }
        return Objects.requireNonNull(viewHolder);
    }

    protected abstract int onBindLayout();

    protected abstract VH onCreateHolder(View view);

    protected abstract void onBindData(VH holder, E item, int position);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            onBindData((VH) holder, mList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<E> list) {
        isLoadMoreOrRefresh = false;
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void refresh(List<E> list) {
        isLoadMoreOrRefresh = false;
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        isLoadMoreOrRefresh = false;
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void remove(E e) {
        isLoadMoreOrRefresh = false;
        mList.remove(e);
        notifyDataSetChanged();
    }

    public void add(E e) {
        isLoadMoreOrRefresh = false;
        mList.add(e);
        notifyDataSetChanged();
    }

    public void addLast(E e) {
        isLoadMoreOrRefresh = false;
        add(e);
    }

    public void addFirst(E e) {
        isLoadMoreOrRefresh = false;
        mList.add(0, e);
        notifyDataSetChanged();
    }

    public void clear() {
        isLoadMoreOrRefresh = false;
        mList.clear();
        notifyDataSetChanged();
    }

    public void addLoadingFooter(E e) {
        isLoadMoreOrRefresh = true;
        mList.add(e);
        notifyItemInserted(mList.size() - 1);
    }

    public E getItem(int position) {
        return mList.get(position);
    }

    public void removeLoadingFooter() {
        isLoadMoreOrRefresh = false;
        int position = mList.size() - 1;
        if (position == -1) return;
        E o = getItem(position);
        if (o != null) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<E> getListData() {
        return mList;
    }

    public void setOnItemClickListener(OnItemClickListener<E> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener<E> onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<E> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<E> {
        void onItemClick(E e, View view, int position);
    }

    public interface OnButtonClickListener<E> {
        void onButtonClick(E e, View view, int position);
    }

    public interface OnItemLongClickListener<E> {
        void onItemLongClick(E e, View view, int position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mList.size() - 1 &&
                isLoadMoreOrRefresh) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }

    public <t extends View> t bind(View view, int id) {
        return view.findViewById(id);
    }

}
