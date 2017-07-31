package com.opticalix.opticalixtemplate.component.frg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseFragment;
import com.opticalix.opticalixtemplate.utils.DemoUtils;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.view.crop.CalcOffsetView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Felix on 2017/07/31.
 */
public class CropImgFragment extends BaseFragment {
    public static final String LARGE_HORIZONTAL_PIC_URL = "http://img.zcool.cn/community/033eb6b554c768b00000158fceebd4c.jpg";
    public static final String LARGE_VERTICAL_PIC_URL = "http://youimg1.c-ctrip.com/target/tg/623/693/247/78831cc2f56d4e0abb2c5be3b6f53efa.jpg";
    @Bind(R.id.calc_view)
    CalcOffsetView mCalcView;


    public static CropImgFragment newInstance() {
        LogUtils.i("newInstance method invoke!");
        return new CropImgFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_crop_img, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCalcView.setImageUrl(DemoUtils.randomBoolean() ? LARGE_HORIZONTAL_PIC_URL : LARGE_VERTICAL_PIC_URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
