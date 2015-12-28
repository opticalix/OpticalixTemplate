package com.opticalix.opticalixtemplate.component.base;

import android.app.Dialog;

import com.opticalix.opticalixtemplate.R;


/**
 * Created by opticalix@gmail.com on 15/10/21.
 */
public class BaseFullLoadingFragment extends BaseDialogFragment {

    @Override
    protected int innerGetLayoutRes() {
        return R.layout.view_loading;
    }

    @Override
    protected int innerGetTheme() {
        return android.R.style.Theme_Translucent_NoTitleBar;
    }

    @Override
    protected void setDialogProperty(Dialog dialog) {
        super.setDialogProperty(dialog);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    protected String innerGetMessage() {
        return getActivity().getString(R.string.loading_now);
    }

}
