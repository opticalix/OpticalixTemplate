package com.opticalix.opticalixtemplate.net.request.base;

import com.opticalix.opticalixtemplate.net.HttpUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by opticalix@gmail.com on 15/10/10.
 */
public abstract class UploadEduRequest extends BaseRequest {

    /**
     * http://tool.oschina.net/commons check MIME-TYPES
     * 与普通request唯一区别就是post的body比较特殊
     */
    public abstract RequestBody makeUploadRequestBody();

    @Override
    public Request buildRequest(String environmentTag) {
        return HttpUtils.makePostRequest(getUrl(), getHeaders(), makeUploadRequestBody(), environmentTag);
    }
}
