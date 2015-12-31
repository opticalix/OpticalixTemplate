package com.opticalix.opticalixtemplate.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.utils.LogUtils;

/**
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class DemoPagerAdapter extends PagerAdapter {
    private Context mContext;

    public DemoPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        LogUtils.d(this, "isViewFromObject");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.d(this, "instantiateItem, "+position);
//        View inflate = LayoutInflater.from(mContext).inflate(R.layout.view_loading, container, true);
        TextView textView = new TextView(mContext);
//        TextView tv = (TextView) inflate.findViewById(R.id.tv_loading_msg);
//        tv.setVisibility(View.VISIBLE);
        textView.setTag("tv");
        textView.setText("tv" + position);
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtils.d(this, "destroyItem, "+position);
        container.removeView((View) object);
    }

}
