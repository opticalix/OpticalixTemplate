package com.opticalix.opticalixtemplate.net.interceptor;

import com.opticalix.opticalixtemplate.net.HttpConfig;
import com.opticalix.opticalixtemplate.net.HttpUtils;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by opticalix@gmail.com on 15/12/28.
 */
public class Interceptors {
    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "max-age=60")
                    .build();
        }
    };

    public static final Interceptor LOG_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();

            LogUtils.i(String.format("Sending request %s on %s%n%s",
                    request.url(), request.headers(), chain.connection()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtils.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    };


//    public static final Interceptor LOGIN_INTERCEPTOR = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            if (expire) {
//                Request original = chain.request();
//
//                Request request = new Request.Builder()
//                        .url(HttpConfig.TEST_GET_URL + "?login=true")
//                        .build();
//                OkHttpClient clone = HttpUtils.getOkHttpClient().clone();
//                clone.interceptors().clear();
//                Response response = clone.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    LogUtils.i("login success");
//                    expire = false;
//                    Request further = new Request.Builder()
//                            .url(original.url())
//                            .headers(original.headers())
//                            .build();
//                    return chain.proceed(further);
//                }
//            }
//            return chain.proceed(chain.request());
//        }
//    };
}
