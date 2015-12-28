package com.opticalix.opticalixtemplate.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.opticalix.opticalixtemplate.net.HttpException;
import com.opticalix.opticalixtemplate.utils.GsonUtils;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by opticalix@gmail.com on 15/12/24.
 */
public abstract class BaseJsonCallback extends BaseCallback {

    @Override
    public void onResponseSuccessful(Response response) {
        try {
            String json = preHandle(response);
            Object fromJson = offerGson().fromJson(json, offerJsonType());
            onGson(fromJson);
        } catch (Exception e) {
            e.printStackTrace();
            onResponseFailure(response, HttpException.ERROR_AFTER_RESPONSE);
        }
    }


    /**
     * Object: new TypeToken<Model>(){}.getType();
     * List: new TypeToken<List<Model>(){}.getType();
     *
     * @return
     */
    protected abstract Type offerJsonType();

    protected String preHandle(Response response) throws IOException {
        return response.body().string();
    }

    protected Gson offerGson(){
        return GsonUtils.getSimpleGson();
    }

    protected abstract void onGson(Object result);
}
