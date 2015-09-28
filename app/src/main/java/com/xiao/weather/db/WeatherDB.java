package com.xiao.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiao.weather.model.City;
import com.xiao.weather.model.Country;
import com.xiao.weather.model.Province;
import com.xiao.weather.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装数据库的常用操作
 * Created by xiao on 2015/9/24.
 */
public class WeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "db_weather";
    /**
     * 数据库版本
     */
    public static final int DB_VERSION= 1;
    /**
     * 让类自身保存其实例
     */
    private static WeatherDB weatherDB;

    private SQLiteDatabase db;
    /**
     * 私有化构照方法
     */
    private WeatherDB(Context context){
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context,DB_NAME,null,DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取WeatherDB实例,也就是提供一个访问该实例的方法
     */
    public synchronized static WeatherDB getInstance(Context context){
        if (weatherDB == null){
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    /**
     * 将province实例存储到数据库中
     */
    public void saveProvince(Province province){
        if (province != null){
            ContentValues cv = new ContentValues();
            cv.put("province_name",province.getProvinceName());
            cv.put("province_code",province.getProviceCode());
            db.insert("Province",null,cv);
        }
    }
    /**
     * 从数据库中读取全国所有省份信息
     */
    public List<Province> loadProvinces(){
        /**
         * 用List集合存储province
         */
        List<Province> provinces = new ArrayList<Province>();

        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProviceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinces.add(province);
            }while (cursor.moveToNext());
        }

        return provinces;
    }
    /**
     * 将City实例存储到数据库
     */
    public void saveCity(City city){
        if (city!=null){
            ContentValues cv = new ContentValues();
            cv.put("city_name",city.getCityName());
            cv.put("city_code",city.getCityCode());
            cv.put("province_id",city.getProvinceId());
            db.insert("City", null, cv);
        }

    }
    /**
     * 从数据库中获取某省所有城市信息
     */
    public List<City> loadCities(int provinceId){
        List<City> cities = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                cities.add(city);
            }while (cursor.moveToNext());
        }

        return cities;
    }

    /**
     * 将Country实例存储到数据库
     */
    public void saveCountry(Country country){
        if (country!=null){
            ContentValues cv = new ContentValues();
            cv.put("country_name",country.getCountryName());
            cv.put("country_code",country.getCountryCode());
            cv.put("city_id",country.getCityId());
            db.insert("Country", null, cv);
        }
    }

    /**
     * 从数据库中获取某省某城市的县信息
     */
    public List<Country> loadCountries(int cityId){
        List<Country> countries = new ArrayList<Country>();
        Cursor cursor = db.query("Country", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()){
            do{
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cityId);
                countries.add(country);
            }while (cursor.moveToNext());
        }

        return countries;
    }

    /**
     * 将User用户信息存储到数据库
     */
    public void saveUser(User user){
        ContentValues cv  = new ContentValues();
        cv.put("user_name",user.getUserName());
        cv.put("user_pwd",user.getUserPwd());
        db.insert("User",null,cv);
    }

    /**
     * 从数据库中获取用户的姓名和密码,验证是否正确
     */
    public int isRight(String userName,String userPwd) {
//        Cursor cursor = db.query("User", null, null, null, null, null, null);
//        if (cursor.moveToFirst()){
//            do{
//                String user_name = cursor.getString(cursor.getColumnIndex("user_name"));
//                String user_pwd = cursor.getString(cursor.getColumnIndex("user_pwd"));
//                if (userName.equals(user_name)) {
//                    return 1;
//                }
//                if (userPwd.equals(user_pwd)) {
//                    return 2;
//                }
//            }while (cursor.moveToNext());
//
//
//        }
        return 3;
    }

}
