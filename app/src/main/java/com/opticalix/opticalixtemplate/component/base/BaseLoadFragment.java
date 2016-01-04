package com.opticalix.opticalixtemplate.component.base;

import android.os.Bundle;

import com.opticalix.opticalixtemplate.utils.TargetUtils;

/**
 * Optimise create process.
 * Suit for Fragment which need init with data.
 * Created by opticalix@gmail.com on 16/1/4.
 */
public abstract class BaseLoadFragment extends BaseFragment{

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        onPrepareData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TargetUtils.removeTarget(this.getClass().getSimpleName());
    }

    /**
     * asynchronous load data from server
     */
    protected abstract void onPrepareData();

    /**
     * when all 'target' is reached
     */
    protected abstract void onDataReady();

    /**
     * how many loading tasks
     * @return count of loading tasks
     */
    protected abstract int buildInitTaskCount();

    /**
     * Need call this after one load data back
     * @param taskBit must be smaller than {@link #buildInitTaskCount()}
     */
    protected void finishOneTask(int taskBit){
        if(TargetUtils.contributeTarget(this.getClass().getSimpleName(), taskBit, buildInitTaskCount())){
            getBaseActivity().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    //need run in main thread
                    onDataReady();
                    dismissLoading();
                }
            });
        }
    }
}
