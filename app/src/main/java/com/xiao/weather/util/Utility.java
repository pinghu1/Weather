package com.xiao.weather.util;

import android.text.TextUtils;

import com.xiao.weather.db.WeatherDB;
import com.xiao.weather.model.City;
import com.xiao.weather.model.Country;
import com.xiao.weather.model.Province;

/**
 * 工具类来解析和处理从服务器返回的数据
 * Created by xiao on 2015/9/26.
 */
public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvinceResponce(WeatherDB weatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProviceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储到Province表中
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(WeatherDB weatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountriesResponse(WeatherDB weatherDB, String response, int cityId) {

        if (!TextUtils.isEmpty(response)) {
            String[] allCountries = response.split(",");
            if (allCountries != null && allCountries.length > 0) {
                for (String c : allCountries) {
                    String[] array = c.split("\\|");
                    Country country = new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    country.setCityId(cityId);
                    weatherDB.saveCountry(country);
                }
                return true;
            }
        }
        return false;
    }
}