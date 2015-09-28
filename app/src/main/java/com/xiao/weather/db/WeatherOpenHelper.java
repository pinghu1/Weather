package com.xiao.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.DoubleBuffer;

/**
 * Created by xiao on 2015/9/24.
 * 创建表存储
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_PROVINCE = "create table Province(" +
            "id integer primary key autoincrement," +
            "province_name text," +
            "province_code text" +
            ")";
    private static  final String CREATE_CITY = "create table City(" +
            "id integer primary key autoincrement," +
            "city_name text," +
            "city_code text," +
            "province_id integer" +
            ")";

    private static  final String CREATE_COUNTRY = "create table Country(" +
            "id integer primary key autoincrement," +
            "country_name text," +
            "country_code text," +
            "city_id integer" +
            ")";
    private static  final String  CRETE_USER = "create table User(" +
            "user_name text," +
            "user_pwd text" +
            ")";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CRETE_USER);//创建用户表
        db.execSQL(CREATE_PROVINCE);//创建Province表
        db.execSQL(CREATE_CITY);//创建城市表
        db.execSQL(CREATE_COUNTRY);//创建县表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
