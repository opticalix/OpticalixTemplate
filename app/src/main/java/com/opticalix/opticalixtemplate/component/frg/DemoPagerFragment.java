package com.opticalix.opticalixtemplate.component.frg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.adapter.DemoFragmentPagerAdapter;
import com.opticalix.opticalixtemplate.adapter.DemoFragmentStatePagerAdapter;
import com.opticalix.opticalixtemplate.adapter.DemoPagerAdapter;
import com.opticalix.opticalixtemplate.utils.LogUtils;

/**
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class DemoPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private DemoFragmentPagerAdapter mDemoFragmentPagerAdapter;
    private DemoFragmentStatePagerAdapter mDemoFragmentStatePagerAdapter;

    public static DemoPagerFragment newInstance() {
        LogUtils.i("newInstance method invoke!");
        return new DemoPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_pager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        Button  button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDemoFragmentPagerAdapter.notifyDataSetChanged();
                mDemoFragmentStatePagerAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewPager.setAdapter(new DemoPagerAdapter(getContext()));
        mDemoFragmentStatePagerAdapter = new DemoFragmentStatePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mDemoFragmentStatePagerAdapter);
    }

}
