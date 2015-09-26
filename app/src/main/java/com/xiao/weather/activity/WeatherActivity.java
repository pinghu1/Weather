package com.xiao.weather.activity;

import android.app.Activity;
import android.os.Bundle;

import com.xiao.weather.R;
import com.xiao.weather.controller.BaseActivity;

/**
 * Created by xiao on 2015/9/26.
 */
public class WeatherActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_aty);
    }
}
