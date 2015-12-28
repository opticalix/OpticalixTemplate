package com.opticalix.opticalixtemplate.net.request.base;

import com.squareup.okhttp.Request;

/**
 * 自定Request特征
 * Created by opticalix@gmail.com on 15/9/29.
 */
public interface IRequest {
    String getUrl();
    Request buildRequest(String environmentTag);
    String getMethod();
}
