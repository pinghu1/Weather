package com.xiao.weather.util;

/**
 * Created by xiao on 2015/9/26.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
