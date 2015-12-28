package com.opticalix.opticalixtemplate.net;

/**
 * Created by opticalix@gmail.com on 15/9/29.
 * Fail code definition at http layer.
 */
public class HttpException extends RuntimeException {
    //Config custom fail code
    public static final int ERROR_AFTER_RESPONSE = 1;
    public static final int ERROR_UNKNOWN = 10086;

    private int mCode;

    public HttpException() {
    }

    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    public HttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public HttpException(Throwable throwable) {
        super(throwable);
    }

    public HttpException(String detailMessage, int code) {
        super(detailMessage);
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
