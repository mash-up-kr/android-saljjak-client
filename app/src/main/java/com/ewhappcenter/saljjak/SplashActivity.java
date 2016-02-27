package com.ewhappcenter.saljjak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ewhappcenter.saljjak.login.KakaoLoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById(R.id.splash).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, KakaoLoginActivity.class));
                finish();
            }
        }, 2950);

    }
}
