package com.ewhappcenter.saljjak.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ewhappcenter.saljjak.KakaoProfileInformation;
import com.ewhappcenter.saljjak.R;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.response.model.UserProfile;

/**
 * Created by JungMin on 2016-02-27.
 */
public class SaljjakSignupActivity extends Activity {
    private UserProfile userProfile;
    private KakaoProfileInformation profileInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeProfileView();
    }

    private void initializeProfileView() {
        profileInformation = (KakaoProfileInformation) findViewById(R.id.com_kakao_profileinformation);
        ((TextView)findViewById(R.id.text_title)).setText("회원가입");

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
            if (nickName != null)
                profileInformation.setNickname(nickName);

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
