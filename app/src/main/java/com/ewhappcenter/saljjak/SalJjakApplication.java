package com.ewhappcenter.saljjak;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.util.helper.log.Logger;
import com.parse.Parse;

/**
 * Created by makeus on 2016. 1. 14..
 */
public class SalJjakApplication extends Application {

    private static SalJjakApplication instance;
    private static volatile Activity currentActivity = null;


    public static SalJjakApplication getInstance() {
        return instance;
    }

    public static Activity getCurrentActivity() {
        Logger.d("++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    @Override public void onCreate() {
        super.onCreate();

        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());
        initFresco();
        initParse();
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private void initParse() {
        Parse.initialize(this);
    }

    public class KakaoSDKAdapter extends KakaoAdapter {

        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Activity getTopActivity() {
                    return SalJjakApplication.getCurrentActivity();
                }

                @Override
                public Context getApplicationContext() {
                    return SalJjakApplication.getInstance();
                }
            };
        }
    }
}
