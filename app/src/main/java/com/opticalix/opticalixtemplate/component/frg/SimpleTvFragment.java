package com.opticalix.opticalixtemplate.component.frg;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.utils.LogUtils;

/**
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class SimpleTvFragment extends Fragment {

    public static SimpleTvFragment newInstance(int data){
        SimpleTvFragment simpleTvFragment = new SimpleTvFragment();
        Bundle args = new Bundle();
        args.putInt("data", data);
        simpleTvFragment.setArguments(args);
        return simpleTvFragment;
    }
    private int mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mData = arguments.getInt("data");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText(this.getClass().getSimpleName() + ", data=" + mData);
        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.d(this, "onAttach, data="+mData);//onAttach is ahead of onCreate
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.d(this, "onDetach, data=" + mData);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.d(this, "onSaveInstanceState, data=" + mData);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtils.d(this, "onViewStateRestored, data=" + mData);
    }
}
