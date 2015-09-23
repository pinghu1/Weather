package com.xiao.weather.controller;

import android.app.Activity;
import android.os.Bundle;

/**
 *  自定义基础的activity
 * Created by xiao on 2015/9/23.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("baseActivity", getClass().getName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
