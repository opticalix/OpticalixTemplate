package com.opticalix.opticalixtemplate.net.test;

/**
 * Created by opticalix@gmail.com on 15/12/28.
 * http://httpbin.org
 */
public class HttpBinModel {
    public String args;
    public String headers;
    public String origin;
    public String url;

    @Override
    public String toString() {
        return "HttpBinModel{" +
                "args='" + args + '\'' +
                ", headers='" + headers + '\'' +
                ", origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
