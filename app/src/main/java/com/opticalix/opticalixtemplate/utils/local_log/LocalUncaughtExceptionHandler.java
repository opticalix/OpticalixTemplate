package com.opticalix.opticalixtemplate.utils.local_log;

import com.opticalix.opticalixtemplate.BuildConfig;

/**
 * Created by opticalix@gmail.com on 15/10/12.
 */
public class LocalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LocalLog.e(this, "Uncaught Exception!", ex);

        if (!BuildConfig.DEBUG) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
