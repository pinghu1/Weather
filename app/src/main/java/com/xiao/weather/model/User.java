package com.xiao.weather.model;

/**
 * Created by xiao on 2015/9/25.
 */
public class User {
    /**
     * 用户名字，密码
     */
    private String userName;
    private String userPwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
