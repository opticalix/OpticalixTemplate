package com.opticalix.opticalixtemplate;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;


import com.opticalix.opticalixtemplate.utils.LogUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by opticalix@gmail.com on 15/10/12.
 */
public class BaseApplication extends Application {

    private ArrayList<FragmentActivity> list = new ArrayList<>();

    public static String getAppShortName(){
        return BuildConfig.APPLICATION_ID.substring(BuildConfig.APPLICATION_ID.lastIndexOf(".") + 1, BuildConfig.APPLICATION_ID.length());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //enable log.
//        LocalLog.init(null, null, true);
        LogUtils.isDebug = BuildConfig.DEBUG;

        //set default uncaught-ex-handler. Easier to look up last crash exception.
//        Thread.currentThread().setUncaughtExceptionHandler(new LocalUncaughtExceptionHandler());

        //prepare versionCode

        //setup leak tool
//        refWatcher = LeakCanary.install(this);

        //setup stetho debug tool
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());

    }

//    public static RefWatcher getRefWatcher(Context context) {
//        BaseApplication application = (BaseApplication)   context.getApplicationContext();
//        return application.refWatcher;
//    }

    /**
     * 全局env
     *
     * @return
     */

    public void addActivity(FragmentActivity activity) {
        list.add(activity);
    }

    public void removeActivity(FragmentActivity activity){
        list.remove(activity);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void finishActivityNotKill() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

    /*
    * 关闭Activity列表中的所有Activity
    */
    public void finishActivity() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    private ExecutorService mExecutorService;

    public ExecutorService getExecutorService() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
        return mExecutorService;
    }
}
