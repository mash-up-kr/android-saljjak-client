package com.ewhappcenter.saljjak;

import android.app.Application;

/**
 * Created by makeus on 2016. 1. 14..
 */
public class SalJjakApplication extends Application {
    private static SalJjakApplication instance;

    public static SalJjakApplication getInstance() {
        return instance;
    }

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
