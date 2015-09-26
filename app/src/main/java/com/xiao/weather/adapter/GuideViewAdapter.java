package com.xiao.weather.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 *
 * viewpager的适配器，用于app欢迎页的适配
 * Created by xiao on 2015/9/26.
 */
public class GuideViewAdapter extends PagerAdapter {
    private Context context;
    /**
     * 存储view
     */
    //private List<ImageView> mImgViews ;
    private List<View> mViews;
    public GuideViewAdapter(Context context,List<View> mViews ){
        this.context = context;
//        this.mImgViews = mImgViews;
        this.mViews = mViews;
    }
    /**
     * 添加view
     */
    @Override
    public Object instantiateItem(View container, int position) {

//        ((ViewPager)container).addView(mImgViews.get(position));
//        return mImgViews.get(position);
        ((ViewPager)container).addView(mViews.get(position));
        return mViews.get(position);

    }

    /**
     * 移除view
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
//        ((ViewPager)container).removeView(mImgViews.get(position));
        ((ViewPager)container).removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
//        return mImgViews.size();
        return mViews.size();
      }
    /**
     * 判断当前的view 是不是需要的对象
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
}
