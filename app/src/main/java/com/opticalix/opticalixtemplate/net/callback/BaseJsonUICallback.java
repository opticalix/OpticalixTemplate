package com.opticalix.opticalixtemplate.net.callback;

import android.os.Handler;

import com.opticalix.opticalixtemplate.net.HttpException;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by opticalix@gmail.com on 15/12/24.
 */
public abstract class BaseJsonUICallback extends BaseJsonCallback {
    private Handler mHandler;
    public static final int ON_GSON = 0;
    public static final int ON_FAIL = 1;

    public BaseJsonUICallback(Handler handler) {
        mHandler = handler;
    }

    @Override
    protected void onGson(Object result) {
        mHandler.post(new CallbackRunnable(ON_GSON, result));
    }

    @Override
    public void onFailure(Request request, IOException e) {
        super.onFailure(request, e);
        //no net should not be check here.
        mHandler.post(new CallbackRunnable(ON_FAIL, e));
    }

    @Override
    public void onResponseFailure(Response response, int httpCode) {
        mHandler.post(new CallbackRunnable(ON_FAIL, new HttpException("onResponseFailure", httpCode)));
    }

    protected abstract void onGsonUI(Object content);

    protected abstract void onFailUI(Exception e);

    class CallbackRunnable implements Runnable {
        int type;
        Object content;

        public CallbackRunnable(int type, Object content) {
            this.type = type;
            this.content = content;
        }

        @Override
        public void run() {
            switch (type) {
                case ON_GSON:
                    onGsonUI(content);
                    break;
                case ON_FAIL:
                    if (content instanceof Exception)
                        onFailUI((Exception) content);
                    else
                        onFailUI(new HttpException("unknown", HttpException.ERROR_UNKNOWN));
                    break;
            }
        }
    }
}
