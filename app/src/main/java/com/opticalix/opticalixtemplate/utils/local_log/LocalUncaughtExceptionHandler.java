package com.opticalix.opticalixtemplate.utils.local_log;

/**
 * Created by opticalix@gmail.com on 15/10/12.
 */
public class LocalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LocalLog.e(this, "Uncaught Exception!", ex);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
