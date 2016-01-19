package com.ewhappcenter.saljjak;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ewhappcenter.saljjak.font.FontHelper;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_dv_logo) SimpleDraweeView dvLogo;
    @Bind(R.id.splash_tv_logo) TextView tvLogo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        final Context context = SalJjakApplication.getInstance();
        FontHelper.getInstance(context).setType(tvLogo, FontHelper.FontType.BOLD);
    }
}
