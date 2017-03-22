package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.EditerUserInfoActivity;
import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.CommonUtils;
import com.example.yangyu.palmread.Util.SharePreferenceUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yangyu on 17/3/18.
 */

public class UserLoginedFragment extends BaseFragment {

    private View mLayout;
    private ImageView mSetting;
    private TextView userName;
    private TextView userSignature;
    private CircleImageView userIcon;
    private PopupWindow popupWindow;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_logined,null);
        return mLayout;
    }

    @Override
    protected void initView() {
        mSetting = (ImageView) mLayout.findViewById(R.id.user_setting);
        userName = (TextView) mLayout.findViewById(R.id.user_name);
        userSignature = (TextView) mLayout.findViewById(R.id.user_signature);
        userIcon = (CircleImageView) mLayout.findViewById(R.id.user_icon);
    }

    @Override
    public void onResume() {
        super.onResume();
        String sinfoName= SharePreferenceUtils.getStringData(getActivity(), ProjectContent.USER_INFO_NAME);
        String sinfoIntroduce=SharePreferenceUtils.getStringData(getActivity(),ProjectContent.USER_INFO_INTRODUCE);
        if (sinfoName!=null){
            userName.setText(sinfoName);
        }else {
            userName.setText("--");
        }
        if (sinfoIntroduce!=null){
            userSignature.setText(sinfoIntroduce);
        }else{
            userSignature.setText("--");
        }
    }

    @Override
    protected void initData() {
        Bitmap bmp=CommonUtils.restoreBitmap("user_avatar");
        if (bmp!=null){
            userIcon.setImageBitmap(bmp);
        }else {
            userIcon.setBackgroundResource(R.drawable.user_photo);
        }
        mSetting.setOnClickListener(mSettingListener);
        userIcon.setOnClickListener(mUserIconListener);
    }
    private View.OnClickListener mSettingListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(), EditerUserInfoActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mUserIconListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPop();
        }
    };

    public void showPop() {
        popupWindow = new PopupWindow(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pop, null);
        TextView popCamera = (TextView) view.findViewById(R.id.from_camera);
        TextView popPhoto = (TextView) view.findViewById(R.id.from_photo);
        popCamera.setOnClickListener(popCameraListener);
        popPhoto.setOnClickListener(popPhotoListener);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_editer_user_info, null);
        popupWindow.setAnimationStyle(R.style.popAnimStyle);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private View.OnClickListener popCameraListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener popPhotoListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent openAlbumIntent = new Intent(
                    Intent.ACTION_GET_CONTENT);
            openAlbumIntent.setType("image/*");
            startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
            popupWindow.dismiss();
        }
    };

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            photo = Utils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            userIcon.setImageBitmap(photo);
            CommonUtils.saveMyBitmap(photo,"user_avatar");
//            uploadPic(photo);
        }
    }
}
