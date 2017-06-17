package com.chuck.ltactionrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chuck.ltactionrecycleradapterlib.LTActionItemViewHolder;
import com.chuck.ltactionrecycleradapterlib.LTActionRecyclerAdapter;

import java.util.List;

/**
 * Created by Steven Reyes (sreyes@stratpoint.com) on 15/06/2017
 */

public class ImagesRecyclerAdapter extends LTActionRecyclerAdapter<String, ImageLTActionViewHolder> {

    private Context context;
    private List<String> pathsList;

    public ImagesRecyclerAdapter(Context context, List<String> t, ActionPosition actionPosition) {
        super(t, actionPosition);
        this.context = context;
        this.pathsList = t;
    }

    public ImagesRecyclerAdapter(Context context, List<String> t, int maxItems, ActionPosition actionPosition) {
        super(t, actionPosition, maxItems);
        this.context = context;
        this.pathsList = t;
    }

    @Override
    public void onBindAdaptingRecyclerViewHolder(LTActionItemViewHolder LTActionItemViewHolder, int position) {
        ImageView storeImageIv = ((ImageViewHolder) LTActionItemViewHolder).ivImage;
        if (pathsList.get(position) != null && !pathsList.get(position).isEmpty())
            Glide.with(context)
                    .load(pathsList.get(position))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(storeImageIv);
    }

    @Override
    public ImageLTActionViewHolder renderTrailingAddViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_action, parent, false);
        ImageLTActionViewHolder storeImageTrailingActionViewHolder =
                new ImageLTActionViewHolder(itemView);
        return storeImageTrailingActionViewHolder;
    }

    @Override
    public RecyclerView.ViewHolder renderViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_image, parent, false);
        ImageViewHolder storeImageViewHolder = new ImageViewHolder(itemView);
        return storeImageViewHolder;
    }
}
