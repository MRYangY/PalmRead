package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Logic.HomeJsonToResult;
import com.example.yangyu.palmread.Models.GetQQUserInfo;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.SharePreferenceUtils;
import com.example.yangyu.palmread.Util.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.yangyu.palmread.Constant.ProjectContent.mAppid;

/**
 * Created by yangyu on 17/3/18.
 */

public class AccountLoginFragment extends BaseFragment {

    private static final String TAG = "AccountLoginFragment";

    private View mLayout;
    private CircleImageView mLogin;
    private ImageView mQQLogin;
    private ImageView mWeiXinLogin;

    public static Tencent mTencent;

    private boolean isServerSideLogin = false;
    private GetQQUserInfo qqUserInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        return mLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTencent = Tencent.createInstance(mAppid, getActivity().getApplicationContext());
    }

//    private View.OnClickListener mLoginListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String account = mAccount.getText().toString().trim();
//            String pwd = mPassword.getText().toString().trim();
//            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
//                ToastUtils.TipToast(getContext(), "不能输入空信息");
//            } else if (account.equals(SharePreferenceUtils.getStringData(getContext()
//                    , ProjectContent.USER_ACCOUNT)) && pwd.equals(SharePreferenceUtils.getStringData(getContext()
//                    , ProjectContent.USER_PASSWORD))) {
//                ToastUtils.TipToast(getContext(), "登录成功");
//                SharePreferenceUtils.setBoolean(getContext(), true, ProjectContent.LOGIN_OK);
//                Intent intent = new Intent();
//                intent.setAction(ProjectContent.INTENT_LOGIN_OK);
//                getContext().sendBroadcast(intent);
//            } else {
//                ToastUtils.TipToast(getContext(), "账号密码错误");
//            }
//        }
//    };

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    if (o != null) {
                        JSONObject jo = (JSONObject) o;
                        qqUserInfo = HomeJsonToResult.QQUserInfoParse(GetQQUserInfo.class, jo.toString());
                        if (qqUserInfo != null) {
                            Intent intent = new Intent();
                            intent.putExtra(ProjectContent.EXTRA_QQ_USER_INFO, qqUserInfo);
                            intent.setAction(ProjectContent.INTENT_LOGIN_OK);
                            getContext().sendBroadcast(intent);
                            SharePreferenceUtils.setBoolean(getContext(), true, ProjectContent.LOGIN_OK);
                            SharePreferenceUtils.setStringData(getActivity(), qqUserInfo.getNickname(), ProjectContent.USER_INFO_NAME);
                            SharePreferenceUtils.setStringData(getActivity(), qqUserInfo.getFigureurl_qq_2(), ProjectContent.USER_INFO_PIC);
                        } else {
                            ToastUtils.TipToast(getActivity(), "登录出错");
                        }
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    ToastUtils.TipToast(getActivity(), uiError.errorDetail);
                }

                @Override
                public void onCancel() {
                    ToastUtils.TipToast(getActivity(), "onCancel");
                }
            };
            UserInfo mInfo = new UserInfo(getActivity(), mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.TipToast(getActivity(), "返回为空,登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse.length() == 0) {
                ToastUtils.TipToast(getActivity(), "返回为空,登录失败");
                return;
            }
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            ToastUtils.TipToast(getActivity(), "onError: " + e.errorDetail);

        }

        @Override
        public void onCancel() {
            ToastUtils.TipToast(getActivity(), "onCancel: ");
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }

    private View.OnClickListener mQQLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mTencent.isSessionValid()) {
                mTencent.login(getActivity(), "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
            } else {
                if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                    mTencent.logout(getActivity());
                    mTencent.login(getActivity(), "all", loginListener);
                    isServerSideLogin = false;
                    Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                    return;
                }
                mTencent.logout(getActivity());

            }
        }
    };


    private View.OnClickListener mWeiChatListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.TipToast(getActivity(), "微信登录功能暂无开启");
        }
    };

    @Override
    protected void initView() {
        mLogin = (CircleImageView) mLayout.findViewById(R.id.login);
        mQQLogin = (ImageView) mLayout.findViewById(R.id.qq_login);
        mWeiXinLogin = (ImageView) mLayout.findViewById(R.id.weixin_login);
    }

    @Override
    protected void initData() {
//        mLogin.setOnClickListener(mLoginListener);
        mQQLogin.setOnClickListener(mQQLoginListener);
        mWeiXinLogin.setOnClickListener(mWeiChatListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            Log.e(TAG, "initOpenidAndToken: token:" + token + "--" + "openId:" + openId);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }
}
