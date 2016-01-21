package com.ewhappcenter.saljjak;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.parse.Parse;

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

        initFresco();
        initParse();
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private void initParse() {
        Parse.initialize(this);
    }
}
