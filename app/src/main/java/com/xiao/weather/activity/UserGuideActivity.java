package com.xiao.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiao.weather.R;
import com.xiao.weather.adapter.GuideViewAdapter;
import com.xiao.weather.controller.ActivityCollector;
import com.xiao.weather.controller.BaseActivity;
import com.xiao.weather.controller.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * app第一次启动时，欢迎页面
 *
 * Created by xiao on 2015/9/26.
 */
public class UserGuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private GuideViewAdapter guideViewAdapter;

    /**
     * 存储Imageview
     */
//    private List<ImageView> mImgViews = new ArrayList<ImageView>();
//    private int[] mImgIds = new int[]{R.drawable.frame_view1, R.drawable.frame_view2, R.drawable.frame_view4};
    private List<View> mViews;

    private int[] dots = new int[]{R.id.dot1,R.id.dot2,R.id.dot3,R.id.dot4};
    private ImageView[] dotsImg;
    private LinearLayout dotll;
    private Button btnStartWeatherAty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_guide_aty);
//        initImgViews();
        initView();
        initDots();
        mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
//        guideViewAdapter = new GuideViewAdapter(this, mImgViews);
        guideViewAdapter = new GuideViewAdapter(this,mViews);
        mViewPager.setAdapter(guideViewAdapter);
        //设置切换效果
        //mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //设置pager改变效果
        mViewPager.setOnPageChangeListener(this);
        btnStartWeatherAty = (Button) mViews.get(3).findViewById(R.id.btnStartWeatherAty);
        btnStartWeatherAty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserGuideActivity.this,WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
    /**
     * 初始化mImgViews
     */
//    public void initImgViews() {
//        for (int imgId : mImgIds) {
//            ImageView imageView = new ImageView(getApplicationContext());
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageView.setImageResource(imgId);
//            mImgViews.add(imageView);
//        }
//    }

    /**
     * 将View添加到mViews
     */
    public void initView(){
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        mViews = new ArrayList<View>();
        mViews.add(inflater.inflate(R.layout.guide_one,null));
        mViews.add(inflater.inflate(R.layout.guide_two,null));
        mViews.add(inflater.inflate(R.layout.guide_three,null));
        mViews.add(inflater.inflate(R.layout.guide_four,null));

    }

    /**
     * 初始化点
     */
    public void initDots(){
        dotll = (LinearLayout) findViewById(R.id.dotll);
        dotsImg  = new ImageView[mViews.size()];
        for (int i = 0;i<mViews.size();i++){
            dotsImg[i] = (ImageView) findViewById(dots[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * page改变时，替换dotsImg
     * @param position
     */

    @Override
    public void onPageSelected(int position) {

        if (position==dots.length-1){
            dotll.setVisibility(View.INVISIBLE);
        }else{
            dotll.setVisibility(View.VISIBLE);
            for (int i = 0;i<dots.length-1;i++){
                if (i==position){
                    dotsImg[i].setImageResource(R.drawable.on_select_point);
                }else{
                    dotsImg[i].setImageResource(R.drawable.select_point);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
