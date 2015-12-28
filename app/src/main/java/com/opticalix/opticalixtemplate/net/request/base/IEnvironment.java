package com.opticalix.opticalixtemplate.net.request.base;

/**
 * 发起请求的场景实现，以此tag拼接Request.uniTag作为凭据cancel
 * Created by opticalix@gmail.com on 2015/10/7.
 */
public interface IEnvironment {
    /**
     * 返回当前发起请求环境的TAG标识
     *
     * @return
     */
    String getEnvironmentTag();
}
