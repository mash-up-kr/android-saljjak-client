package com.ewhappcenter.saljjak.ui.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ewhappcenter.saljjak.R;
import com.ewhappcenter.saljjak.common.KakaoProfileInformation;
import com.ewhappcenter.saljjak.ui.login.KakaoLoginActivity;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaljjakSignupActivity extends Activity {

    private UserProfile userProfile;

    @Bind(R.id.actionbar_tv_title) TextView tvActionbarTitle;

    @Bind(R.id.edit_signup_username) EditText etInputUsername;
    @Bind(R.id.com_kakao_profileinformation) KakaoProfileInformation profileInformation;

    @OnClick(R.id.actionbar_btn_back)
    void onActionbarBackButtonClicked() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        initializeProfileView();
    }

    private void initializeProfileView() {
        tvActionbarTitle.setText("회원가입");

        // 로그인 하면서 caching되어 있는 profile를 그린다.
        userProfile = UserProfile.loadFromCache();
        if (userProfile != null) {
            profileInformation.setUserProfile(userProfile);
        }

        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("TAG", "Session closed");
            }

            @Override
            public void onNotSignedUp() {
                Log.e("TAG", "No signedUp");
            }

            @Override
            public void onSuccess(UserProfile result) {
                applyTalkProfileToView(result);
            }
        });
    }

    @Override
    public void onBackPressed() {
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
                .negativeText("취소")
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
    private void applyTalkProfileToView(final UserProfile userProfile) {
        if (profileInformation != null) {
            if (userProfile != null) {
                profileInformation.setUserProfile(userProfile);
            }
            final String profileImageURL = userProfile.getProfileImagePath();
            if (profileImageURL != null)
                profileInformation.setProfileURL(profileImageURL);

            final String nickName = userProfile.getNickname();
            etInputUsername.setText(nickName);

        }
    }
}
