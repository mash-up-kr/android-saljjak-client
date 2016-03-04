package com.ewhappcenter.saljjak.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ewhappcenter.saljjak.KakaoProfileInformation;
import com.ewhappcenter.saljjak.R;
import com.ewhappcenter.saljjak.login.KakaoLoginActivity;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaljjakSignupActivity extends Activity {

    private UserProfile userProfile;
    private KakaoProfileInformation profileInformation;

    @Bind(R.id.edit_signup_username) EditText input_signup_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        initializeProfileView();
    }

    private void initializeProfileView() {
        profileInformation = (KakaoProfileInformation) findViewById(R.id.com_kakao_profileinformation);
        ((TextView)findViewById(R.id.text_title)).setText("회원가입");
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbutton();
            }
        });

        // 로그인 하면서 caching되어 있는 profile를 그린다.
        userProfile = UserProfile.loadFromCache();
        if (userProfile != null) {
            profileInformation.setUserProfile(userProfile);
        }

        KakaoTalkService.requestProfile(new KakaoTalkResponseCallback<KakaoTalkProfile>() {
            @Override
            public void onSuccess(KakaoTalkProfile result) {
                applyTalkProfileToView(result);
            }
        });

    }

    private void backbutton(){
        new MaterialDialog.Builder(this)
                .title("회원가입 취소")
                .content("홈화면으로 돌아가시겠습니까?")
                .positiveText("확인")
                .positiveColorRes(R.color.blue)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        UserManagement.requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                final Intent intent = new Intent(SaljjakSignupActivity.this, KakaoLoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                })
                .negativeText("최소")
                .negativeColorRes(R.color.blue)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    // profile view에서 talk profile을 update 한다.
    private void applyTalkProfileToView(final KakaoTalkProfile talkProfile) {
        if (profileInformation != null) {
            if (userProfile != null) {
                profileInformation.setUserProfile(userProfile);
            }
            final String profileImageURL = talkProfile.getProfileImageUrl();
            if (profileImageURL != null)
                profileInformation.setProfileURL(profileImageURL);

            final String nickName = talkProfile.getNickName();
            input_signup_username.setText(nickName);

        }
    }

    public abstract class KakaoTalkResponseCallback<T> extends TalkResponseCallback<T> {

        @Override
        public void onNotKakaoTalkUser() {
            // KakaoToast.makeToast(self, "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            // KakaoToast.makeToast(self, "failure : " + errorResult, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {

        }

        @Override
        public void onNotSignedUp() {

        }

        @Override
        public void onDidStart() {
            // showWaitingDialog();
        }

        @Override
        public void onDidEnd() {
            //cancelWaitingDialog();
        }
    }
}
