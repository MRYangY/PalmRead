package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.SharePreferenceUtils;
import com.example.yangyu.palmread.Util.ToastUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yangyu on 17/3/18.
 */

public class AccountRegisterFragment extends BaseFragment {
    private View mLayout;
    private EditText mAccount;
    private EditText mPassWord;
    private EditText mRePassWord;
    private CircleImageView mRegister;

    private String account;
    private String pwd;
    private String rePwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register,null);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRegister.setOnClickListener(mRegisterListener);
    }

    private View.OnClickListener mRegisterListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            account=mAccount.getText().toString().trim();
            pwd=mPassWord.getText().toString().trim();
            rePwd=mRePassWord.getText().toString().trim();
            if (TextUtils.isEmpty(account)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(rePwd)){
                ToastUtils.TipToast(getContext(),"不能填入空信息");
            }else if (!pwd.equals(rePwd)){
                ToastUtils.TipToast(getContext(),"密码不一致");
            }else{
                ToastUtils.TipToast(getContext(),"注册成功");
                SharePreferenceUtils.setStringData(getContext(),account, ProjectContent.USER_ACCOUNT);
                SharePreferenceUtils.setStringData(getContext(),pwd,ProjectContent.USER_PASSWORD);
                Intent intent=new Intent();
                intent.setAction(ProjectContent.REGISTER_OK);
                getContext().sendBroadcast(intent);
            }
        }
    };
    @Override
    protected void initView() {
        mAccount = (EditText) mLayout.findViewById(R.id.person_account);
        mPassWord = (EditText) mLayout.findViewById(R.id.person_password);
        mRePassWord = (EditText) mLayout.findViewById(R.id.person_re_password);
        mRegister = (CircleImageView) mLayout.findViewById(R.id.register);
    }

    @Override
    protected void initData() {

    }
}
