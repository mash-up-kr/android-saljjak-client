package com.ewhappcenter.saljjak.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ewhappcenter.saljjak.R;
import com.ewhappcenter.saljjak.ui.login.KakaoLoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, KakaoLoginActivity.class));
                finish();
            }
        }, 1000);

    }
}
