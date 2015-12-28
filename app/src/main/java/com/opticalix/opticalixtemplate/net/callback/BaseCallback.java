package com.opticalix.opticalixtemplate.net.callback;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;

/**
 * Print log and separate onRespSuccessful/onRespFail
 * Created by opticalix@gmail.com on 2015/10/6.
 */
public abstract class BaseCallback implements Callback {
    private final static String TAG = "BaseCallback";

    /**
     * 取消/无网/超时
     *
     * @param request
     * @param e
     */
    @Override
    public void onFailure(Request request, IOException e) {
        Log.e(TAG, this.getClass().getSimpleName() + " onFailure with request:" + request, e);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        Log.d(TAG, "Response response:          " + response);
        Log.d(TAG, "Response cache response:    " + response.cacheResponse());
        Log.d(TAG, "Response network response:  " + response.networkResponse());
        if (response.isSuccessful()) {
            onResponseSuccessful(response);
        } else {
            onResponseFailure(response, response.code());
        }
    }

    /**
     * 有请求返回且状态码正常
     *
     * @param response
     */
    public abstract void onResponseSuccessful(Response response);

    /**
     * 有请求返回但状态码异常, 包含http错误和业务错误。
     *  @param response
     * @param httpCode
     */
    public abstract void onResponseFailure(Response response, int httpCode);

    /**
     * Print response body's string msg.
     *
     * @param response
     * @throws IOException
     */
    public void printBody(Response response) throws IOException {
        Log.d(TAG, "Response Body String: " + response.body().string());
    }

    /**
     * Print response body's charStream msg.
     *
     * @param response
     * @throws IOException
     */
    public void printCharStream(Response response) throws IOException {
        Reader reader = response.body().charStream();
        String s = IOUtils.toString(reader);
        Log.d(TAG, "Response Body CharStream: " + s);
    }

}
