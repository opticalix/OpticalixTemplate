//package com.opticalix.opticalixtemplate.net.callback;
//
//import android.content.Context;
//import android.os.Handler;
//
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//
///**
// * Created by opticalix@gmail.com on 2015/10/7.
// * 解封status/data.
// * 回调在主线程
// */
//public abstract class BaseEduJsonUICallback extends BaseEduJsonCallback {
//    private final Context mContext;
//    private IBaseLoadHandler mLoadHandler;
//    protected Handler mHandler = new Handler();
//
//    public BaseEduJsonUICallback(Context context, IBaseLoadHandler loadHandler) {
//        this.mLoadHandler = loadHandler;
//        mContext = context;
//    }
//
//    /**
//     * Object: new TypeToken<Model>(){}.getType();
//     * List: new TypeToken<List<Model>(){}.getType();
//     *
//     * @return
//     */
//    protected abstract Type getJsonType();
//
//    @Override
//    public void onResponseSuccessful(Response response) {
//        super.onResponseSuccessful(response);
//    }
//
//    /**
//     * 解析结果为Object/List
//     * null代表parseResponse错误
//     *
//     * @param result
//     */
//    final protected void onGson(final Object result) {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                onGsonUI(result);
//            }
//        });
//    }
//
//    @Override
//    final public void onResponseFailure(final Response response, final int httpCode) {
//        super.onResponseFailure(response, httpCode);
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                onResponseFailureUI(response, httpCode);
//            }
//        });
//    }
//
//    @Override
//    final public void onFailure(final Request request, final IOException e) {
//        super.onFailure(request, e);
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                onFailureUI(request, e);
//            }
//        });
//    }
//
//    /**
//     * 特殊情形不使用Gson
//     */
//    final protected boolean onOriginalJson(final Object object) {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                onOriginalJsonUI(object);
//            }
//        });
//
//        return isOriginalJson();
//    }
//
//
//    /**
//     * 返回由子类提供的jsonType解析后的model对象或列表
//     * UI线程
//     *
//     * @param result
//     */
//    protected void onGsonUI(Object result) {
//        mLoadHandler.onDataBack(result);
//    }
//
//    /**
//     * 请求已成功发起但失败
//     * UI线程
//     *
//     * @param response
//     * @param yyCode
//     */
//    protected void onResponseFailureUI(Response response, int yyCode) {
//        try {
//            if (yyCode == HttpConfig.HTTP_CUSTOM_ERROR_CODE_YY_EMPTY) {
//                mLoadHandler.onDataFail(DataFailType.DATA_EMPTY);
//            } else {
//                mLoadHandler.onDataFail(DataFailType.DATA_FAIL);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 取消/无网络/超时
//     * UI线程
//     *
//     * @param request
//     * @param e
//     */
//    protected void onFailureUI(Request request, IOException e) {
//        try {
//            if (!NetUtils.isNetConnected(mContext)) {
//                mLoadHandler.onDataFail(DataFailType.DATA_NO_NET);
//            } else {
//                mLoadHandler.onDataFail(DataFailType.DATA_FAIL);
//            }
//        } catch (Exception ex) {
//            e.printStackTrace();
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * 特殊情形，不使用Gson解析，自己解析JsonObject/JsonArray
//     * UI线程
//     *
//     * @param object JsonObject或JsonArray
//     * @return
//     */
//    protected void onOriginalJsonUI(Object object) {
//    }
//
//    /**
//     * 特殊情形，不使用Gson解析，自己解析JsonObject/JsonArray
//     *
//     * @return true代表不使用gson
//     */
//    protected boolean isOriginalJson() {
//        return false;
//    }
//}
