package com.tiha.admin.anphat.utils;

import android.content.Context;

public class SquaredImageView extends de.hdodenhof.circleimageview.CircleImageView{
    public SquaredImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }
}
