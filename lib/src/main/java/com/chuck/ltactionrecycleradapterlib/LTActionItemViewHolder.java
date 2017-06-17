package com.chuck.ltactionrecycleradapterlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by steven on 6/14/17.
 */

public abstract  class LTActionItemViewHolder extends RecyclerView.ViewHolder {

    private View.OnClickListener ltActionItemOnClickListener;

    public LTActionItemViewHolder(View itemView) {
        super(itemView);
    }

    public View.OnClickListener getLtActionItemOnClickListener() {
        return ltActionItemOnClickListener;
    }

    public void setLtActionItemOnClickListener(View.OnClickListener ltActionItemOnClickListener) {
        this.ltActionItemOnClickListener = ltActionItemOnClickListener;
    }


}
