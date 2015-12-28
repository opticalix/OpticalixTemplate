package com.opticalix.opticalixtemplate.net;

import android.util.Log;

import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by opticalix@gmail.com on 15/9/28.
 */
public class HttpUtils {
    /*
        Questions
        1. cancel request
            对于退出Act/Frag的情形，发起环境继承ITag，以便给request打标签(RequestBuilder.tag(tag))。退出时全部cancel(OkHttpClient.cancel(tag))。
            主动cancel  拿到Call对象的ref就可以取消。(以Call为单位取消请求)。
        2. 多个请求（async 依赖Dispatcher内部线程池做到）
            无关联 多个call或一个call
            有依赖关系 连环回调
        3. Json

        Advantages
        1. timeout  onFailure
        2. retry  auto enabled. see OkHttpClient.setRetryOnConnectionFailure
        3. redirect   auto
        4. cache   if client/server support
     */
    private static OkHttpClient sOkHttpClient = new OkHttpClient();
    private static final String CHARSET_NAME = "UTF-8";
    public static final int INTERCEPTOR_SHOW_LOADING = 100;
    public static final int INTERCEPTOR_DISMISS_LOADING = 200;

    static {
        getOkHttpClient().setConnectTimeout(HttpConfig.TIME_OUT, TimeUnit.SECONDS);
        getOkHttpClient().setWriteTimeout(HttpConfig.TIME_OUT, TimeUnit.SECONDS);
        getOkHttpClient().setReadTimeout(HttpConfig.TIME_OUT, TimeUnit.SECONDS);
//        getOkHttpClient().networkInterceptors().add(new StethoInterceptor());

        //prepare cache dir
        if (!HttpConfig.HTTP_CACHE_DIR.exists()) {
            try {
                if (HttpConfig.HTTP_CACHE_DIR.mkdirs()) {
                    getOkHttpClient().setCache(new Cache(HttpConfig.HTTP_CACHE_DIR, HttpConfig.HTTP_CACHE_SIZE));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient();
        }
        return sOkHttpClient;
    }


    /**
     * can not get called on main thread
     * response may be null
     *
     * @param request
     * @return Response
     */
    public static Response syncCall(Request request) throws IOException {
        return getOkHttpClient().newCall(request).execute();
    }

    /**
     * Notice that callback runs not on ui thread
     *
     * @param request
     * @param callback
     * @return Call
     */
    public static Call asyncCall(Request request, Callback callback) {
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * 将NameValuePair格式化。
     * 为HttpPost 提供 params
     *
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return url + "?" + formatParams(params);
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    /**
     * 创建一个Get请求，Headers自定义
     *
     * @param url
     * @param tag
     * @return
     */
    public static Request makeGetRequest(String url, Headers headers, Object tag) {
        return new Request.Builder().get().headers(headers).url(url).tag(tag).build();
    }

    /**
     * 创建一个Post请求，Headers自定义
     *
     * @param url
     * @param requestBody
     * @param tag
     * @return
     */
    public static Request makePostRequest(String url, Headers headers, RequestBody requestBody, Object tag) {
        return new Request.Builder().post(requestBody).headers(headers).url(url).tag(tag).build();
    }

    public static Request makePutRequest(String url, Headers headers, RequestBody requestBody, Object tag) {
        return new Request.Builder().put(requestBody).headers(headers).url(url).tag(tag).build();
    }

    public static Request makeDeleteRequest(String url, Headers headers, Object tag) {
        return new Request.Builder().delete().headers(headers).url(url).tag(tag).build();
    }


}
