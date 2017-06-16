package com.chuck.ltactionrecycleradapterlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by steven on 6/14/17.
 */

public abstract class LTActionRecyclerAdapter<T, S extends LTActionViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ACTION_ITEM = -1;
    private static final int NORMAL_ITEM = -2;

    private List<T> t;
    private ActionPosition actionPosition;
    private int maxItems = -1;
    private View.OnClickListener itemOnClickListener;
    private View.OnClickListener actionOnClickListener;

    public enum ActionPosition {
        LEADING,
        TRAILING
    }

    public LTActionRecyclerAdapter(List<T> t, ActionPosition actionPosition) {
        this.t = t;
        this.actionPosition = actionPosition;
    }

    public LTActionRecyclerAdapter(List<T> t, ActionPosition actionPosition, int maxItems) {
        this.t = t;
        this.actionPosition = actionPosition;
        this.maxItems = maxItems;
        if (maxItems != -1 && t.size() > maxItems)
            this.t = t.subList(0, maxItems);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM)
            return renderViewHolder(parent);
        else
            return renderTrailingAddViewHolder(parent);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < t.size() && holder instanceof LTActionItemViewHolder)
            onBindAdaptingRecyclerViewHolder((LTActionItemViewHolder) holder, position);

        if (position < t.size() && holder instanceof LTActionItemViewHolder) {
            if (itemOnClickListener != null)
                holder.itemView.setOnClickListener(itemOnClickListener);
        } else {
            if (actionOnClickListener != null)
                holder.itemView.setOnClickListener(actionOnClickListener);
        }
    }

    public abstract void onBindAdaptingRecyclerViewHolder(LTActionItemViewHolder LTActionItemViewHolder, int position);

    @Override
    public int getItemViewType(int position) {
        if (actionPosition == ActionPosition.LEADING) {
            if (position == 0)
                return ACTION_ITEM;
            else
                return NORMAL_ITEM;
        } else {
            if (position < t.size())
                return NORMAL_ITEM;
            else
                return ACTION_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (maxItems != -1 && maxItems <= t.size())
            return t.size();
        else
            return t.size() + 1;
    }

    public abstract S
        renderTrailingAddViewHolder(ViewGroup parent);

    public abstract RecyclerView.ViewHolder renderViewHolder(ViewGroup parent);

    public View.OnClickListener getItemOnClickListener() {
        return itemOnClickListener;
    }

    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public View.OnClickListener getActionOnClickListener() {
        return actionOnClickListener;
    }

    public void setActionOnClickListener(View.OnClickListener actionOnClickListener) {
        this.actionOnClickListener = actionOnClickListener;
    }
}
