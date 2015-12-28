package com.opticalix.opticalixtemplate.component.base;

/**
 * Created by opticalix@gmail.com on 15/11/10.
 */
public interface ISimpleLoading {
    void showLoading();

    void dismissLoading();

    int getLoadViewId();

    String DEFAULT_LOADING_VIEW_ID = "view_loading";
}
