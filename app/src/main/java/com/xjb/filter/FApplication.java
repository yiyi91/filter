package com.xjb.filter;

import android.app.Application;

/**
 * @author xujingbo
 * @date 2017/3/7 17:12
 */
public class FApplication extends Application{
    private static FApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static FApplication getInstance(){
        return instance;
    }
}
