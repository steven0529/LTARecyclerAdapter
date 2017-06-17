package com.chuck.ltactionrecycleradapter;

import android.view.View;
import android.widget.ImageView;

import com.chuck.ltactionrecycleradapterlib.LTAItemViewHolder;

/**
 * Created by Steven Reyes (sreyes@stratpoint.com) on 15/06/2017
 */

public class ImageViewHolder extends LTAItemViewHolder {

    ImageView ivImage;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
    }
}
