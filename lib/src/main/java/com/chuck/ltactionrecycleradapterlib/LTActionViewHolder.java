package com.chuck.ltactionrecycleradapterlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by steven on 6/14/17.
 */

public abstract class LTActionViewHolder extends RecyclerView.ViewHolder {

    private View.OnClickListener ltActionOnClickListener;

    public LTActionViewHolder(View itemView) {
        super(itemView);
        if (ltActionOnClickListener != null)
            itemView.setOnClickListener(ltActionOnClickListener);
    }

    public View.OnClickListener getLtActionOnClickListener() {
        return ltActionOnClickListener;
    }

    public void setLtActionOnClickListener(View.OnClickListener ltActionOnClickListener) {
        this.ltActionOnClickListener = ltActionOnClickListener;
    }
}
