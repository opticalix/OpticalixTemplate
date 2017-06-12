package com.opticalix.opticalixtemplate.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.opticalix.opticalixtemplate.R;


/**
 * padding
 * bottom_divider color
 * Created by Felix on 2017/06/07.
 */
public abstract class CommonItemVIew extends FrameLayout {

    RelativeLayout mRlLeftPlaceholder;
    RelativeLayout mRlRightPlaceholder;
    View mBottomLine;

    public CommonItemVIew(Context context) {
        this(context, null);
    }

    public CommonItemVIew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItemVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_common_item, this, true);
        mRlLeftPlaceholder = (RelativeLayout) findViewById(R.id.rl_left_placeholder);
        mRlRightPlaceholder = (RelativeLayout) findViewById(R.id.rl_right_placeholder);
        mBottomLine = findViewById(R.id.bottom_line);

        mRlLeftPlaceholder.addView(getLeftView());
        mRlRightPlaceholder.addView(getRightView());
        mBottomLine.setBackgroundColor(getBottomLineColor());
    }

    public void setBottomLineColor(@ColorInt int color) {
        mBottomLine.setBackgroundColor(color);
    }

    public void setBottomLineVisibility(int visibility) {
        mBottomLine.setVisibility(visibility);
    }

    protected abstract View getLeftView();
    protected abstract View getRightView();
    protected int getBottomLineColor() {
        return getResources().getColor(R.color.common_divider);//default
    }
}
