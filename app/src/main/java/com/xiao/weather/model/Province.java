package com.xiao.weather.model;

/**
 * Created by xiao on 2015/9/24.
 */
public class Province {
    /**
     * 城市的id,name,code(编码)
     */
    private  int id;
    private String provinceName;
    private String proviceCode;


    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProviceCode() {
        return proviceCode;
    }

    public void setProviceCode(String proviceCode) {
        this.proviceCode = proviceCode;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
