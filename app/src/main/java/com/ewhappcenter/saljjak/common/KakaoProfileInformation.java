package com.ewhappcenter.saljjak.common;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ewhappcenter.saljjak.R;
import com.ewhappcenter.saljjak.SalJjakApplication;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

public class KakaoProfileInformation extends FrameLayout {

    private MeResponseCallback meResponseCallback;

    private String nickname;
    private String userId;
    private SimpleDraweeView profile;

    public KakaoProfileInformation(Context context) {
        super(context);
        initView();
    }

    public KakaoProfileInformation(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KakaoProfileInformation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.kakao_profile_information, this);
        profile = (SimpleDraweeView) view.findViewById(R.id.user_profile_image);
    }

    /**
     * 사용자정보 요청 결과에 따른 callback을 설정한다.
     * @param callback 사용자정보 요청 결과에 따른 callback
     */
    public void setMeResponseCallback(final MeResponseCallback callback){
        this.meResponseCallback = callback;
    }

    /**
     * param으로 온 UserProfile에 대해 view를 update한다.
     * @param userProfile 화면에 반영할 사용자 정보
     */
    public void setUserProfile(final UserProfile userProfile) {
        setProfileURL(userProfile.getProfileImagePath());
        setNickname(userProfile.getNickname());
        setUserId(String.valueOf(userProfile.getId()));
    }

    /**
     * 프로필 이미지에 대해 view를 update한다.
     * @param profileImageURL 화면에 반영할 프로필 이미지
     */
    public void setProfileURL(final String profileImageURL) {
        if (profile != null && profileImageURL != null) {
            Application app = SalJjakApplication.getInstance();
            if (app == null)
                throw new UnsupportedOperationException("needs com.kakao.GlobalApplication in order to use ImageLoader");

            Uri uri = Uri.parse(profileImageURL);
            profile.setImageURI(uri);
        }
    }


    /**
     * 별명 view를 update한다.
     * @param nickname 화면에 반영할 별명
     */
    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }
    /**
     * 사용자 아이디 view를 update한다.
     * @param userId 화면에 반영할 사용자 아이디
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * 사용자 정보를 요청한다.
     */
    public void requestMe() {
        UserManagement.requestMe(meResponseCallback);
    }

}
