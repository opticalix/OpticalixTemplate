package com.opticalix.opticalixtemplate.loader;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opticalix.opticalixtemplate.net.callback.BaseCallback;
import com.opticalix.opticalixtemplate.net.request.PostSampleRequest;
import com.opticalix.opticalixtemplate.net.test.HttpBinTypeAdapter;
import com.opticalix.opticalixtemplate.loader.base.DataFailType;
import com.opticalix.opticalixtemplate.loader.base.IBaseLoadHandler;
import com.opticalix.opticalixtemplate.net.HttpException;
import com.opticalix.opticalixtemplate.net.HttpUtils;
import com.opticalix.opticalixtemplate.net.callback.BaseJsonUICallback;
import com.opticalix.opticalixtemplate.net.request.GetSampleRequest;
import com.opticalix.opticalixtemplate.net.request.base.IEnvironment;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by opticalix@gmail.com on 15/12/24.
 */
public class SampleDataLoader {
    public static final String TAG = "SampleDataLoader";

    public void loadSampleGet(final String url, final IEnvironment environment, Handler handler, final IBaseLoadHandler loadHandler) {
        BaseJsonUICallback uiCallback = new BaseJsonUICallback(handler) {
            @Override
            protected void onGsonUI(Object content) {
                loadHandler.onDataBack(content);
            }

            @Override
            protected void onFailUI(Exception e) {
                loadHandler.onDataFail(DataFailType.DATA_FAIL);
                //fixme how to handle different error

                if (e instanceof HttpException) {
                    if (HttpException.ERROR_AFTER_RESPONSE == ((HttpException) e).getCode()) {
                        //response is successful, but parse fail
                        LogUtils.w(TAG, "onFailUI error after response");
                    } else {
                        //fail at specific http status code
                        LogUtils.w(TAG, "onFailUI httpCode=" + ((HttpException) e).getCode());
                    }
                } else {
                    //fail at start
                    LogUtils.w(TAG, "onFailure.");
                }
            }

            @Override
            protected Type offerJsonType() {
                return new TypeToken<HttpBinTypeAdapter>() {
                }.getType();
            }

            @Override
            protected Gson offerGson() {
                return new GsonBuilder().registerTypeAdapter(offerJsonType(), new HttpBinTypeAdapter()).create();
            }

            @Override
            protected String preHandle(Response response) throws IOException {
                return super.preHandle(response);
            }
        };

        HttpUtils.asyncCall(new GetSampleRequest(){
            @Override
            public String getUrl() {
                return url;
            }
        }.buildRequest(environment.getEnvironmentTag()), uiCallback);
    }

    public void loadSamplePost(final String url, final IEnvironment environment, Handler handler, final IBaseLoadHandler loadHandler) {
        BaseCallback callback = new BaseCallback() {
            @Override
            public void onResponseSuccessful(Response response) {
                try {
                    loadHandler.onDataBack(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                    loadHandler.onDataFail(DataFailType.DATA_FAIL);
                }
            }

            @Override
            public void onResponseFailure(Response response, int httpCode) {
                loadHandler.onDataFail(DataFailType.DATA_FAIL);
            }
        };

        HttpUtils.asyncCall(new PostSampleRequest(){
            @Override
            public String getUrl() {
                return url;
            }
        }.buildRequest(environment.getEnvironmentTag()), callback);
    }
}
