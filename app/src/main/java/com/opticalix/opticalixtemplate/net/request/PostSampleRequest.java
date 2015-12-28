package com.opticalix.opticalixtemplate.net.request;

import com.opticalix.opticalixtemplate.net.HttpConfig;
import com.opticalix.opticalixtemplate.net.request.base.BaseRequest;
import com.squareup.okhttp.RequestBody;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by opticalix@gmail.com on 15/12/28.
 */
public class PostSampleRequest extends BaseRequest {
    @Override
    public String getUrl() {
        return "http://httpbin.org/post";
    }

    @Override
    public String getMethod() {
        return HttpConfig.METHOD_POST;
    }

    @Override
    public List<BasicNameValuePair> getParams() {
        List<BasicNameValuePair> params = super.getParams();
        params.add(new BasicNameValuePair("key", "value"));
        return params;
    }
}
