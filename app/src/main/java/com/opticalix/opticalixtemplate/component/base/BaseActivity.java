package com.opticalix.opticalixtemplate.component.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.opticalix.opticalixtemplate.net.HttpUtils;
import com.opticalix.opticalixtemplate.net.request.base.IEnvironment;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by opticalix@gmail.com on 15/10/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements ISimpleLoading, IEnvironment {
    protected UIHandler mHandler;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d(this, "onNewIntent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new UIHandler(this);

        LogUtils.d(this, "onCreate taskId=" + getTaskId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(this, "onStart taskId=" + getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(enableFullLoadingWhenAccessingNetwork()){
            HttpUtils.getOkHttpClient().interceptors().add(LOADING_INTERCEPTOR);
        }
        LogUtils.d(this, "onResume taskId=" + getTaskId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(this, "onPause taskId=" + getTaskId());
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(enableFullLoadingWhenAccessingNetwork()){
            HttpUtils.getOkHttpClient().interceptors().remove(LOADING_INTERCEPTOR);
        }
        LogUtils.d(this, "onStop taskId=" + getTaskId());
    }

    @Override
    protected void onDestroy() {
        //stop all requests on this env
        if (cancelHttpTaskWhenDestroy())
            HttpUtils.getOkHttpClient().cancel(getEnvironmentTag());


        //remove handler tasks
        if (mHandler != null) {
            mHandler.removeCallbacks(null);
        }
        super.onDestroy();
        LogUtils.d(this, "onDestroy taskId=" + getTaskId());
    }

    /*
    --------------------
    Handler
    --------------------
     */
    protected static class UIHandler extends Handler {

        private WeakReference<BaseActivity> mRef;

        public UIHandler(BaseActivity activity) {
            mRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mRef.get();
            if (activity != null) {
                activity.onHandleMessage(activity, msg);
            }
        }

    }

    /**
     * Need to be override
     *
     * @param activity current activity
     * @param msg      message
     */
    protected void onHandleMessage(Activity activity, Message msg) {
        if(enableFullLoadingWhenAccessingNetwork()){
            switch (msg.what){
                case HttpUtils.INTERCEPTOR_SHOW_LOADING:
                    showLoading();
                    break;
                case HttpUtils.INTERCEPTOR_DISMISS_LOADING:
                    dismissLoading();
                    break;
            }
        }
    }


    /*
    --------------------
    Loading
    --------------------
     */
    public BaseDialogFragment showDialogFragment(Class<? extends BaseDialogFragment> clazz) {
        BaseDialogFragment fragmentByTag = (BaseDialogFragment) this.getSupportFragmentManager().findFragmentByTag(clazz.getSimpleName());
        if (fragmentByTag != null) {
            return fragmentByTag;
        }
        try {
            BaseDialogFragment dialogFragment = clazz.newInstance();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.add(dialogFragment,
                    clazz.getSimpleName());//add tag
            fragmentTransaction.commitAllowingStateLoss();//ignore ex
            this.getSupportFragmentManager().executePendingTransactions();
            return dialogFragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dismissDialogFragment(Class<? extends BaseDialogFragment> clazz) {
        BaseDialogFragment dialogFragment = (BaseDialogFragment) this.getSupportFragmentManager().findFragmentByTag(clazz.getSimpleName());
        if (dialogFragment != null) {
            dialogFragment.dismissAllowingStateLoss();
            this.getSupportFragmentManager().beginTransaction().remove(dialogFragment).commitAllowingStateLoss();
        }
    }

    protected Class<? extends BaseDialogFragment> getLoadingFragmentClass() {
        return BaseFullLoadingFragment.class;
    }

    @Override
    /**
     * show default loading fragment, or you can custom loading-view by call method {@link #getLoadViewId()}
     */
    public void showLoading() {
        if (getRootView() != null) {
            if (getLoadViewId() != 0) {
                View loading = getRootView().findViewById(getLoadViewId());
                loading.setVisibility(View.VISIBLE);
            } else {
                showDialogFragment(getLoadingFragmentClass());
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (getRootView() != null) {
            if (getLoadViewId() != 0) {
                View loading = getRootView().findViewById(getLoadViewId());
                loading.setVisibility(View.GONE);
            } else {
                dismissDialogFragment(getLoadingFragmentClass());
            }
        }
    }

    @Override
    public int getLoadViewId() {
        return 0;
    }


    protected View getRootView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * @return if this activity has network task and need showing fullScreen loading, return true; else return false
     */
    protected abstract boolean enableFullLoadingWhenAccessingNetwork();

    /*
    --------------------
    OkHttp
    --------------------
     */

    @Override
    public String getEnvironmentTag() {
        return this.getClass().getSimpleName();
    }

    public boolean cancelHttpTaskWhenDestroy() {
        return true;
    }

    public final Interceptor LOADING_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            mHandler.sendEmptyMessage(HttpUtils.INTERCEPTOR_SHOW_LOADING);
            Response proceed = chain.proceed(request);
            mHandler.sendEmptyMessage(HttpUtils.INTERCEPTOR_DISMISS_LOADING);
            return proceed;
        }
    };
}
