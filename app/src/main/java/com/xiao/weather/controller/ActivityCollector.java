package com.xiao.weather.controller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2015/9/23.
 * 用于管理全局的activity资源
 */
public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 添加actvity到activities
     * @param activity
     */
    public static  void addActivity(Activity activity){
        if (!activities.contains(activity)){
            activities.add(activity);
        }
    }

    /**
     * 移除
     * @param activity
     */
    public static  void removeActivity(Activity activity){
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }
    /**
     * 全部移除
     */
    public static void finishAll(){
        for(Activity activity:activities){
            removeActivity(activity);
        }
    }


}
