package com.ewhappcenter.saljjak.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ewhappcenter.saljjak.R;
import com.ewhappcenter.saljjak.signup.SaljjakSignupActivity;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.util.Map;


public class KakaoSessionCheck extends AppCompatActivity {

    /**
     * 유효한 세션이 있다는 검증 후
     * me를 호출하여 가입 여부에 따라 가입 페이지를 그리던지 Main 페이지로 이동 시킨다.
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    private void requestSignUp(final Map<String, String> properties) {
        UserManagement.requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onNotSignedUp() {
            }

            @Override
            public void onSuccess(Long result) {
                requestMe();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                final String message = "UsermgmtResponseCallback : failure : " + errorResult;
                com.kakao.util.helper.log.Logger.w(message);
                //KakaoToast.makeToast(self, message, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }
        }, properties);
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    Log.v("Request Me Error", "네트워크가 불안정 합니다. 나중에 다시 시도해주세요.");
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.v("onSessionClosed", "세션이 닫혔습니다 !!");
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.v("로그인 성공", "사용자 상태검토 후 페이지 이동");
                redirectSignupActivity();
            }

            @Override
            public void onNotSignedUp() {
                Log.v("onNotSignedUp", "회원가입창을 보여주세요!!");
            }
        });
    }

    private void redirectSignupActivity() {
        startActivity(new Intent(this, SaljjakSignupActivity.class));
        finish();
    }

    private void redirectLoginActivity() {
        new MaterialDialog.Builder(this)
                .content("로그인에 실패하였습니다.")
                .positiveText("확인")
                .positiveColorRes(R.color.blue)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        final Intent intent = new Intent(getApplicationContext(), KakaoLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}
