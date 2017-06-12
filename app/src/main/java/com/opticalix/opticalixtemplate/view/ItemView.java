package com.opticalix.opticalixtemplate.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

class ItemView extends CommonItemVIew {

    public ItemView(Context context) {
        super(context);
//        int px = ScreenUtil.dip2px(getContext(), 14);
//        setPadding(px, 0, px, 0);
    }

    @Override
    protected View getLeftView() {
        TextView textView = new TextView(getContext());
//        textView.setTypeface(FontUtil.getInstance().getFontTypeface(getContext(), FontUtil.FONT_AVENIR_MEDIUM));
        return textView;
    }

    @Override
    protected View getRightView() {
        return null;
    }
}