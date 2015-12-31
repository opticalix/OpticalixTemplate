package com.opticalix.opticalixtemplate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.opticalix.opticalixtemplate.component.frg.SimpleTvFragment;
import com.opticalix.opticalixtemplate.utils.LogUtils;

/**
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class DemoFragmentPagerAdapter extends FragmentPagerAdapter {
    public DemoFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        LogUtils.d(this, "getItem, pos="+position);
        return SimpleTvFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.d(this, "instantiateItem, pos="+position);
        //super方法已经实现了一套优化逻辑。 根据containerId+itemPos新建出一个唯一Tag，利用fragmentManager寻找，
        // 如果这个tag对应的fragment不存在则通过getItem新建，存在则attach并返回。
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtils.d(this, "destroyItem, pos="+position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        LogUtils.d(this, "getItemPosition");
        return POSITION_NONE;
    }
}
