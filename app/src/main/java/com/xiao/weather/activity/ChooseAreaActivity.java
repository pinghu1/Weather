package com.xiao.weather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.weather.R;
import com.xiao.weather.controller.BaseActivity;
import com.xiao.weather.db.WeatherDB;
import com.xiao.weather.model.City;
import com.xiao.weather.model.Country;
import com.xiao.weather.model.Province;
import com.xiao.weather.util.HttpCallbackListener;
import com.xiao.weather.util.HttpUtil;
import com.xiao.weather.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2015/9/26.
 */
public class ChooseAreaActivity extends BaseActivity {
    private static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB weatherDB;

    private List<String> dataList = new ArrayList<String>();

    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<Country> countryList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 选中的县
     */
    private Country selectedCountry;
    /**
     * 当前选中的级别
     */
    private int currentLevel;

    /**
     * 是否从weatheractivity跳转过来
     */
    private boolean isFromWeatherActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //已经选择了城市且不是从weatheractivity跳转过来，才会直接跳转到weatheractivity
        if (preferences.getBoolean("selectedCity",false)&&!isFromWeatherActivity){
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);

        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.list_text);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        weatherDB = WeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    //加载城市数据
                    queryCities();

                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    //加载县数据
                    queryCountries();
                }else  if (currentLevel == LEVEL_COUNTRY){
                    String countryCode =countryList.get(position).getCountryCode();
                    Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                    intent.putExtra("country_code",countryCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        //加载省级数据
        queryProvinces();

    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces() {
        provinceList = weatherDB.loadProvinces();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            //从服务器上查询
            queryFromSever(null,"province");

        }
    }

    /**
     * 查询选择省所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCities() {
        //从数据库中查询
        cityList = weatherDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            //currentLevel置为LEVEL_CITY
            currentLevel = LEVEL_CITY;
        } else {
            //从服务器上查询
            queryFromSever(selectedProvince.getProviceCode(),"city");
        }
    }

    /**
     * 查询选择市所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCountries() {
        countryList = weatherDB.loadCountries(selectedCity.getId());
        if (countryList.size() > 0) {
            dataList.clear();
            for (Country country : countryList) {
                dataList.add(country.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTRY;
        } else {
            //从服务器上查询
            queryFromSever(selectedCity.getCityCode(),"country");
        }
    }

    /**
     * 根据传入的代号和类型从服务器上查询省市县数据
     */
    private void queryFromSever(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        //显示进度条
        showProgressDialog();

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponce(weatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(weatherDB, response, selectedProvince.getId());
                } else if ("country".equals(type)) {
                    //保存到数据库中
                    result = Utility.handleCountriesResponse(weatherDB, response, selectedCity.getId());
                }
                if (result) {
                    //通过runOnUiThread方法回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //关闭进度条
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("country".equals(type)) {
                                queryCountries();
                            }

                        }

                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //关闭进度条
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度条
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */;
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    /**
     * 捕获back按键，根据当前的级别来判断，此时应该返回市列表，省列表，直接退出
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTRY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            if (isFromWeatherActivity){
                Intent intent = new Intent(this,WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }



}
