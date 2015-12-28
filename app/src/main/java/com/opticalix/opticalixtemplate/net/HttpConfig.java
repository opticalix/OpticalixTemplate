package com.opticalix.opticalixtemplate.net;

import android.os.Environment;

import com.opticalix.opticalixtemplate.BaseApplication;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;

import java.io.File;

/**
 * Created by opticalix@gmail.com on 15/9/28.
 */
public class HttpConfig {
    public static final int TIME_OUT = 10;
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB
    public static final File HTTP_CACHE_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + BaseApplication.getAppShortName() + "/cache");

    /*
     * http://httpbin.org/
     */
    public static final String TEST_BASE_URL = "http://httpbin.org/";
    public static final String TEST_GET_URL = "http://httpbin.org/get";
    public static final String TEST_PUT_URL = "http://httpbin.org/put";
    public static final String TEST_DELETE_URL = "http://httpbin.org/delete";


    public static final Headers COMMON_HEADERS = new Headers.Builder().build();
    public static final MediaType MEDIA_TYPE_DEFAULT
            = MediaType.parse("application/x-www-form-urlencoded");//; charset=utf-8
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

}
