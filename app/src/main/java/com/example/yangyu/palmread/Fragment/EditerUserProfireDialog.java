package com.example.yangyu.palmread.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yangyu.palmread.R;

/**
 * Created by yangyu on 17/3/18.
 */

public class EditerUserProfireDialog extends DialogFragment {

    private View mLayout;
    private TextView cancel;
    private TextView confirm;
    private TextView title;
    private EditText content;

    private static final String TYPE_NAME="name";
    private static final String TYPE_INTRODUCE="introduce";
    private String mType;

    public interface DialogFragmentDataImp{//定义一个与Activity通信的接口，使用该DialogFragment的Activity须实现该接口
        void setInfoName(String message);
        void setInfoIntroduce(String message);
    }

    public static EditerUserProfireDialog newInstance(String message){
        //创建一个带有参数的Fragment实例
        EditerUserProfireDialog fragment = new EditerUserProfireDialog();
        Bundle bundle = new Bundle();
        bundle.putString("type",message);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = LayoutInflater.from(getContext()).inflate(R.layout.dialog_editer_user_profire, container);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel = (TextView) mLayout.findViewById(R.id.cancel);
        confirm = (TextView) mLayout.findViewById(R.id.confirm);
        title = (TextView) mLayout.findViewById(R.id.dialog_title);
        content = (EditText) mLayout.findViewById(R.id.dialog_content);
        cancel.setOnClickListener(mCancelListener);
        confirm.setOnClickListener(mConfirmListener);
        return mLayout;
    }
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mType = getArguments().getString("type",null);
        if (mType.equals(TYPE_NAME)){
            title.setText("编辑用户名");
        }else if (mType.equals(TYPE_INTRODUCE)){
            title.setText("编辑介绍");
        }
    }

    private View.OnClickListener mCancelListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private View.OnClickListener mConfirmListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = content.getText().toString();
            DialogFragmentDataImp imp=(DialogFragmentDataImp) getActivity();
            if (mType.equals(TYPE_NAME)){
                imp.setInfoName(s);
            }else if (mType.equals(TYPE_INTRODUCE)){
                imp.setInfoIntroduce(s);
            }
            dismiss();
        }
    };

}
