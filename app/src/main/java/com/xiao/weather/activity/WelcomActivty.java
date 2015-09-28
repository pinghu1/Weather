package com.xiao.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.xiao.weather.R;
import com.xiao.weather.adapter.GuideViewAdapter;
import com.xiao.weather.controller.BaseActivity;



/**
 * 每次启动程序欢迎界面
 * Created by xiao on 2015/9/26.
 */
public class WelcomActivty extends Activity {
    /**
     * 在此界面延迟2秒再跳转
     */
    private static final int DELAY_TIME = 2000;

    private static  final int GO_WEATHER_ATY = 1000;
    private static final int GOO_GUIDE_ATY = 1002;
    boolean isFirstIn = false;
    /**
     * 消息处理
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_WEATHER_ATY:
                    goWeatherAty();
                    break;
                case GOO_GUIDE_ATY:
                    goGuideAty();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_aty);
        init();
    }
    private void init(){
        SharedPreferences preferences = getSharedPreferences("storeFirstIn",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn",true);
        //判断是否是首次进入
        if (!isFirstIn){//
            mHandler.sendEmptyMessageDelayed(GO_WEATHER_ATY,DELAY_TIME);
        }else{
            mHandler.sendEmptyMessageDelayed(GOO_GUIDE_ATY,DELAY_TIME);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
        }
    }

    private void goWeatherAty(){
        Intent intent = new Intent(WelcomActivty.this,ChooseAreaActivity.class);
        startActivity(intent);
        finish();//finish掉不用的界面
    }

    private void goGuideAty() {
        Intent intent = new Intent(WelcomActivty.this,UserGuideActivity.class);
        startActivity(intent);
        finish();//finish掉不用的界面
    }
}
