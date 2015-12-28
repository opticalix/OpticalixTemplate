package com.opticalix.opticalixtemplate.net.request.base;

import com.opticalix.opticalixtemplate.net.HttpException;
import com.opticalix.opticalixtemplate.net.HttpConfig;
import com.opticalix.opticalixtemplate.net.HttpUtils;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by opticalix@gmail.com on 15/9/29.
 */
public abstract class BaseRequest implements IRequest {

    public Headers getHeaders() {
        return HttpConfig.COMMON_HEADERS;
    }

    public List<BasicNameValuePair> getParams() {
        List<BasicNameValuePair> pairList = new ArrayList<>();
        //add common params
//        pairList.add(new BasicNameValuePair("_appid", "200001"));
//        pairList.add(new BasicNameValuePair("_os", "1"));
//        pairList.add(new BasicNameValuePair("_v", BuildConfig.VERSION_NAME));
//        pairList.add(new BasicNameValuePair("_t", String.valueOf(System.currentTimeMillis())));
        return pairList;
    }

    @Override
    public Request buildRequest(String environmentTag) {
        Request request;
        String content;
        switch (getMethod()) {
            case HttpConfig.METHOD_GET:
                request = HttpUtils.makeGetRequest(HttpUtils.attachHttpGetParams(getUrl(), getParams()), getHeaders(), environmentTag);
                break;
            case HttpConfig.METHOD_POST:
                RequestBody postRequestBody = null;
                if (getRequestBody() != null) {
                    postRequestBody = getRequestBody();
                } else if (getParams() != null) {
                    content = HttpUtils.formatParams(getParams());
                    postRequestBody = RequestBody.create(HttpConfig.MEDIA_TYPE_DEFAULT, content);
                }
                request = HttpUtils.makePostRequest(getUrl(), getHeaders(), postRequestBody, environmentTag);
                break;
            case HttpConfig.METHOD_PUT:
                RequestBody putRequestBody = null;
                if (getRequestBody() != null) {
                    putRequestBody = getRequestBody();
                } else if (getParams() != null) {
                    content = HttpUtils.formatParams(getParams());
                    putRequestBody = RequestBody.create(HttpConfig.MEDIA_TYPE_DEFAULT, content);
                }
                request = HttpUtils.makePutRequest(getUrl(), getHeaders(), putRequestBody, environmentTag);
                break;
            case HttpConfig.METHOD_DELETE:
                //FIXME params
                request = HttpUtils.makeDeleteRequest(getUrl(), getHeaders(), environmentTag);
                break;
            default:
                throw new HttpException("no such http method: " + getMethod());
        }
        if (request == null) {
            throw new HttpException("request is null");
        } else {
            return request;
        }
    }

    /**
     * 与getParams不共存
     * 有需求才用
     *
     * @return
     */
    protected RequestBody getRequestBody() {
        return null;
    }


}
