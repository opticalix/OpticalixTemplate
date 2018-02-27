package com.opticalix.opticalixtemplate.component.frg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseFragment;
import com.opticalix.opticalixtemplate.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Felix on 2018/02/26.
 */
public class ZeppDemoFragment extends BaseFragment {

    @Bind(R.id.btn_send_intent)
    Button mBtnSendIntent;

    public static ZeppDemoFragment newInstance() {
        LogUtils.i("newInstance method invoke!");
        return new ZeppDemoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_zepp_demo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_send_intent)
    public void onSendIntentBtnClick() {
        Intent intent = new Intent();
        intent.setAction("com.zepp.zepp_tennis.action.start_tennis");
        //需要zepp里加上dataScheme=zepp
//        intent.setData(Uri.parse("zepp://com.zepp.zepp_tennis?arg1=test1&arg2=test2"));
        startActivity(intent);
    }
}
