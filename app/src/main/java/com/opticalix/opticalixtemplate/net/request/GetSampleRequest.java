package com.opticalix.opticalixtemplate.net.request;

import com.opticalix.opticalixtemplate.net.HttpConfig;
import com.opticalix.opticalixtemplate.net.request.base.BaseRequest;
import com.squareup.okhttp.Headers;

/**
 * Created by opticalix@gmail.com on 15/12/28.
 */
public class GetSampleRequest extends BaseRequest {
    @Override
    public String getUrl() {
        return "http://httpbin.org/get?key=value";
//        return "http://httpbin.org/cache/60";
    }

    @Override
    public String getMethod() {
        return HttpConfig.METHOD_GET;
    }

}
