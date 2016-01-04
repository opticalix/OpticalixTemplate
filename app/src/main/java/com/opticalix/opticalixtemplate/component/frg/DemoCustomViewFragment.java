package com.opticalix.opticalixtemplate.component.frg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseFragment;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.view.DemoCustomView;
import com.opticalix.opticalixtemplate.view.DropdownView;

import java.util.ArrayList;
import java.util.List;

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
        mDropdownView.attachListView(createSimpleListView(), createSimpleListView());
        return inflate;
    }

    @NonNull
    private ListView createSimpleListView() {
        ListView listView = new ListView(getContext());
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        listView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.view_loading, R.id.tv_loading_msg, data));
        return listView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
