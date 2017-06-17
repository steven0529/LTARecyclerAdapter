package com.chuck.ltactionrecycleradapterlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by steven on 6/14/17.
 */

public abstract class LTARecyclerAdapter<T, S extends LTActionViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ACTION_ITEM = -1;
    private static final int NORMAL_ITEM = -2;

    private List<T> data;
    private ActionPosition actionPosition;
    private int maxItems = -1;
    private LTAItemOnClickListener itemOnClickListener;
    private LTActionOnClickListener actionOnClickListener;

    public enum ActionPosition {
        LEADING,
        TRAILING
    }

    public LTARecyclerAdapter(List<T> data, ActionPosition actionPosition) {
        this.data = data;
        this.actionPosition = actionPosition;
    }

    public LTARecyclerAdapter(List<T> data, ActionPosition actionPosition, int maxItems) {
        this.data = data;
        this.actionPosition = actionPosition;
        this.maxItems = maxItems;
        if (maxItems != -1 && data.size() > maxItems)
            this.data = data.subList(0, maxItems);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM)
            return renderItemViewHolder(parent);
        else
            return renderActionViewHolder(parent);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int finalPos = position;
        if (position < data.size() && holder instanceof LTAItemViewHolder)
            onBindAdaptingRecyclerViewHolder((LTAItemViewHolder) holder, position);

        if (position < data.size() && holder instanceof LTAItemViewHolder) {
            if (itemOnClickListener != null)
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOnClickListener.onClick(v, finalPos);
                    }
                });
        } else {
            if (actionOnClickListener != null)
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionOnClickListener.onClick(v, finalPos);
                    }
                });
        }
    }

    public abstract void onBindAdaptingRecyclerViewHolder(LTAItemViewHolder LTAItemViewHolder, int position);

    @Override
    public int getItemViewType(int position) {
        if (actionPosition == ActionPosition.LEADING) {
            if (position == 0)
                return ACTION_ITEM;
            else
                return NORMAL_ITEM;
        } else {
            if (position < data.size())
                return NORMAL_ITEM;
            else
                return ACTION_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (maxItems != -1 && maxItems <= data.size())
            return data.size();
        else
            return data.size() + 1;
    }

    public abstract S
        renderActionViewHolder(ViewGroup parent);

    public abstract RecyclerView.ViewHolder renderItemViewHolder(ViewGroup parent);

    public LTAItemOnClickListener getItemOnClickListener() {
        return itemOnClickListener;
    }

    public void setItemOnClickListener(LTAItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public LTActionOnClickListener getActionOnClickListener() {
        return actionOnClickListener;
    }

    public void setActionOnClickListener(LTActionOnClickListener actionOnClickListener) {
        this.actionOnClickListener = actionOnClickListener;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
