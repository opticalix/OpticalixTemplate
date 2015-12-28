package com.opticalix.opticalixtemplate.loader.base;

/**
 * Created by opticalix@gmail.com on 15/10/16.
 * 与UI层交互的回调接口
 */
public interface IBaseLoadHandler {
    /**
     * 需要在loader层自行抛出至UI线程执行
     * @param object
     */
    void onDataBack(Object object);

    /**
     * 需要在loader层自行抛出至UI线程执行
     * @param dataFailType
     */
    void onDataFail(DataFailType dataFailType);
}
