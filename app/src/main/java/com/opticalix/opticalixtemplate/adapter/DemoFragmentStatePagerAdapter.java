package com.opticalix.opticalixtemplate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.opticalix.opticalixtemplate.component.frg.SimpleTvFragment;
import com.opticalix.opticalixtemplate.utils.LogUtils;

/**
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class DemoFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public DemoFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        LogUtils.d(this, "getItem, pos=" + position);
        return SimpleTvFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.d(this, "instantiateItem, pos=" + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtils.d(this, "destroyItem, pos=" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
