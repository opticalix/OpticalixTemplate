//package com.opticalix.opticalixtemplate.net.callback;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.opticalix.opticalixtemplate.net.HttpConfig;
//import com.opticalix.opticalixtemplate.net.HttpUtils;
//import com.squareup.okhttp.Response;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//
//import java.lang.reflect.Type;
//
///**
// * Created by opticalix@gmail.com on 2015/10/7.
// * 解封status/data
// */
//public abstract class BaseEduJsonCallback extends BaseCallback {
//    /**
//     * Object: new TypeToken<Model>(){}.getType();
//     * List: new TypeToken<List<Model>(){}.getType();
//     *
//     * @return
//     */
//    protected abstract Type getJsonType();
//
//    /**
//     * 这里针对status-data做了适配 fix:应该在子类做
//     *
//     * @param response
//     */
//    @Override
//    @SuppressWarnings("")
//    public void onResponseSuccessful(Response response) {
//        try {
//            final Type type = getJsonType();
//            String bodyString = response.body().string();
//            Log.d("result", "result-------------->>" + bodyString);
//            if (isStatusDataFormat()) {
//                //status-data
//                JSONObject jsonObject = (JSONObject) parseResponse(HttpUtils.filterWrongData(bodyString));
//                JSONObject status = jsonObject.optJSONObject(getStatus());
//                printStatus(status);
//                int code = getCorrectCode();
//                if (isStatusContainCode()) {
//                    code = (int) status.get(getCode());
//                }
//                if (code != getCorrectCode()) {
//                    onResponseFailure(response, code);
//                    return;
//                }
//                Object data = jsonObject.opt(getData());
//                if(isDataEmpty(data.toString())){
//                    onResponseFailure(response, HttpConfig.HTTP_CUSTOM_ERROR_CODE_YY_EMPTY);
//                    return;
//                }
//                if (onOriginalJson(data))//对于非典型格式选择自己处理
//                    return;
//                //normal json
//                Object o = mGson.fromJson(data.toString(), type);
//                onGson(o);
//            } else {
//                if (onOriginalJson(new JSONObject(HttpUtils.filterWrongData(bodyString))))//对于非典型格式选择自己处理
//                    return;
//                //normal json
//                onGson(mGson.fromJson(bodyString, type));
//            }
//
//        } catch (Exception e) {
//            if (e instanceof JSONException) {
//                Log.e(this.getClass().getSimpleName(), "Response may not be 'stats-data' formation", e);
//            } else if (e instanceof NullPointerException) {
//                Log.e(this.getClass().getSimpleName(), "Npe occurs at onResponseSuccessful method", e);
//            } else {
//                e.printStackTrace();
//            }
//            //统一出口
//            onResponseFailure(response, HttpConfig.HTTP_CUSTOM_ERROR_CODE_PARSE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 解析结果为Object/List
//     * null代表parseResponse错误
//     *
//     * @param result
//     */
//    protected abstract void onGson(Object result);
//
//
//    protected Object parseResponse(String responseBody) throws JSONException {
//        if (null == responseBody)
//            return null;
//        Object result = null;
//        // trim the string to prevent start with blank, and test if the string
//        // is valid JSON, because the parser don't do this :(. If Json is not
//        // valid this will return null
//        String jsonString = responseBody.trim();
//        if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
//            result = new JSONTokener(jsonString).nextValue();
//        }
//        if (result == null) {
//            result = jsonString;
//        }
//        return result;
//    }
//
//    protected void printStatus(JSONObject jsonObject) {
//        if (jsonObject == null)
//            return;
//        String code = jsonObject.optString("code");
//        String msg = jsonObject.optString("msg");
//        String tips = jsonObject.optString("tips");
//        String sip = jsonObject.optString("sip");
//        Log.d(getClass().getSimpleName(), String.format("code=%s,msg=%s,tips=%s,sip=%s", code, msg, tips, sip));
//    }
//
//
//    /**
//     * 特殊情形不使用Gson
//     */
//    protected boolean onOriginalJson(Object object) {
//        return false;
//    }
//
//    /**
//     * 是否是Edu规定的 status-data格式
//     *
//     * @return
//     */
//    protected boolean isStatusDataFormat() {
//        return true;
//    }
//
//    protected boolean isDataEmpty(String data){
//        return TextUtils.isEmpty(data)||"[]".equals(data)||"{}".equals(data);
//    }
//
//    /**
//     * status中是否包含code字段
//     *
//     * @return
//     */
//    protected boolean isStatusContainCode() {
//        return true;
//    }
//
//    protected String getStatus() {
//        return "status";
//    }
//
//    protected String getData() {
//        return "data";
//    }
//
//    /**
//     * 自定义正确code
//     *
//     * @return
//     */
//    protected int getCorrectCode() {
//        return 0;
//    }
//
//    protected final String getCode() {
//        return "code";
//    }
//
//    protected String getEncoding() {
//        return "utf-8";
//    }
//}
