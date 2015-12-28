package com.opticalix.opticalixtemplate;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;


import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.utils.local_log.LocalLog;
import com.opticalix.opticalixtemplate.utils.local_log.LocalUncaughtExceptionHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by opticalix@gmail.com on 15/10/12.
 */
public class BaseApplication extends Application {

    private RefWatcher mRefWatcher;

    public static String getAppShortName(){
        return BuildConfig.APPLICATION_ID.substring(BuildConfig.APPLICATION_ID.lastIndexOf(".") + 1, BuildConfig.APPLICATION_ID.length());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //enable log.
        LocalLog.init(null, null, BuildConfig.DEBUG);
        LogUtils.isDebug = BuildConfig.DEBUG;

        //set default uncaught-ex-handler. Easier to look up last crash exception.
        Thread.currentThread().setUncaughtExceptionHandler(new LocalUncaughtExceptionHandler());

        //setup leak tool
        mRefWatcher = LeakCanary.install(this);

        //setup stetho debug tool
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());

    }

    /*
    --------------------
    ExecutorService
    --------------------
     */
    private ExecutorService mExecutorService;

    public ExecutorService getExecutorService() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
        return mExecutorService;
    }

}
