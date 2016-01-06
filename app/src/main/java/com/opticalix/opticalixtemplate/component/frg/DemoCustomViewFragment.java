package com.opticalix.opticalixtemplate.component.frg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.opticalix.dropdown_lib.DropdownView;
import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseFragment;
import com.opticalix.opticalixtemplate.utils.DemoUtils;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.view.DemoCustomView;

/**
 * Test my custom-view
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class DemoCustomViewFragment extends BaseFragment {

    private DemoCustomView mCustomView;
    private DropdownView mDropdownView;

    public static DemoCustomViewFragment newInstance() {
        LogUtils.i("newInstance method invoke!");
        return new DemoCustomViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frg_custom_view, container, false);
        mCustomView = (DemoCustomView) inflate.findViewById(R.id.custom_view);
        mCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.startScroll();
            }
        });

        mDropdownView = (DropdownView) inflate.findViewById(R.id.dropdown_view);
        Button button = (Button) inflate.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("I'm button got clicked");
            }
        });
        ListView simpleListView1 = DemoUtils.createSimpleListView(getContext(), 10, 0);
        ListView simpleListView2 = DemoUtils.createSimpleListView(getContext(), 5, 1);
        simpleListView1.setBackgroundResource(android.R.color.white);
        simpleListView2.setBackgroundResource(android.R.color.white);
        mDropdownView.attachListView(simpleListView1, simpleListView2);
        mDropdownView.setOnDropdownItemClickListener(new DropdownView.OnDropdownItemClickListener() {
            @Override
            public void onItemClick(View v, int whichList, int position) {
                LogUtils.d(DemoCustomViewFragment.this, "which=" + whichList + ", pos=" + position);
                mDropdownView.setTitleText(whichList, whichList + "-" + position);
                mDropdownView.setTitleTextColor(whichList, 0xff0b86e5);
                mDropdownView.setTitleTextSize(whichList, TypedValue.COMPLEX_UNIT_SP, 8);

                mDropdownView.setArrowDownDrawable(R.mipmap.ic_launcher);
            }
        });
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
